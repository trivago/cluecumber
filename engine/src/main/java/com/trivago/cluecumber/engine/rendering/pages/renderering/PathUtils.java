/*
 * Copyright 2023 trivago N.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trivago.cluecumber.engine.rendering.pages.renderering;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class PathUtils {

    private PathUtils() {
    }

    /**
     * Extracts and normalizes the parent directory path from a URI or path string.
     *
     * <p>Returns {@code "/" } if there is no parent directory.
     *
     * @param uriString The URI or path string.
     * @return The normalized parent directory as a {@link Path}, or {@code "/" } if there is no parent.
     */
    public static Path extractDirectoryPath(String uriString) {
        try {
            URI uri = new URI(uriString);
            String rawPath = "classpath".equalsIgnoreCase(uri.getScheme())
                    ? uri.getSchemeSpecificPart()
                    : uri.getPath();

            if (rawPath == null) {
                // Handle cases where the path is null (e.g., malformed file URIs)
                rawPath = uri.getSchemeSpecificPart();
            }

            Path normalizedPath = Paths.get(rawPath).normalize();
            Path parent = normalizedPath.getParent();
            return parent == null ? Path.of("/") : parent;
        } catch (URISyntaxException | IllegalArgumentException e) {
            // Fallback for invalid URIs or plain paths
            Path normalizedPath = Paths.get(uriString).normalize();
            Path parent = normalizedPath.getParent();
            return parent == null ? Path.of("/") : parent;
        }
    }
}
