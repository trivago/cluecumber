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

import com.trivago.cluecumber.engine.rendering.pages.renderering.TreeNode;

/**
 * Page collection for the tree view page.
 */
public class TreeViewPageCollection extends PageCollection {
    private final TreeNode rootTreeNode;
    private final int numberOfFeatures;
    private final int numberOfScenarios;

    /**
     * Constructor.
     *
     * @param rootTreeNode The root node of the tree view.
     * @param pageTitle    The title of the tree view page.
     */
    public TreeViewPageCollection(final TreeNode rootTreeNode,
                                  final String pageTitle,
                                  final int numberOfFeatures,
                                  final int numberOfScenarios) {
        super(pageTitle);
        this.rootTreeNode = rootTreeNode;
        this.numberOfFeatures = numberOfFeatures;
        this.numberOfScenarios = numberOfScenarios;
    }

    public TreeNode getRootTreeNode() {
        return rootTreeNode;
    }

    public int getNumberOfFeatures(){
        return numberOfFeatures;
    }

    public int getNumberOfScenarios(){
        return numberOfScenarios;
    }
}

