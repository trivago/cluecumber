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

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * {@code DirectoryNameFormatter} defines the SPI for converting directory names
 * to a display name.
 *
 * <p>Implementations of this interface are used to dynamically format directory names in the URI of feature files when presented in reports.
 *
 * <h2>Built-in Implementations</h2>
 * <ul>
 *     <li>{@link Standard}</li>
 *     <li>{@link SnakeCase}</li>
 *     <li>{@link CamelCase}</li>
 *     <li>{@link KebabCase}</li>
 * </ul>
 */
public interface DirectoryNameFormatter {

    /**
     * Converts a directory name into the display name based on the specific naming convention.
     *
     * @param name the original directory name, never {@code null}
     * @return the formatted directory name
     */
    String toDisplayName(String name);

    /**
     * {@code Standard} formatter that uses the input name as-is without modification.
     */
    class Standard implements DirectoryNameFormatter {

        @Override
        public String toDisplayName(String name) {
            return Objects.requireNonNull(name);
        }
    }

    /**
     * {@code Snake Case} formatter that converts snake_case names into Title Case.
     *
     * <p>Example: {@code "my_directory_name"} → {@code "My Directory Name"}.
     *
     * <p>Caveat: Minor words are capitalized as well, contrary to common Title Case standards.
     * {@code "an_introduction_to_programming_with_java"} → {@code "An Introduction To Programming With Java"} instead of {@code "An Introduction to Programming with Java"}.
     */
    class SnakeCase implements DirectoryNameFormatter {

        @Override
        public String toDisplayName(String name) {
            if (Objects.requireNonNull(name).isEmpty()) {
                return name;
            }
            return Arrays.stream(name.split("_"))
                    .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                    .collect(Collectors.joining(" "));
        }
    }

    /**
     * {@code CamelCase} formatter that converts camelCase names into Title Case.
     *
     * <p>Example: {@code "myDirectoryName"} → {@code "My Directory Name"}.
     *
     * <p>Caveat: Minor words are capitalized as well, contrary to common Title Case standards.
     * {@code "anIntroductionToProgrammingWithJava"} → {@code "An Introduction To Programming With Java"} instead of {@code "An Introduction to Programming with Java"}.
     */
    class CamelCase implements DirectoryNameFormatter {

        @Override
        public String toDisplayName(String name) {
            if (Objects.requireNonNull(name).isEmpty()) {
                return name;
            }
            return capitalizeWords(name.replaceAll(
                    "(?<=\\p{Lu})(?=\\p{Lu}\\p{Ll})"
                    + "|"
                    + "(?<=[^\\p{Lu}])(?=\\p{Lu})"
                    + "|"
                    + "(?<=\\d)(?=\\p{Ll})"
                    + "|"
                    + "(?<=\\p{L})(?=[^A-Za-z])",
                    " "));
        }

        private String capitalizeWords(String text) {
            return Arrays.stream(text.split("\\s+"))
                    .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                    .collect(Collectors.joining(" "));
        }

    }

    /**
     * {@code KebabCase} formatter that converts kebab-case names into Title Case.
     *
     * <p>Example: {@code "my-directory-name"} → {@code "My Directory Name"}.
     *
     * <p>Caveat: Minor words are capitalized as well, contrary to common Title Case standards.
     * {@code "an-introduction-to-programming-with-java"} → {@code "An Introduction To Programming With Java"} instead of {@code "An Introduction to Programming with Java"}.
     */
    class KebabCase implements DirectoryNameFormatter {

        @Override
        public String toDisplayName(String name) {
            if (Objects.requireNonNull(name).isEmpty()) {
                return name;
            }
            return Arrays.stream(name.split("-"))
                    .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                    .collect(Collectors.joining(" "));
        }
    }
}