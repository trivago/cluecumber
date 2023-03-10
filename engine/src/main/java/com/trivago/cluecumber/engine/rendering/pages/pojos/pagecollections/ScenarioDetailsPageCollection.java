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

import com.trivago.cluecumber.engine.json.pojo.Element;

/**
 * The page collection for the scenario details page.
 */
public class ScenarioDetailsPageCollection extends PageCollection {
    private final Element element;

    /**
     * The constructor.
     *
     * @param element   The scenario.
     * @param pageTitle The page title.
     */
    public ScenarioDetailsPageCollection(final Element element, final String pageTitle) {
        super(pageTitle);
        this.element = element;
    }

    /**
     * Get the scenario from this page collection.
     *
     * @return The scenario.
     */
    public Element getElement() {
        return element;
    }
}
