/*
 * Copyright 2019 trivago N.V.
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

package com.trivago.cluecumber.rendering.pages.pojos;

import java.util.Objects;

public class Feature {
    private final String name;
    private final String description;
    private final int index;
    private final String uri;

    public Feature(final String name, final String description, final String uri, final int index) {
        this.name = name;
        this.description = description;
        this.uri = uri;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUri() {
        return uri;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "name='" + name + '\'' +
                ", index='" + index + '\'' +
                '}';
    }

    // description is not taken into account in comparison
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feature feature = (Feature) o;
        return index == feature.index &&
                Objects.equals(name, feature.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, index);
    }
}
