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
import java.util.Comparator;

/**
 * A comparator for {@link Path} objects that compares paths based on the following rules:
 * <ul>
 *     <li>If one path is a prefix of the other, the shorter path is considered "less than" the longer path.</li>
 *     <li>Otherwise, paths are compared alphabetically based on their string representations.</li>
 * </ul>
 *
 * <p>This comparator is useful when paths need to be sorted in a way that respects both prefix relationships
 * and lexicographical order.
 * For example, this ensures that a parent directory appears before its subdirectories
 * in a sorted list.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * List&lt;Path&gt; paths = List.of(
 *     Path.of("/a/b"),
 *     Path.of("/a"),
 *     Path.of("/b")
 * );
 * paths.sort(new PathComparator());
 * // Result: ["/a", "/a/b", "/b"]
 * </pre>
 */
public class PathComparator implements Comparator<Path> {

    @Override
    public int compare(Path path1, Path path2) {
        String p1 = path1.toString();
        String p2 = path2.toString();

        // If one path is a prefix of the other, prioritize the shorter path
        if (p1.startsWith(p2) || p2.startsWith(p1)) {
            return Integer.compare(p1.length(), p2.length());
        }

        // Otherwise, sort alphabetically
        return p1.compareTo(p2);
    }
}