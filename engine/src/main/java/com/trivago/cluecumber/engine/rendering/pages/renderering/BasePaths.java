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

import com.trivago.cluecumber.engine.rendering.pages.pojos.Feature;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A set of potential base paths of feature file URIs.
 *
 * <p>These base paths can be stripped from the {@link Feature#getUri()} of {@link com.trivago.cluecumber.engine.rendering.pages.pojos.Feature} when presenting them hierarchically.
 * <p>Example: {@code "/features/customer/registration.feature"} → {@code "/customer/registration.feature"}.
 */
public class BasePaths {
    private final Set<Path> normalizedBasePaths;

    /**
     * Constructs a BasePaths object
     *
     * @param basePaths The collection of base paths
     */
    public BasePaths(Collection<Path> basePaths) {
        Objects.requireNonNull(basePaths, "Base paths cannot be null");
        this.normalizedBasePaths = basePaths.stream()
                .map(BasePaths::normalizePath)
                .collect(Collectors.toSet());
    }

    /**
     * Constructs a BasePaths object from a collection of strings, representing the paths.
     *
     * @param basePaths The collection of base paths
     * @return the newly initialized instance
     */
    public static BasePaths fromStrings(Collection<String> basePaths) {
        Set<Path> paths = Objects.requireNonNull(basePaths, "Base paths cannot be null").stream()
                .map(Path::of)
                .collect(Collectors.toSet());
        return new BasePaths(paths);
    }

    /**
     * Normalizes a path and treats it as "absolute" for comparison purposes.
     *
     * @param path The path to normalize.
     * @return The normalized "absolute" path.
     */
    private static Path normalizePath(Path path) {
        String pathString = path.toString();
        if (!pathString.startsWith("/")) {
            pathString = "/" + pathString;
        }
        return Path.of(pathString).normalize();
    }

    /**
     * Gets the normalized base paths.
     *
     * @return An unmodifiable list of normalized base paths.
     */
    public Set<Path> getBasePaths() {
        return Collections.unmodifiableSet(normalizedBasePaths);
    }

    /**
     * Finds the longest matching base path for the given path and strips it.
     *
     * @param path The {@link Path} to process.
     * @return The stripped path as a {@link Path}, or {@code "/"} if the resulting path is empty.
     */
    public Path stripBasePath(Path path) {
        Path normalizedPath = normalizePath(Objects.requireNonNull(path, "Path cannot be null"));

        Path bestMatch = null;
        for (Path basePath : normalizedBasePaths) {
            if (normalizedPath.startsWith(basePath) && (bestMatch == null || basePath.toString().length() > bestMatch.toString().length())) {
                    bestMatch = basePath;
            }
        }

        if (bestMatch != null) {
            return normalizePath(bestMatch.relativize(normalizedPath));
        }

        return normalizedPath;
    }
}