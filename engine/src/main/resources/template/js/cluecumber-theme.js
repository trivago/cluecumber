/*
Copyright 2023 trivago N.V.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

window.CluecumberTheme = (function () {
    var STORAGE_FRAME_ID = 'cluecumber-storage-frame';
    var STORAGE_HTML = 'js/cluecumber-storage.html';
    var STORAGE_KEY = 'darkMode';
    var THEME_PARAM = 'cluecumber-theme';
    var IFRAME_TIMEOUT_MS = 3000;
    var linkHandlerBound = false;

    function isFileProtocol() {
        return window.location.protocol === 'file:';
    }

    function readStoredThemeSync() {
        try {
            var value = localStorage.getItem(STORAGE_KEY);
            if (value === 'enabled') {
                return 'dark';
            }
            if (value === 'disabled') {
                return 'light';
            }
        } catch (error) {
        }
        return null;
    }

    function writeStoredThemeSync(dark) {
        try {
            localStorage.setItem(STORAGE_KEY, dark ? 'enabled' : 'disabled');
        } catch (error) {
        }
    }

    function readThemeFromUrl() {
        var params = new URLSearchParams(window.location.search);
        var value = params.get(THEME_PARAM);
        if (value === 'dark' || value === 'light') {
            return value;
        }
        return null;
    }

    function readThemeForBoot() {
        if (isFileProtocol()) {
            return readThemeFromUrl() || readStoredThemeSync();
        }
        return readStoredThemeSync();
    }

    function resolveReportAssetUrl(relativePath) {
        var path = window.location.href.split('#')[0].split('?')[0];
        var pagesMarker = '/pages/';
        var pagesIdx = path.indexOf(pagesMarker);
        if (pagesIdx !== -1) {
            return path.substring(0, pagesIdx) + '/' + relativePath;
        }
        var lastSlash = path.lastIndexOf('/');
        return path.substring(0, lastSlash + 1) + relativePath;
    }

    function isInternalHref(href) {
        return href
            && href.charAt(0) !== '#'
            && href.indexOf('://') === -1
            && href.indexOf('mailto:') !== 0;
    }

    function withThemeParam(href, theme) {
        if (!theme || !isInternalHref(href)) {
            return href;
        }

        var hash = '';
        var hashIndex = href.indexOf('#');
        if (hashIndex !== -1) {
            hash = href.substring(hashIndex);
            href = href.substring(0, hashIndex);
        }

        var path = href;
        var query = '';
        var queryIndex = href.indexOf('?');
        if (queryIndex !== -1) {
            path = href.substring(0, queryIndex);
            query = href.substring(queryIndex + 1);
        }

        var params = new URLSearchParams(query);
        params.set(THEME_PARAM, theme);
        var serialized = params.toString();
        return path + (serialized ? '?' + serialized : '') + hash;
    }

    function applyTheme(theme) {
        if (theme !== 'dark' && theme !== 'light') {
            return;
        }
        document.documentElement.setAttribute('data-theme', theme);
        if (typeof updateDarkLightModeButton === 'function') {
            updateDarkLightModeButton(theme === 'dark');
        }
    }

    function updateUrlTheme(theme) {
        if (!isFileProtocol()) {
            return;
        }
        try {
            var url = new URL(window.location.href);
            url.searchParams.set(THEME_PARAM, theme);
            window.history.replaceState(null, '', url);
        } catch (error) {
        }
    }

    function decorateInternalLinks() {
        if (!isFileProtocol()) {
            return;
        }

        var theme = document.documentElement.getAttribute('data-theme');
        if (theme !== 'dark' && theme !== 'light') {
            return;
        }

        document.querySelectorAll('a[href]').forEach(function (link) {
            if (link.target === '_blank' || link.hasAttribute('download')) {
                return;
            }
            var href = link.getAttribute('href');
            if (!isInternalHref(href)) {
                return;
            }
            link.setAttribute('href', withThemeParam(href, theme));
        });
    }

    function bindLinkThemeHandler() {
        if (!isFileProtocol() || linkHandlerBound) {
            return;
        }
        linkHandlerBound = true;

        document.addEventListener('click', function (event) {
            var link = event.target.closest('a[href]');
            if (!link || link.target === '_blank' || link.hasAttribute('download')) {
                return;
            }
            var href = link.getAttribute('href');
            if (!isInternalHref(href)) {
                return;
            }
            var theme = document.documentElement.getAttribute('data-theme');
            if (theme !== 'dark' && theme !== 'light') {
                return;
            }
            link.setAttribute('href', withThemeParam(href, theme));
        }, true);
    }

    function getStorageFrame() {
        var frame = document.getElementById(STORAGE_FRAME_ID);
        if (!frame) {
            frame = document.createElement('iframe');
            frame.id = STORAGE_FRAME_ID;
            frame.src = resolveReportAssetUrl(STORAGE_HTML);
            frame.hidden = true;
            frame.title = '';
            document.documentElement.appendChild(frame);
        }
        return frame;
    }

    function postToStorageFrame(type, dark, callback) {
        var frame = getStorageFrame();
        var handled = false;
        var timeoutId = null;

        function finish(darkResult) {
            if (handled) {
                return;
            }
            handled = true;
            window.removeEventListener('message', onMessage);
            if (timeoutId !== null) {
                clearTimeout(timeoutId);
            }
            if (callback) {
                callback(darkResult);
            }
        }

        function onMessage(event) {
            if (!event.data || event.data.type !== 'cluecumber-theme') {
                return;
            }
            finish(event.data.dark);
        }

        window.addEventListener('message', onMessage);

        if (callback) {
            timeoutId = setTimeout(function () {
                finish(false);
            }, IFRAME_TIMEOUT_MS);
        }

        function sendMessage() {
            if (!frame.contentWindow) {
                return;
            }
            frame.contentWindow.postMessage({
                type: type,
                dark: dark
            }, '*');
        }

        function sendWhenReady() {
            try {
                if (frame.contentDocument && frame.contentDocument.readyState === 'complete') {
                    sendMessage();
                } else {
                    frame.addEventListener('load', sendMessage, {once: true});
                }
            } catch (error) {
                frame.addEventListener('load', sendMessage, {once: true});
            }
        }

        sendWhenReady();
    }

    function persistToSharedStorage(dark) {
        writeStoredThemeSync(dark);
        if (isFileProtocol()) {
            postToStorageFrame('cluecumber-set-theme', dark);
        }
    }

    function readFromSharedStorage(callback) {
        postToStorageFrame('cluecumber-get-theme', null, callback);
    }

    (function applyEarlyTheme() {
        var theme = readThemeForBoot();
        if (theme === 'dark' || theme === 'light') {
            applyTheme(theme);
        }
    })();

    bindLinkThemeHandler();

    return {
        init: function (onReady) {
            var theme = readThemeForBoot()
                || document.documentElement.getAttribute('data-theme');
            if (theme === 'dark' || theme === 'light') {
                applyTheme(theme);
                writeStoredThemeSync(theme === 'dark');
                if (isFileProtocol()) {
                    updateUrlTheme(theme);
                    postToStorageFrame('cluecumber-set-theme', theme === 'dark');
                    if (document.body) {
                        decorateInternalLinks();
                    }
                }
            }
            if (onReady) {
                onReady();
            }
        },
        toggle: function () {
            var isDark = document.documentElement.getAttribute('data-theme') !== 'dark';
            var theme = isDark ? 'dark' : 'light';
            applyTheme(theme);
            persistToSharedStorage(isDark);
            if (isFileProtocol()) {
                updateUrlTheme(theme);
                if (document.body) {
                    decorateInternalLinks();
                }
            }
        },
        redirectWithStoredTheme: function (targetPath) {
            if (!isFileProtocol()) {
                window.location.replace(targetPath);
                return;
            }

            var urlTheme = readThemeFromUrl();
            if (urlTheme) {
                window.location.replace(withThemeParam(targetPath, urlTheme));
                return;
            }

            var syncTheme = readStoredThemeSync();
            if (syncTheme) {
                window.location.replace(withThemeParam(targetPath, syncTheme));
                return;
            }

            var redirected = false;
            function go(theme) {
                if (redirected) {
                    return;
                }
                redirected = true;
                window.location.replace(withThemeParam(targetPath, theme));
            }

            readFromSharedStorage(function (dark) {
                go(dark ? 'dark' : 'light');
            });
            setTimeout(function () {
                go('light');
            }, IFRAME_TIMEOUT_MS);
        },
        decorateInternalLinks: decorateInternalLinks
    };
})();
