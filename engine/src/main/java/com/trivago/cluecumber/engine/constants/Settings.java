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
package com.trivago.cluecumber.engine.constants;

/**
 * Holder class for static plugin settings
 */
public class Settings {

    /**
     * The name of the Cluecumber plugin.
     */
    public final static String NAME = "Cluecumber Report";
    /**
     * The file extension for Freemarker template files.
     */
    public static final String TEMPLATE_FILE_EXTENSION = ".ftl";
    /**
     * The file extension for HTML files.
     */
    public static final String HTML_FILE_EXTENSION = ".html";
    /**
     * The folder for Freemarker template files.
     */
    public static final String BASE_TEMPLATE_PATH = "/template";
    /**
     * The folder for report pages.
     */
    public static final String PAGES_DIRECTORY = "pages";
    /**
     * The name of the report start page.
     */
    public final static String START_PAGE = "index";
    /**
     * The folder of the scenario summary pages.
     */
    public final static String SCENARIO_SUMMARY_PAGE_PATH = "scenario-summary";
    /**
     * The folder of the scenario sequence page.
     */
    public final static String SCENARIO_SEQUENCE_PAGE_PATH = "scenario-sequence";
    /**
     * The folder of the scenario detail pages.
     */
    public final static String SCENARIO_DETAIL_PAGE_PATH = "scenario-detail";
    /**
     * The first part of the name of scenario detail pages.
     */
    public static final String SCENARIO_DETAIL_PAGE_FRAGMENT = "/" + SCENARIO_DETAIL_PAGE_PATH + "/scenario_";
    /**
     * The name of the tag summary page.
     */
    public final static String TAG_SUMMARY_PAGE = "tag-summary";
    /**
     * The folder of the scenarios by tag pages.
     */
    public static final String TAG_SCENARIO_PAGE_PATH = "tag-scenarios";
    /**
     * The first part of the name of scenario by tag pages.
     */
    public static final String TAG_SCENARIO_PAGE_FRAGMENT = "/" + TAG_SCENARIO_PAGE_PATH + "/tag_";
    /**
     * The name of the step summary page.
     */
    public final static String STEP_SUMMARY_PAGE = "step-summary";
    /**
     * The folder of the scenarios by step pages.
     */
    public static final String STEP_SCENARIO_PAGE_PATH = "step-scenarios";
    /**
     * The first part of the name of scenarios by step pages.
     */
    public static final String STEP_SCENARIO_PAGE_FRAGMENT = "/" + STEP_SCENARIO_PAGE_PATH + "/step_";
    /**
     * The name of the feature summary page.
     */
    public final static String FEATURE_SUMMARY_PAGE = "feature-summary";
    /**
     * The folder of the scenarios by feature pages.
     */
    public static final String FEATURE_SCENARIOS_PAGE_PATH = "feature-scenarios";
    /**
     * The first part of the name of scenarios by feature pages.
     */
    public static final String FEATURE_SCENARIOS_PAGE_FRAGMENT = "/" + FEATURE_SCENARIOS_PAGE_PATH + "/feature_";
    /**
     * The name of the tree view page.
     */
    public static final String TREE_VIEW_PAGE = "tree-view";

    /**
     * Defines all possible start pages.
     */
    public enum StartPage {
        /**
         * The scenario overview page.
         */
        ALL_SCENARIOS(SCENARIO_SUMMARY_PAGE_PATH),
        /**
         * The scenario sequence page.
         */
        SCENARIO_SEQUENCE(SCENARIO_SEQUENCE_PAGE_PATH),
        /**
         * The tag overview page.
         */
        ALL_TAGS(TAG_SUMMARY_PAGE),
        /**
         * The step overview page.
         */
        ALL_STEPS(STEP_SUMMARY_PAGE),
        /**
         * The feature overview page.
         */
        ALL_FEATURES(FEATURE_SUMMARY_PAGE),
        /**
         * The tree view page.
         */
        TREE_VIEW(TREE_VIEW_PAGE);

        private final String pageName;

        StartPage(final String pageName) {
            this.pageName = pageName;
        }

        /**
         * Get the name of the page.
         *
         * @return The page name.
         */
        @SuppressWarnings("unused")
        public String getPageName() {
            return pageName;
        }
    }

    /**
     * Where to display custom parameters.
     */
    public enum CustomParamDisplayMode {
        /**
         * Display on all scenario pages.
         */
        SCENARIO_PAGES,
        /**
         * Display on all pages.
         */
        ALL_PAGES
    }
}
