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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathUtilsTest {

    @ParameterizedTest(name = "Test case {index}: input=\"{0}\"")
    @CsvSource({
            "/resources/features/Login.feature, /resources/features",
            "classpath:Calendar.feature,/",
            "classpath:org/ShoppingCart.feature, org",
            "feature/Karate.feature, feature",
            "feature/../KarateEmbedded.feature,/",
            "features/feature1.feature, features",
            "features/../feature2.feature,/",
            "file:API/src/test/resources/features/orion_user.feature, API/src/test/resources/features",
            "file:API/src/test/resources/features/foo/../orion_user.feature, API/src/test/resources/features",
            "file:target/parallel/features/MyTest10_scenario007_run001_IT.feature, target/parallel/features",
            "http://example.com/features/test.feature, /features"
    })
    void testExtractDirectoryPath(String input, Path expected) {
        Path actual = PathUtils.extractDirectoryPath(input);
        assertEquals(expected, actual,
                "Expected directory path for input \"" + input + "\" does not match.");
    }
}