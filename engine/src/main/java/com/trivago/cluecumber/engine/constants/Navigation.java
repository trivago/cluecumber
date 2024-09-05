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

import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.Link;
import com.trivago.cluecumber.engine.rendering.pages.pojos.pagecollections.LinkType;

import java.util.Arrays;
import java.util.List;

/**
 * Report navigation.
 */
public class Navigation {
    /**
     * The list of internal report navigation links.
     */
    public static final List<Link> internalLinks = Arrays.asList(
            new Link("scenario_summary", "pages/scenario-summary.html", LinkType.INTERNAL),
            new Link("rerun_scenarios", "pages/rerun-scenarios.html", LinkType.INTERNAL),
            new Link("scenario_sequence", "pages/scenario-sequence.html", LinkType.INTERNAL),
            new Link("tag_summary", "pages/tag-summary.html", LinkType.INTERNAL),
            new Link("step_summary", "pages/step-summary.html", LinkType.INTERNAL),
            new Link("feature_summary", "pages/feature-summary.html", LinkType.INTERNAL),
            new Link("tree_view", "pages/tree-view.html", LinkType.INTERNAL)
    );
}
