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

<@page.page
title="${pageTitle} - Scenario Detail"
base="../.."
highlight=""
headline="${element.name?html}"
subheadline="${element.description?html}"
subsubheadline="${element.featureUri}"
preheadline="${element.featureName?html}"
preheadlineLink="pages/feature-scenarios/feature_${element.featureIndex?c}.html">

    <#if hasCustomParameters()>
        <@customparams.card customParams=customParameters/>
    </#if>

    <div class="row" id="scenario-detail">
        <@page.card width="6" title="Step Results" subtitle="" classes="">
            <@page.graph />
        </@page.card>

        <@page.card width="3" title="Step Summary" subtitle="" classes="">
            <ul class="list-group list-group-flush">
                <li class="list-group-item">
                    ${element.totalNumberOfSteps} ${common.pluralizeFn("Step", element.totalNumberOfSteps)}
                </li>
                <li class="list-group-item">
                    ${element.totalNumberOfPassedSteps} passed <@common.status status="passed"/>
                    <br>
                    ${element.totalNumberOfFailedSteps} failed <@common.status status="failed"/>
                    <br>
                    ${element.totalNumberOfSkippedSteps} skipped <@common.status status="skipped"/>
                </li>
            </ul>
        </@page.card>

        <@page.card width="3" title="Scenario Info" subtitle="" classes="">
            <#if element.startTimestamp?has_content>
                <li class="list-group-item">Started on:<br>${element.startDateString} ${element.startTimeString}</li>
            </#if>
            <#if element.startTimestamp?has_content>
                <li class="list-group-item">Ended on:<br>${element.endDateString} ${element.endTimeString}</li>
            </#if>
            <li class="list-group-item">Test Runtime:<br>${element.returnTotalDurationString()}</li>
            <#if groupPreviousScenarioRuns && element.getIsLastOfMultipleScenarioRuns()>
                <div class="alert alert-info" role="alert">
                    This is the last run of this scenario.
                </div>
            </#if>
            <#if groupPreviousScenarioRuns && element.getIsNotLastOfMultipleScenarioRuns()>
                <div class="alert alert-info" role="alert">
                    There are later runs of the same scenario.
                </div>
            </#if>

            <#if element.hasHooks() && element.hasHooksWithContent()>
                <hr>
                <button class="btn-clipboard" type="button"
                        data-cluecumber-item="before-after-hooks-button"
                        onclick="expandAll('.scenarioHook')">Open Hooks
                </button>
                <button class="btn-clipboard" type="button"
                        data-cluecumber-item="before-after-hooks-button"
                        onclick="collapseAll('.scenarioHook')">Close Hooks
                </button>
            </#if>
            <#if element.hasSubSections()>
                <hr>
                <button class="btn-clipboard" type="button"
                        data-cluecumber-item="sub-sections-button"
                        onclick="expandAll('.scenarioSubSection')">Open Sub Sections
                </button>
                <button class="btn-clipboard" type="button"
                        data-cluecumber-item="sub-sections-button"
                        onclick="collapseAll('.scenarioSubSection')">Close Sub Sections
                </button>
            </#if>
            <#if element.hasStepHooks() && element.hasStepHooksWithContent()>
                <hr>
                <button class="btn-clipboard" type="button"
                        data-cluecumber-item="step-hooks-button"
                        onclick="expandAll('.stepHook')">Open Step Hooks
                </button>
                <button class="btn-clipboard" type="button"
                        data-cluecumber-item="step-hooks-button"
                        onclick="collapseAll('.stepHook')">Close Step Hooks
                </button>
            </#if>
            <#if element.hasDocStrings()>
                <hr>
                <button class="btn-clipboard" type="button"
                        data-cluecumber-item="doc-strings-button"
                        onclick="expandAll('.scenarioDocstring')">Open DocStrings
                </button>
                <button class="btn-clipboard" type="button"
                        data-cluecumber-item="doc-strings-button"
                        onclick="collapseAll('.scenarioDocstring')">Close DocStrings
                </button>
            </#if>
        </@page.card>
    </div>

    <ul class="list-group list-group-flush">

        <#if (element.tags?size > 0)>
            <@page.card width="12" title="Tags" subtitle="" classes="tags">
                <li class="list-group-item">
                    <#list element.tags as tag>
                        <a href="pages/tag-scenarios/tag_${tag.getUrlFriendlyName()}.html"
                           class="btn btn-link" style="word-break: break-all;">${tag.name}</a><#sep>
                    </#list>
                </li>
            </@page.card>
        </#if>

        <#if (element.before?size > 0 && element.anyBeforeHookHasContent())>
            <@page.card width="12" title="Before Hooks" subtitle="" classes="scenarioHook collapse">
                <li class="list-group-item">
                    <#list element.before as before>
                        <#if before.hasContent() || before.isFailed()>
                            <div class="row row_${before.consolidatedStatusString} table-row-${before.consolidatedStatusString}">
                                <div class="col-9 text-left">
                                    <span class="text-left">${before?counter}.</span>
                                    <i>${before.glueMethodName}</i>
                                </div>
                                <div class="col-2 text-left small">
                                    ${before.result.returnDurationString()}
                                </div>
                                <div class="col-1 text-right">
                                    <@common.status status=before.consolidatedStatusString/>
                                </div>
                                <@scenario.errorMessage step=before/>
                                <@scenario.output step=before/>
                                <@scenario.attachments step=before/>
                            </div>
                        </#if>
                    </#list>
                </li>
            </@page.card>
        </#if>

        <#if (element.backgroundSteps?size > 0)>
            <@page.card width="12" title="Background Steps" subtitle="" classes="">
                <li class="list-group-item">
                    <#list element.backgroundSteps as step>

                        <@scenario.stepHooks step.index step.before />

                        <div class="row row_${step.consolidatedStatusString} table-row-${step.consolidatedStatusString}">
                            <div class="col-9 text-left">
                                <span class="text-left">${step?counter}.</span>
                                <#assign stepName=step.returnNameWithArguments()>
                                <span data-toggle="tooltip" title="${step.glueMethodName}">
                                    <a href="pages/step-scenarios/step_${step.getUrlFriendlyName()}.html"><span
                                                class="keyword">${step.keyword}</span> ${stepName}</a>
                                </span>
                            </div>
                            <div class="col-2 text-left small">
                                ${step.result.returnDurationString()}
                            </div>
                            <div class="col-1 text-right">
                                <@common.status status=step.consolidatedStatusString/>
                            </div>

                            <#if (step.rows?size > 0) >
                                <div class="row w-100 p-3 m-0 scenarioDataTable">
                                    <div class="w-100 text-left border border-dark table-responsive">
                                        <table class="table table-hover small table-striped text-left pb-0">
                                            <#list step.rows as row>
                                                <tr>
                                                    <#list row.cells as cell>
                                                        <td>${cell}</td>
                                                    </#list>
                                                </tr>
                                            </#list>
                                        </table>
                                    </div>
                                </div>
                            </#if>
                            <#if (step.docString.value)?? >
                                <div class="scenarioDocstring collapse">
                                    <div class="row w-100 p-3 m-0">
                                        <div class="w-100 text-left border">
                                            <pre class="text-secondary small p-2">${step.docString.returnWithClickableLinks()}</pre>
                                        </div>
                                    </div>
                                </div>
                            </#if>
                            <@scenario.errorMessage step=step/>
                            <@scenario.output step=step/>
                            <@scenario.attachments step=step/>
                        </div>
                        <@scenario.stepHooks step.index step.after />
                    </#list>
                </li>
            </@page.card>
        </#if>

        <#if (element.steps?size > 0)>
            <@page.card width="12" title="Steps" subtitle="" classes="">
                <li class="list-group-item">

                    <#assign oldCollapseLevel = 0>
                    <#assign lastLevelChange = 0>
                    <#assign openDivs = 0>

                    <#list element.steps as step>
                        <@scenario.stepHooks step.index step.before />

                        <#assign sectionChange = step.collapseLevel - oldCollapseLevel>
                        <#assign oldCollapseLevel = step.collapseLevel>

                        <#if (sectionChange > 0) >
                            <#list 1..sectionChange as n>
                                <#assign openDivs = openDivs + 1>
                                <div style="margin-left: 2em;" id="section_${step?counter}"
                                class="scenarioSubSection collapse">
                            </#list>
                        <#elseif (sectionChange < 0) >
                            <#list sectionChange..-1 as n>
                                </div>
                            </#list>
                        </#if>

                        <div class="row row_${step.consolidatedStatusString} table-row-${step.consolidatedStatusString}">

                            <div class="col-9 text-left">
                                <span class="text-left">${step?counter}.</span>
                                <#assign stepName=step.returnNameWithArguments()>
                                <span data-toggle="tooltip" title="${step.glueMethodName}">
                                    <a href="pages/step-scenarios/step_${step.getUrlFriendlyName()}.html"><span
                                                class="keyword">${step.keyword}</span> ${stepName}</a>
                                </span>
                                <#if (step.hasSubSections())>
                                    <button type="button" class="btn-clipboard sectionExpansionButton"
                                            data-toggle="collapse"
                                            aria-expanded="false"
                                            data-target="#section_${step?counter + 1}">Sub Section
                                    </button>
                                </#if>
                                <#if (step.docString.value)?? >
                                    <button type="button" class="btn-clipboard docstringExpansionButton"
                                            data-toggle="collapse"
                                            aria-expanded="false"
                                            data-target="#step_${step.index}_docstring">DocString
                                    </button>
                                </#if>
                                <#if (step.hasHooksWithContent()) >
                                    <button type="button" class="btn-clipboard stepHooksExpansionButton"
                                            data-toggle="collapse"
                                            aria-expanded="false"
                                            data-target="#step_${step.index}_stepHooks">Step Hooks
                                    </button>
                                </#if>
                            </div>
                            <div class="col-2 text-left small">
                                ${step.result.returnDurationString()}
                            </div>
                            <div class="col-1 text-right">
                                <@common.status status=step.consolidatedStatusString/>
                            </div>

                            <#if (step.rows?size > 0) >
                                <div class="row w-100 p-3 m-0 scenarioDataTable">
                                    <div class="w-100 text-left border border-dark table-responsive">
                                        <table class="table table-hover small table-striped text-left pb-0">
                                            <#list step.rows as row>
                                                <tr>
                                                    <#list row.cells as cell>
                                                        <td>${cell}</td>
                                                    </#list>
                                                </tr>
                                            </#list>
                                        </table>
                                    </div>
                                </div>
                            </#if>
                            <#if (step.docString.value)?? >
                                <div class="scenarioDocstring collapse" id="step_${step.index}_docstring">
                                    <div class="row w-100 p-3 m-0">
                                        <div class="w-100 text-left border">
                                            <pre class="text-secondary small p-2">${step.docString.returnWithClickableLinks()}</pre>
                                        </div>
                                    </div>
                                </div>
                            </#if>
                            <@scenario.errorMessage step=step/>
                            <@scenario.output step=step/>
                            <@scenario.attachments step=step/>
                        </div>
                        <@scenario.stepHooks step.index step.after />
                    </#list>
                </li>
            </@page.card>
        </#if>

        <#if (element.after?size > 0 && element.anyAfterHookHasContent())>
            <div class="scenarioHook collapse">
                <@page.card width="12" title="After Hooks" subtitle="" classes="">
                    <li class="list-group-item">
                        <#list element.after as after>
                            <#if after.hasContent() || after.isFailed()>
                                <div class="row row_${after.consolidatedStatusString} table-row-${after.consolidatedStatusString}">
                                    <div class="col-9 text-left">
                                        <span class="text-left">${after?counter}.</span>
                                        <i>${after.glueMethodName}</i>
                                    </div>
                                    <div class="col-2 text-left small">
                                        ${after.result.returnDurationString()}
                                    </div>
                                    <div class="col-1 text-right">
                                        <@common.status status=after.consolidatedStatusString/>
                                    </div>
                                    <@scenario.errorMessage step=after/>
                                    <@scenario.output step=after/>
                                    <@scenario.attachments step=after/>
                                </div>
                            </#if>
                        </#list>
                    </li>
                </@page.card>
            </div>
        </#if>
    </ul>

    <script>
        function expandAll(className) {
            $(className).collapse('show');
        }

        function collapseAll(className) {
            $(className).collapse('hide');
        }
    </script>
</@page.page>