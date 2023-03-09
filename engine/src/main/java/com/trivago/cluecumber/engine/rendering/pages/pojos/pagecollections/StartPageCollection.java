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

@SuppressWarnings({"FieldMayBeFinal", "unused"})
public class StartPageCollection {
    private Settings.StartPage startPage;
    private boolean redirectToFirstScenario;

    public StartPageCollection(Settings.StartPage startPage, boolean redirectToFirstScenario) {
        this.startPage = startPage;
        this.redirectToFirstScenario = redirectToFirstScenario;
    }

    public Settings.StartPage getStartPage() {
        return startPage;
    }

    public boolean isRedirectToFirstScenario() {
        return redirectToFirstScenario;
    }
}
