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
    var STORAGE_KEY = 'darkMode';
    var LEGACY_STORAGE_KEY = 'cluecumber-dark-mode';
    var SESSION_KEY = 'cluecumber-theme';
    var THEME_PARAM = 'cluecumber-theme';
    var linkHandlerBound = false;

    function isFileProtocol() {
        return window.location.protocol === 'file:';
    }

    function prefersDark() {
        return window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
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

    function readSessionTheme() {
        try {
            var value = sessionStorage.getItem(SESSION_KEY);
            if (value === 'dark' || value === 'light') {
                return value;
            }
        } catch (error) {
        }
        return null;
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

    function getTheme() {
        var urlTheme = readThemeFromUrl();
        if (urlTheme) {
            return urlTheme;
        }

        if (!isFileProtocol()) {
            var stored = readLocalStorageTheme();
            if (stored) {
                return stored;
            }
            return 'light';
        }

        var fileStored = readLocalStorageTheme();
        if (fileStored) {
            return fileStored;
        }

        var sessionTheme = readSessionTheme();
        if (sessionTheme) {
            return sessionTheme;
        }

        return prefersDark() ? 'dark' : 'light';
    }

    function persistTheme(theme) {
        var dark = theme === 'dark';
        if (!isFileProtocol()) {
            try {
                localStorage.setItem(STORAGE_KEY, dark ? 'enabled' : 'disabled');
                localStorage.removeItem(LEGACY_STORAGE_KEY);
            } catch (error) {
            }
            return;
        }

        try {
            sessionStorage.setItem(SESSION_KEY, theme);
        } catch (error) {
        }
        try {
            localStorage.setItem(STORAGE_KEY, dark ? 'enabled' : 'disabled');
            localStorage.removeItem(LEGACY_STORAGE_KEY);
        } catch (error) {
        }
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

    (function applyEarlyTheme() {
        applyTheme(getTheme());
    })();

    bindLinkThemeHandler();

    return {
        init: function (onReady) {
            var theme = getTheme();
            applyTheme(theme);
            persistTheme(theme);
            if (isFileProtocol()) {
                updateUrlTheme(theme);
                if (document.body) {
                    decorateInternalLinks();
                }
            }
            if (onReady) {
                onReady();
            }
        },
        toggle: function () {
            var isDark = document.documentElement.getAttribute('data-theme') === 'dark';
            var theme = isDark ? 'light' : 'dark';
            applyTheme(theme);
            persistTheme(theme);
            if (isFileProtocol()) {
                updateUrlTheme(theme);
                if (document.body) {
                    decorateInternalLinks();
                }
            }
        },
        redirectWithStoredTheme: function (targetPath) {
            if (isFileProtocol()) {
                window.location.replace(withThemeParam(targetPath, getTheme()));
                return;
            }
            window.location.replace(targetPath);
        },
        decorateInternalLinks: decorateInternalLinks
    };
})();
