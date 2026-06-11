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
    var THEME_PARAM = 'cluecumber-theme';
    var linkHandlerBound = false;

    function readThemeFromUrl() {
        var params = new URLSearchParams(window.location.search);
        var value = params.get(THEME_PARAM);
        if (value === 'dark' || value === 'light') {
            return value;
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
        document.documentElement.setAttribute('data-theme', theme);
        if (typeof updateDarkLightModeButton === 'function') {
            updateDarkLightModeButton(theme === 'dark');
        }
    }

    function updateUrlTheme(theme) {
        try {
            var url = new URL(window.location.href);
            url.searchParams.set(THEME_PARAM, theme);
            window.history.replaceState(null, '', url);
        } catch (error) {
        }
    }

    function decorateInternalLinks() {
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
        if (linkHandlerBound) {
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

        function onMessage(event) {
            if (!event.data || event.data.type !== 'cluecumber-theme') {
                return;
            }
            if (handled) {
                return;
            }
            handled = true;
            window.removeEventListener('message', onMessage);
            if (callback) {
                callback(event.data.dark);
            }
        }

        window.addEventListener('message', onMessage);

        function sendMessage() {
            if (!frame.contentWindow) {
                return;
            }
            frame.contentWindow.postMessage({
                type: type,
                dark: dark
            }, '*');
        }

        if (frame.contentWindow) {
            sendMessage();
        } else {
            frame.addEventListener('load', sendMessage, {once: true});
        }
    }

    function persistToSharedStorage(dark) {
        postToStorageFrame('cluecumber-set-theme', dark);
    }

    function readFromSharedStorage(callback) {
        postToStorageFrame('cluecumber-get-theme', null, callback);
    }

    function setTheme(theme, persist) {
        applyTheme(theme);
        updateUrlTheme(theme);
        if (persist) {
            persistToSharedStorage(theme === 'dark');
        }
        if (document.body) {
            decorateInternalLinks();
        }
    }

    function redirectWithStoredTheme(targetPath) {
        readFromSharedStorage(function (dark) {
            var theme = dark ? 'dark' : 'light';
            window.location.replace(withThemeParam(targetPath, theme));
        });
    }

    bindLinkThemeHandler();

    return {
        init: function (onReady) {
            var theme = readThemeFromUrl() || document.documentElement.getAttribute('data-theme');
            if (theme === 'dark' || theme === 'light') {
                setTheme(theme, true);
                if (onReady) {
                    onReady();
                }
                return;
            }

            readFromSharedStorage(function (dark) {
                setTheme(dark ? 'dark' : 'light', false);
                if (onReady) {
                    onReady();
                }
            });
        },
        toggle: function () {
            var theme = document.documentElement.getAttribute('data-theme') === 'dark' ? 'light' : 'dark';
            setTheme(theme, true);
        },
        redirectWithStoredTheme: redirectWithStoredTheme,
        decorateInternalLinks: decorateInternalLinks
    };
})();
