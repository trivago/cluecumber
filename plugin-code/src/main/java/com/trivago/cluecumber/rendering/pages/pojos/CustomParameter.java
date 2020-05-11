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

import com.trivago.cluecumber.rendering.pages.renderering.RenderingUtils;

import java.net.URL;

public class CustomParameter {
    private final String key;
    private final String value;

    public CustomParameter(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value != null ? value.trim() : "";
    }

    public boolean isUrl() {
        return RenderingUtils.isUrl(getValue());
    }

    @Override
    public String toString() {
        return "CustomParameter{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
