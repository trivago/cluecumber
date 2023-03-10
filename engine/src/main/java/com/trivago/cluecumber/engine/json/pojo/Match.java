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
package com.trivago.cluecumber.engine.json.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * The match of the {@link ResultMatch}.
 */
public class Match {
    private String location = "";
    private List<Argument> arguments = new ArrayList<>();

    /**
     * Get the class and method where this step is defined.
     *
     * @return The location string.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the class and method where this step is defined.
     *
     * @param location The location string.
     */
    public void setLocation(final String location) {
        this.location = location;
    }

    /**
     * Get the list of arguments of the step method.
     *
     * @return The {@link Argument} list.
     */
    public List<Argument> getArguments() {
        return arguments;
    }

    /**
     * Set the list of arguments of the step method.
     *
     * @param arguments The {@link Argument} list.
     */
    public void setArguments(final List<Argument> arguments) {
        this.arguments = arguments;
    }
}
