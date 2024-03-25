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
<#import "macros/scenario.ftl" as scenario>
<#import "macros/common.ftl" as common>
<#import "macros/navigation.ftl" as navigation>
<#import "macros/custom-parameters.ftl" as customparams>

<#if (tagFilter??)>
    <#assign base = "./../..">
    <#assign headline = "Scenarios tagged with '${tagFilter.name}'">
    <#assign pageName = "Tagged Scenarios">
    <#assign highlight = "tag_summary">
    <#assign subheadline = "">
    <#assign subsubheadline = "">
    <#assign preheadline = "">
    <#assign preheadlineLink = "">
<#elseif (featureFilter??)>
    <#assign base = "./../..">
    <#assign headline = "Scenarios in Feature">
    <#assign pageName = "Scenarios in Feature">
    <#assign highlight = "feature_summary">
    <#assign subheadline = "${featureFilter.description?html}">
    <#assign subsubheadline = "${featureFilter.uri}">
    <#assign preheadline = "${featureFilter.name}">
    <#assign preheadlineLink="pages/feature-scenarios/feature_${featureFilter.index?c}.html">
<#elseif (stepFilter??)>
    <#assign base = "./../..">
    <#assign headline = "Scenarios using Step '${stepFilter.returnNameWithArgumentPlaceholders()}'">
    <#assign pageName = "Scenarios with Step">
    <#assign highlight = "step_summary">
    <#assign subheadline = "">
    <#assign subsubheadline = "">
    <#assign preheadline = "">
    <#assign preheadlineLink = "">
<#elseif (scenarioSequence??)>
    <#assign base = "./..">
    <#assign headline = "Scenario Sequence">
    <#assign pageName = "Scenario Sequence">
    <#assign highlight = "scenario_sequence">
    <#assign subheadline = "">
    <#assign subsubheadline = "">
    <#assign preheadline = "">
    <#assign preheadlineLink = "">
<#else>
    <#assign base = "./..">
    <#assign headline = "All Scenarios">
    <#assign pageName = "All Scenarios">
    <#assign highlight = "scenario_summary">
    <#assign subheadline = "">
    <#assign subsubheadline = "">
    <#assign preheadline = "">
    <#assign preheadlineLink = "">
</#if>

<@page.page
title="${pageTitle} - ${pageName}"
base=base
highlight=highlight
headline=headline
subheadline=subheadline
subsubheadline=subsubheadline
preheadline=preheadline
preheadlineLink=preheadlineLink>

    <#if hasCustomParameters()>
        <@customparams.card customParams=customParameters/>
    </#if>

    <div class="row" id="scenario-summary">
        <@page.card width="6" title="Scenario Results" subtitle="" classes="">
            <@page.graph />
        </@page.card>
        <@page.card width="3" title="Scenario Summary" subtitle="" classes="">
            <ul class="list-group list-group-flush">
                <li class="list-group-item" data-cluecumber-item="scenario-summary">
                    ${totalNumberOfScenarios} ${common.pluralizeFn("Scenario", totalNumberOfScenarios)}
                </li>
                <li class="list-group-item" data-cluecumber-item="scenario-summary">
                    <#if (scenarioSequence??)>
                        ${totalNumberOfPassedScenarios} passed <@common.status status="passed"/>
                        <br>
                        ${totalNumberOfFailedScenarios} failed <@common.status status="failed"/>
                        <br>
                        ${totalNumberOfSkippedScenarios} skipped <@common.status status="skipped"/>
                    <#else>
                        <a href="javascript:"
                           onclick="document.location.hash='anchor-passed';">${totalNumberOfPassedScenarios}
                            passed</a> <@common.status status="passed"/>
                        <br>
                        <a href="javascript:"
                           onclick="document.location.hash='anchor-failed';">${totalNumberOfFailedScenarios}
                            failed</a> <@common.status status="failed"/>
                        <br>
                        <a href="javascript:"
                           onclick="document.location.hash='anchor-skipped';">${totalNumberOfSkippedScenarios}
                            skipped</a> <@common.status status="skipped"/>
                    </#if>
                </li>
            </ul>
            <#if isGroupPreviousScenarioRuns() && hasNotLastRunScenarios() && !(scenarioSequence??)>
                <hr>
                <button class="btn w-75 m-2 collapsed" type="button" data-toggle="collapse"
                        aria-expanded="true" data-cluecumber-item="show-not-last-runs-button"
                        data-target=".notLastRun">Toggle ${totalNumberOfNotLastScenariosRuns} Previous Test Runs
                </button>
            </#if>
        </@page.card>
        <@page.card width="3" title="Test Suite Info" subtitle="" classes="">
            <ul class="list-group list-group-flush">
                <#assign startDateTimeString = returnStartDateTimeString()>
                <li class="list-group-item" data-cluecumber-item="total-start">
                    <#if startDateTimeString?has_content>
                        <b>Start</b><br>${startDateTimeString}<br>
                    </#if>
                    <#assign endDateTimeString = returnEndDateTimeString()>
                    <#if endDateTimeString?has_content>
                        <b>End</b><br>${endDateTimeString}<br>
                    </#if>
                    <b>Total</b><br>${totalDurationString}
                </li>
            </ul>
        </@page.card>
    </div>

    <#if (scenarioSequence??)>
        <@scenario.table status="all" numberOfScenarios=totalNumberOfScenarios />
    <#else>
        <@scenario.table status="failed" numberOfScenarios=totalNumberOfFailedScenarios />
        <@scenario.table status="skipped" numberOfScenarios=totalNumberOfSkippedScenarios />
        <@scenario.table status="passed" numberOfScenarios=totalNumberOfPassedScenarios />
    </#if>
</@page.page>
