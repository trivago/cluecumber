<#--
Copyright 2019 trivago N.V.

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

<#if (tagFilter??)>
    <#assign base = "./../..">
    <#assign headline = "Scenarios Tagged With '${tagFilter.name}'">
    <#assign pageName = "Tagged Scenarios">
    <#assign highlight = "tag_summary">
    <#assign subheadline = "">
<#elseif (featureFilter??)>
    <#assign base = "./../..">
    <#assign headline = "Scenarios in Feature '${featureFilter.name}'">
    <#assign pageName = "Scenarios in Feature">
    <#assign highlight = "feature_summary">
    <#assign subheadline = "${featureFilter.description?html}">
<#elseif (stepFilter??)>
    <#assign base = "./../..">
    <#assign headline = "Scenarios using Step '${stepFilter.returnNameWithArgumentPlaceholders()}'">
    <#assign pageName = "Scenarios with Step">
    <#assign highlight = "step_summary">
    <#assign subheadline = "">
<#elseif (scenarioSequence??)>
    <#assign base = "./..">
    <#assign headline = "Scenario Sequence">
    <#assign pageName = "Scenario Sequence">
    <#assign highlight = "scenario_sequence">
    <#assign subheadline = "">
<#else>
    <#assign base = ".">
    <#assign headline = "All Scenarios">
    <#assign pageName = "All Scenarios">
    <#assign highlight = "scenario_summary">
    <#assign subheadline = "">
</#if>

<@page.page
title="${pageTitle} - ${pageName}"
base=base
highlight=highlight
headline=headline
subheadline=subheadline
preheadline=""
preheadlineLink="">

    <#if hasCustomParameters()>
        <div class="row" id="custom-parameters">
            <@page.card width="12" title="" subtitle="" classes="customParameters">
                <table class="table table-fit">
                    <tbody>
                    <#list customParameters as customParameter>
                        <tr>
                            <#if !customParameter.key?starts_with(" ")>
                                <td class="text-left text-nowrap"><strong>${customParameter.key}:</strong></td>
                                <td class="text-left wrap">
                                    <#if customParameter.url>
                                        <a href="${customParameter.value}" style="word-break: break-all;"
                                           target="_blank">${customParameter.value}</a>
                                    <#else>
                                        ${customParameter.value}
                                    </#if>
                                </td>
                            <#else>
                                <td class="text-left noKey" colspan="2">${customParameter.value}</td>
                            </#if>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </@page.card>
        </div>
    </#if>

    <div class="row" id="scenario-summary">
        <@page.card width="6" title="Scenario Results" subtitle="" classes="">
            <@page.graph />
        </@page.card>
        <@page.card width="3" title="Test Suite Time" subtitle="" classes="">
            <ul class="list-group list-group-flush">
                <#assign startDateTimeString = returnStartDateTimeString()>
                <#if startDateTimeString?has_content>
                    <li class="list-group-item" data-cluecumber-item="total-start">
                        Started on:<br>${startDateTimeString}</li>
                </#if>
                <#assign endDateTimeString = returnEndDateTimeString()>
                <#if endDateTimeString?has_content>
                    <li class="list-group-item" data-cluecumber-item="total-end">
                        Ended on:<br>${endDateTimeString}</li>
                </#if>
                <li class="list-group-item" data-cluecumber-item="total-runtime">
                    Test Runtime:<br>${totalDurationString}
                </li>
            </ul>
        </@page.card>
        <@page.card width="3" title="Test Suite Summary" subtitle="" classes="">
            <ul class="list-group list-group-flush">
                <li class="list-group-item" data-cluecumber-item="scenario-summary">
                    ${totalNumberOfScenarios} <@common.pluralize word="Scenario" unitCount=totalNumberOfScenarios/>
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