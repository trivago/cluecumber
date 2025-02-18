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
package com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections;

import com.trivago.cluecumber.engine.rendering.pages.renderering.BasePaths;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BasePathsTest {

    @Test
    void testBasePathsRelativePathsBecomeAbsolute() {
        List<Path> basePaths = List.of(
                Path.of("/features/"),
                Path.of("features/foo") // Relative path
        );
        BasePaths basePathsObject = new BasePaths(basePaths);

        assertTrue(basePathsObject.getBasePaths().contains(Path.of("/features")));
        assertTrue(basePathsObject.getBasePaths().contains(Path.of("/features/foo")));
    }

    @Test
    void testExactMatch() {
        BasePaths basePaths = new BasePaths(List.of(
                Path.of("/features")
        ));
        Path inputPath = Path.of("/features");

        Path result = basePaths.stripBasePath(inputPath);

        assertEquals("/", result.toString());
    }

    @Test
    void testRelativeMatch() {
        BasePaths basePaths = new BasePaths(List.of(
                Path.of("/features")
        ));
        Path inputPath = Path.of("features/foo");

        Path result = basePaths.stripBasePath(inputPath);

        assertEquals("/foo", result.toString());
    }

    @Test
    void testLongestMatch() {
        BasePaths basePaths = new BasePaths(List.of(
                Path.of("/features"),
                Path.of("/features/foo")
        ));
        Path inputPath = Path.of("/features/foo/blah");

        Path result = basePaths.stripBasePath(inputPath);

        assertNotNull(result);
        assertEquals("/blah", result.toString());
    }

    @Test
    void testNoMatch() {
        BasePaths basePaths = new BasePaths(List.of(
                Path.of("/unrelated/path")
        ));
        Path inputPath = Path.of("/features/foo/blah");

        Path result = basePaths.stripBasePath(inputPath);

        assertNotNull(result);
        assertEquals("/features/foo/blah", result.toString());
    }

    @Test
    void testRelativeInputPathWithAbsoluteBasePaths() {
        BasePaths basePaths = new BasePaths(List.of(
                Path.of("/features/"),
                Path.of("/features/foo/")
        ));
        Path inputPath = Path.of("features/foo/blah"); // Relative input

        Path result = basePaths.stripBasePath(inputPath);

        assertNotNull(result);
        assertEquals("/blah", result.toString());
    }

    @Test
    void testNullInput() {
        BasePaths basePaths = new BasePaths(List.of(
                Path.of("/features")
        ));

        assertThrows(NullPointerException.class, () -> basePaths.stripBasePath(null));
    }

    @Test
    void testEmptyBasePaths() {
        BasePaths basePaths = new BasePaths(List.of());
        Path inputPath = Path.of("/features/foo/blah");

        Path result = basePaths.stripBasePath(inputPath);

        assertNotNull(result);
        assertEquals("/features/foo/blah", result.toString());
    }
}