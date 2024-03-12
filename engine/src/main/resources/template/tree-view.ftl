<#--
Copyright 2023 trivago N.V.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->

<#import "macros/page.ftl"as page>
<#import "macros/common.ftl" as common>
<#import "macros/navigation.ftl" as navigation>

<@page.page
title="${pageTitle} - Tree View"
base=".."
highlight="tree_view"
headline="Feature and Scenario Tree"
subheadline=""
subsubheadline=""
preheadline=""
preheadlineLink="">

    <div class="row" id="tree-view">
        <@page.card width="12" title="${numberOfFeatures} ${common.pluralizeFn('Feature', numberOfFeatures)} with ${numberOfScenarios} ${common.pluralizeFn('Scenario', numberOfScenarios)}" subtitle="" classes="">
            <ul>
                <#list elements as feature, scenarios>
                    <#assign tooltipText = "">
                    <#if feature.description?has_content>
                        <#assign tooltipText = "${feature.description} | ">
                    </#if>
                    <#assign tooltipText = "${tooltipText}${feature.uri}">
                    <li>
                        <span data-toggle="tooltip" title="${tooltipText}">
                            <a href="pages/feature-scenarios/feature_${feature.index?c}.html"><strong>${feature.name?html}</strong></a>
                        </span>
                    </li>
                    <ol type="1">
                        <#list scenarios as scenario>
                            <li style="list-style-type: decimal;"><a
                                        href="pages/scenario-detail/scenario_${scenario.scenarioIndex?c}.html"
                                        style="word-break: break-all">${scenario.name?html}${scenario.isNotLastOfMultipleScenarioRuns?string(' [Rerun]', '')}</a>
                            </li>
                        </#list>
                    </ol>
                    <hr>
                </#list>
            </ul>
        </@page.card>
    </div>
</@page.page>
