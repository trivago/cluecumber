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
package com.trivago.cluecumber.engine.rendering.pages.visitors;

import com.trivago.cluecumber.engine.rendering.pages.charts.ChartJsonConverter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * List of visitors
 */
@Singleton
public class VisitorDirectory {

    private final ArrayList<PageVisitor> visitors;

    /**
     * Constructor for dependency injection.
     *
     * @param featureVisitor  The {@link FeatureVisitor} instance.
     * @param scenarioVisitor The {@link ScenarioVisitor} instance.
     * @param stepVisitor     The {@link StepVisitor} instance.
     * @param tagVisitor      The {@link TagVisitor} instance.
     */
    @Inject
    public VisitorDirectory(
            final ScenarioVisitor scenarioVisitor,
            final FeatureVisitor featureVisitor,
            final TagVisitor tagVisitor,
            final StepVisitor stepVisitor
    ) {
        visitors = new ArrayList<>();
        visitors.add(scenarioVisitor);
        visitors.add(featureVisitor);
        visitors.add(tagVisitor);
        visitors.add(stepVisitor);
    }

    /**
     * Retrieve the registered visitors.
     *
     * @return The list of {@link PageVisitor} instances.
     */
    public List<PageVisitor> getVisitors() {
        return visitors;
    }
}
