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

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathComparatorTest {

    private final PathComparator classUnderTest = new PathComparator();

    @Test
    void testSortPathsWithNestedSubsets() {
        List<Path> paths = List.of(
                Path.of("/features/shoppingcart"),
                Path.of("/features/checkout/payment"),
                Path.of("/features/checkout"),
                Path.of("/features/"),
                Path.of("/features/checkout/shipping")
        );

        paths = paths.stream()
                .sorted(classUnderTest)
                .collect(Collectors.toList());

        List<Path> expectedOrder = List.of(
                Path.of("/features/"),
                Path.of("/features/checkout"),
                Path.of("/features/checkout/payment"),
                Path.of("/features/checkout/shipping"),
                Path.of("/features/shoppingcart")
        );

        assertEquals(expectedOrder, paths);
    }
}