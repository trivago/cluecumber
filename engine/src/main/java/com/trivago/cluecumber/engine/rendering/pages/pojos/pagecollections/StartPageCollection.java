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

import com.trivago.cluecumber.engine.constants.Settings;

/**
 * Necessary properties for the start page.
 */
@SuppressWarnings({"FieldMayBeFinal", "unused"})
public class StartPageCollection {
    private Settings.StartPage startPage;
    private boolean redirectToFirstScenario;
    private int redirectScenarioIndex;

    /**
     * Default constructor.
     *
     * @param startPage               The {@link com.trivago.cluecumber.engine.constants.Settings.StartPage} enum.
     * @param redirectToFirstScenario true if it should redirect to the sole scenario detail page.
     * @param redirectScenarioIndex   scenario detail index when redirecting to the sole scenario (ignored otherwise).
     */
    public StartPageCollection(final Settings.StartPage startPage, final boolean redirectToFirstScenario, final int redirectScenarioIndex) {
        this.startPage = startPage;
        this.redirectToFirstScenario = redirectToFirstScenario;
        this.redirectScenarioIndex = redirectScenarioIndex;
    }

    /**
     * Return the start page.
     *
     * @return The {@link com.trivago.cluecumber.engine.constants.Settings.StartPage} enum.
     */
    public Settings.StartPage getStartPage() {
        return startPage;
    }

    /**
     * Should the start page redirect to the first scenario?
     *
     * @return true if it should redirect to the first scenario.
     */
    public boolean isRedirectToFirstScenario() {
        return redirectToFirstScenario;
    }

    /**
     * Scenario detail page index used when redirecting to the sole scenario.
     *
     * @return The scenario index.
     */
    public int getRedirectScenarioIndex() {
        return redirectScenarioIndex;
    }
}
