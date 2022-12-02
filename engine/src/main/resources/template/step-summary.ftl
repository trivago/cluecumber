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
<#import "macros/common.ftl" as common>
<#import "macros/navigation.ftl" as navigation>
<#import "macros/custom-parameters.ftl" as customparams>

<@page.page
title="${pageTitle} - All Steps"
base=".."
highlight="step_summary"
headline="All Steps"
subheadline=""
subsubheadline=""
preheadline=""
preheadlineLink="">

    <#if hasCustomParameters()>
        <@customparams.card customParams=customParameters/>
    </#if>

    <div class="row" id="step-summary">
        <@page.card width="9" title="Step Summary Result Chart" subtitle="" classes="">
            <@page.graph />
        </@page.card>
        <@page.card width="3" title="Steps Summary" subtitle="" classes="">
            <ul class="list-group list-group-flush" data-cluecumber-item="step-summary">
                <li class="list-group-item">
                    ${totalNumberOfSteps} <@common.pluralize word="Step" unitCount=totalNumberOfSteps/> in
                    <br>
                    ${totalNumberOfScenarios} <@common.pluralize word="Scenario" unitCount=totalNumberOfScenarios/>
                </li>
                <li class="list-group-item">
                    ${totalNumberOfPassed} passed <@common.status status="passed"/>
                    <br>
                    ${totalNumberOfFailed} failed <@common.status status="failed"/>
                    <br>
                    ${totalNumberOfSkipped} skipped<@common.status status="skipped"/>
                </li>
            </ul>
        </@page.card>
    </div>

    <div class="row" id="available-steps">
        <@page.card width="12" title="Available Steps" subtitle="" classes="">
            <table id="step_summary" class="table table-hover renderAsDataTable"
                   data-cluecumber-item="step-summary-table">
                <thead>
                <tr>
                    <th>Step</th>
                    <th>Total</th>
                    <th class="passedCell"><@common.status status="passed"/></th>
                    <th class="failedCell"><@common.status status="failed"/></th>
                    <th class="skippedCell"><@common.status status="skipped"/></th>
                    <th>Min Time</th>
                    <th>Max Time</th>
                    <th>Ø Time</th>
                </tr>
                </thead>
                <tbody>
                <#list stepResultCounts as step, stepResultCount>
                    <tr>
                        <td class="text-left">
                            <span data-toggle="tooltip" title="${step.glueMethodName}">
                                <a href="pages/step-scenarios/step_${step.getUrlFriendlyName()}.html">${step.returnNameWithArgumentPlaceholders()}</a>
                            </span>
                        </td>
                        <td class="text-right"><strong>${stepResultCount.total}</strong></td>
                        <td class="text-right passedCell">${stepResultCount.passed}</td>
                        <td class="text-right failedCell">${stepResultCount.failed}</td>
                        <td class="text-right skippedCell">${stepResultCount.skipped}</td>
                        <td class="text-right small">
                            <#if (getMinimumTimeScenarioIndexFromStep(step) > -1)>
                                <a href="pages/scenario-detail/scenario_${getMinimumTimeScenarioIndexFromStep(step)}.html">${getMinimumTimeFromStep(step)}</a>
                            </#if>
                        </td>
                        <td class="text-right small">
                            <#if (getMaximumTimeScenarioIndexFromStep(step) > -1)>
                                <a href="pages/scenario-detail/scenario_${getMaximumTimeScenarioIndexFromStep(step)}.html">${getMaximumTimeFromStep(step)}</a>
                            </#if>
                        </td>
                        <td class="text-right small">${getAverageTimeFromStep(step)}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </@page.card>
    </div>
</@page.page>
