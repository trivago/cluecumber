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

/**
 * The Argument class represents a Cucumber step argument.
 */
public class Argument {
    private String val;
    private int offset;

    /**
     * Gets the value of the argument.
     *
     * @return A string representing the value of the argument.
     */
    public String getVal() {
        return val;
    }

    /**
     * Sets the value of the argument.
     *
     * @param val A string representing the value to be set.
     */
    public void setVal(final String val) {
        this.val = val;
    }

    /**
     * Gets the offset of the argument.
     *
     * @return An integer representing the offset of the argument.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Sets the offset of the argument.
     *
     * @param offset An integer representing the offset to be set.
     */
    public void setOffset(final int offset) {
        this.offset = offset;
    }
}