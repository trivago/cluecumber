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

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DirectoryNameFormatterTest {

    @Nested
    class StandardFormatterTests {

        @Test
        void shouldReturnNameAsIs() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.Standard();
            assertEquals("Already Formatted", formatter.toDisplayName("Already Formatted"));
        }

        @Test
        void shouldHandleEmptyInput() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.Standard();
            assertEquals("", formatter.toDisplayName(""));
        }

        @Test
        void shouldHandleNullInput() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.Standard();
            assertThrows(NullPointerException.class, () -> formatter.toDisplayName(null));
        }
    }

    @Nested
    class SnakeCaseFormatterTests {

        @Test
        void shouldConvertSnakeCaseToTitleCase() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.SnakeCase();
            assertEquals("This Is A Directory", formatter.toDisplayName("this_is_a_directory"));
        }

        @Test
        void shouldHandleEmptyInput() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.SnakeCase();
            assertEquals("", formatter.toDisplayName(""));
        }

        @Test
        void shouldHandleSingleWord() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.SnakeCase();
            assertEquals("Directory", formatter.toDisplayName("directory"));
        }

        @Test
        void shouldHandleMixedCaseInput() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.SnakeCase();
            assertEquals("Directory Loader", formatter.toDisplayName("directory_Loader"));
        }

        @Test
        void shouldCapitalizeMinorWordsInSnakeCase() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.SnakeCase();
            assertEquals("An Introduction To Programming With Java",
                    formatter.toDisplayName("an_introduction_to_programming_with_java"));
        }
    }

    @Nested
    class CamelCaseFormatterTests {

        @Test
        void shouldConvertCamelCaseToTitleCase() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.CamelCase();
            assertEquals("This Is A Directory", formatter.toDisplayName("thisIsADirectory"));
        }

        @Test
        void shouldHandleSingleWord() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.CamelCase();
            assertEquals("Directory", formatter.toDisplayName("directory"));
        }

        @Test
        void shouldHandleMixedCaseInput() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.CamelCase();
            assertEquals("Loader PDF", formatter.toDisplayName("loaderPDF"));
        }

        @Test
        void shouldCapitalizeMinorWordsInCamelCase() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.CamelCase();
            assertEquals("An Introduction To Programming With Java",
                    formatter.toDisplayName("anIntroductionToProgrammingWithJava"));
        }

        @Test
        void shouldHandleUpperCaseAcronymInCamelCase() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.CamelCase();
            assertEquals("XML Parser", formatter.toDisplayName("XMLParser"));
        }

        @Test
        void shouldHandleEmptyInput() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.CamelCase();
            assertEquals("", formatter.toDisplayName(""));
        }

        @Test
        void shouldHandleNumbersInCamelCase() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.CamelCase();

            assertEquals("99 Bottles", formatter.toDisplayName("99bottles"));
            assertEquals("Bottles 99", formatter.toDisplayName("bottles99"));
            assertEquals("99 Bottles Of Beer", formatter.toDisplayName("99bottlesOfBeer"));
        }
    }

    @Nested
    class KebabCaseFormatterTests {

        @Test
        void shouldConvertKebabCaseToTitleCase() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.KebabCase();
            assertEquals("This Is A Directory", formatter.toDisplayName("this-is-a-directory"));
        }

        @Test
        void shouldHandleSingleWord() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.KebabCase();
            assertEquals("Directory", formatter.toDisplayName("directory"));
        }

        @Test
        void shouldHandleMixedCaseInput() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.KebabCase();
            assertEquals("Loader Pdf", formatter.toDisplayName("loader-Pdf"));
        }

        @Test
        void shouldCapitalizeMinorWordsInKebabCase() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.KebabCase();
            assertEquals("An Introduction To Programming With Java",
                    formatter.toDisplayName("an-introduction-to-programming-with-java"));
        }

        @Test
        void shouldHandleEmptyInput() {
            DirectoryNameFormatter formatter = new DirectoryNameFormatter.KebabCase();
            assertEquals("", formatter.toDisplayName(""));
        }
    }
}