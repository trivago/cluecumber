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
 * Table row.
 */
public class Row {

    private List<String> cells = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Row() {
        // Default constructor
    }

    /**
     * Get the individual table cells in this row.
     *
     * @return The string value from every cell.
     */
    public List<String> getCells() {
        return cells;
    }

    /**
     * Set the individual table cells in this row.
     *
     * @param cells The list of string values, each one representing a cell.
     */
    public void setCells(final List<String> cells) {
        this.cells = cells;
    }
}
