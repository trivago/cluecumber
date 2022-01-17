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

package com.trivago.cluecumber.constants;

/**
 * Holder class for static plugin settings
 */
public class PluginSettings {

    public final static String NAME = "Cluecumber Report Plugin";

    public enum StartPage {
        ALL_SCENARIOS(SCENARIO_SUMMARY_PAGE_PATH),
        SCENARIO_SEQUENCE(SCENARIO_SEQUENCE_PAGE_PATH),
        ALL_TAGS(TAG_SUMMARY_PAGE_PATH),
        ALL_STEPS(STEP_SUMMARY_PAGE_PATH),
        ALL_FEATURES(FEATURE_SUMMARY_PAGE_PATH);

        private final String pageName;

        StartPage(final String pageName) {
            this.pageName = pageName;
        }

        @SuppressWarnings("unused")
        public String getPageName() {
            return pageName;
        }
    }

    public enum CustomParamDisplayMode {
        SCENARIO_PAGES,
        ALL_PAGES
    }

    public static final String TEMPLATE_FILE_EXTENSION = ".ftl";
    public static final String HTML_FILE_EXTENSION = ".html";
    public static final String BASE_TEMPLATE_PATH = "/template";
    public static final String PAGES_DIRECTORY = "pages";

    public final static String START_PAGE_PATH = "index";

    public final static String SCENARIO_SUMMARY_PAGE_PATH = "scenario-summary";
    public final static String SCENARIO_SEQUENCE_PAGE_PATH = "scenario-sequence";

    public final static String SCENARIO_DETAIL_PAGE_PATH = "scenario-detail";
    public static final String SCENARIO_DETAIL_PAGE_FRAGMENT = "/" + SCENARIO_DETAIL_PAGE_PATH + "/scenario_";

    public final static String TAG_SUMMARY_PAGE_PATH = "tag-summary";
    public static final String TAG_SCENARIO_PAGE_PATH = "tag-scenarios";
    public static final String TAG_SCENARIO_PAGE_FRAGMENT = "/" + TAG_SCENARIO_PAGE_PATH + "/tag_";

    public final static String STEP_SUMMARY_PAGE_PATH = "step-summary";
    public static final String STEP_SCENARIO_PAGE_PATH = "step-scenarios";
    public static final String STEP_SCENARIO_PAGE_FRAGMENT = "/" + STEP_SCENARIO_PAGE_PATH + "/step_";

    public final static String FEATURE_SUMMARY_PAGE_PATH = "feature-summary";
    public static final String FEATURE_SCENARIOS_PAGE_PATH = "feature-scenarios";
    public static final String FEATURE_SCENARIOS_PAGE_FRAGMENT = "/" + FEATURE_SCENARIOS_PAGE_PATH + "/feature_";

    PluginSettings() {
    }
}
