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

package com.trivago.cluecumber.exceptions.properties;

import com.trivago.cluecumber.exceptions.CluecumberPluginException;

/**
 * Thrown when an expected plugin property is not found or wrong
 * (typically set inside a configuration block within the pom file).
 */
public class WrongOrMissingPropertyException extends CluecumberPluginException {
    /**
     * Constructor.
     *
     * @param property The name of the missing property.
     */
    public WrongOrMissingPropertyException(final String property) {
        super("Property '" + property
                + "' is not specified in the configuration section "
                + "of your pom file or contains an invalid value.");
    }
}
