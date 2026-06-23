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
    var LEGACY_STORAGE_KEY = 'cluecumber-dark-mode';
    var THEME_PARAM = 'cluecumber-theme';
    var STORAGE_TIMEOUT_MS = 3000;
    var linkHandlerBound = false;

    function isFileProtocol() {
        return window.location.protocol === 'file:';
    }

    function readLocalStorageTheme() {
        try {
            var value = localStorage.getItem(STORAGE_KEY);
            if (value === null) {
                var legacy = localStorage.getItem(LEGACY_STORAGE_KEY);
                if (legacy === 'enabled' || legacy === 'disabled') {
                    localStorage.setItem(STORAGE_KEY, legacy);
                    value = legacy;
                }
            }
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

    function writeLocalStorageTheme(theme) {
        try {
            localStorage.setItem(STORAGE_KEY, theme === 'dark' ? 'enabled' : 'disabled');
            localStorage.removeItem(LEGACY_STORAGE_KEY);
        } catch (error) {
        }
    }

    function readThemeFromUrl() {
        try {
            var params = new URLSearchParams(window.location.search);
            var value = params.get(THEME_PARAM);
            if (value === 'dark' || value === 'light') {
                return value;
            }
        } catch (error) {
        }
        return null;
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

    function postToStorage(type, theme, callback) {
        var frame = getStorageFrame();
        var handled = false;
        var timeoutId = null;

        function finish(result) {
            if (handled) {
                return;
            }
            handled = true;
            window.removeEventListener('message', onMessage);
            if (timeoutId !== null) {
                clearTimeout(timeoutId);
            }
            if (callback) {
                callback(result);
            }
        }

        function onMessage(event) {
            if (!event.data || event.data.type !== 'cluecumber-theme') {
                return;
            }
            if (frame.contentWindow && event.source !== frame.contentWindow) {
                return;
            }
            finish(event.data.theme || null);
        }

        window.addEventListener('message', onMessage);
        timeoutId = setTimeout(function () {
            finish(null);
        }, STORAGE_TIMEOUT_MS);

        function send() {
            if (!frame.contentWindow) {
                return;
            }
            frame.contentWindow.postMessage({
                type: type,
                theme: theme
            }, '*');
        }

        frame.addEventListener('load', send, {once: true});
        send();
    }

    function readSharedTheme(callback) {
        if (!isFileProtocol()) {
            callback(null);
            return;
        }
        postToStorage('cluecumber-get-theme', null, callback);
    }

    function writeSharedTheme(theme, callback) {
        if (!isFileProtocol()) {
            if (callback) {
                callback();
            }
            return;
        }
        postToStorage('cluecumber-set-theme', theme, function () {
            if (callback) {
                callback();
            }
        });
    }

    function getHttpTheme() {
        var stored = readLocalStorageTheme();
        if (stored) {
            return stored;
        }
        return 'light';
    }

    function getEarlyTheme() {
        var urlTheme = readThemeFromUrl();
        if (urlTheme) {
            return urlTheme;
        }
        if (isFileProtocol()) {
            return 'light';
        }
        return getHttpTheme();
    }

    function isInternalHref(href) {
        return href
            && href.charAt(0) !== '#'
            && href.indexOf('://') === -1
            && href.indexOf('mailto:') !== 0
            && href.indexOf('javascript:') !== 0;
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

    function currentTheme() {
        var theme = document.documentElement.getAttribute('data-theme');
        if (theme === 'dark' || theme === 'light') {
            return theme;
        }
        return 'light';
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

        var theme = currentTheme();
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
            link.setAttribute('href', withThemeParam(href, currentTheme()));
        }, true);
    }

    function finishFileTheme(theme) {
        applyTheme(theme);
        updateUrlTheme(theme);
        if (document.body) {
            decorateInternalLinks();
        }
    }

    function resolveFileTheme(callback) {
        var urlTheme = readThemeFromUrl();
        if (urlTheme) {
            writeSharedTheme(urlTheme, function () {
                callback(urlTheme);
            });
            return;
        }
        readSharedTheme(function (storedTheme) {
            callback(storedTheme || 'light');
        });
    }

    (function applyEarlyTheme() {
        applyTheme(getEarlyTheme());
    })();

    bindLinkThemeHandler();

    return {
        init: function (onReady) {
            if (isFileProtocol()) {
                resolveFileTheme(finishFileTheme);
            } else {
                applyTheme(getHttpTheme());
            }
            if (onReady) {
                onReady();
            }
        },
        toggle: function () {
            var isDark = currentTheme() === 'dark';
            var theme = isDark ? 'light' : 'dark';
            applyTheme(theme);
            if (isFileProtocol()) {
                writeSharedTheme(theme, function () {
                    updateUrlTheme(theme);
                    if (document.body) {
                        decorateInternalLinks();
                    }
                });
                return;
            }
            writeLocalStorageTheme(theme);
        },
        redirectWithStoredTheme: function (targetPath) {
            if (!isFileProtocol()) {
                window.location.replace(targetPath);
                return;
            }
            resolveFileTheme(function (theme) {
                window.location.replace(withThemeParam(targetPath, theme));
            });
        },
        hrefWithTheme: function (href) {
            if (!isFileProtocol()) {
                return href;
            }
            return withThemeParam(href, currentTheme());
        },
        decorateInternalLinks: decorateInternalLinks
    };
})();
