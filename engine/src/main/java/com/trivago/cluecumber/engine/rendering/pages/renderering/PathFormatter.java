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

import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Formats a {@link Path} for display using a {@link DirectoryNameFormatter} for each element.
 */
public class PathFormatter {

    private static final String ROOT_PATH = "/";

    private final DirectoryNameFormatter directoryNameFormatter;

    /**
     * Constructor
     *
     * @param directoryNameFormatter The formatter to use for formatting each element of the path.
     */
    public PathFormatter(DirectoryNameFormatter directoryNameFormatter) {
        this.directoryNameFormatter = directoryNameFormatter;
    }

    /**
     * Formats the path using the configured {@link DirectoryNameFormatter}.
     *
     * <p>Example (using {@link com.trivago.cluecumber.engine.rendering.pages.renderering.DirectoryNameFormatter.KebabCase}):</p>
     * {@code Path.of("/product-list/top-rated")} → {@code "Product List / Top Rated"}.
     *
     * @param path The path to be formatted
     * @return The string to be displayed
     */
    public String formatPath(Path path) {
        if (Objects.requireNonNull(path).getNameCount() == 0) {
            return ROOT_PATH;
        }
        return StreamSupport.stream(path.spliterator(), false).map(Path::toString)
                .map(directoryNameFormatter::toDisplayName)
                .collect(Collectors.joining(" / "));
    }
}
