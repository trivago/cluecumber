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
        <@page.card width="3" title="Scenario Info" subtitle="" classes="">
            <#if element.startTimestamp?has_content>
                <li class="list-group-item">Started on:<br>${element.startDateString} ${element.startTimeString}</li>
            </#if>
            <#if element.startTimestamp?has_content>
                <li class="list-group-item">Ended on:<br>${element.endDateString} ${element.endTimeString}</li>
            </#if>
            <li class="list-group-item">Test Runtime:<br>${element.returnTotalDurationString()}</li>
            <li class="list-group-item"><#list element.tags as tag>
                    <a href="pages/tag-scenarios/tag_${tag.getUrlFriendlyName()}.html">${tag.name}</a><#sep>,
                </#list>
            </li>
        </@page.card>
        <@page.card width="3" title="Step Summary" subtitle="" classes="">
            <ul class="list-group list-group-flush">
                <li class="list-group-item">
                    ${element.totalNumberOfSteps} <@common.pluralize word="Step" unitCount=element.totalNumberOfSteps/>
                </li>
                <li class="list-group-item">
                    ${element.totalNumberOfPassedSteps} passed <@common.status status="passed"/>
                    <br>
                    ${element.totalNumberOfFailedSteps} failed <@common.status status="failed"/>
                    <br>
                    ${element.totalNumberOfSkippedSteps} skipped <@common.status status="skipped"/>
                </li>
            </ul>
            <#if element.hasHooks() && element.hasHooksWithContent()>
                <button class="btn btn-outline-secondary btn-block collapsed" type="button" data-toggle="collapse"
                        aria-expanded="true" data-cluecumber-item="before-after-hooks-button"
                        data-target=".scenarioHook">Scenario Hooks with content
                </button>
            </#if>
            <#if element.hasStepHooks() && element.hasStepHooksWithContent()>
                <button class="btn btn-outline-secondary btn-block collapsed" type="button" data-toggle="collapse"
                        aria-expanded="true" data-cluecumber-item="step-hooks-button"
                        data-target=".stepHook">Step Hooks with content
                </button>
            </#if>
            <#if element.hasDocStrings()>
                <button class="btn btn-outline-secondary btn-block collapsed" type="button" data-toggle="collapse"
                        aria-expanded="true" data-cluecumber-item="doc-strings-button"
                        data-target=".scenarioDocstring">DocStrings with content
                </button>
            </#if>
        </@page.card>
    </div>

    <ul class="list-group list-group-flush">
        <#if (element.before?size > 0 && element.anyBeforeHookHasContent())>
            <@page.card width="12" title="Before Hooks" subtitle="" classes="scenarioHook collapse">
                <li class="list-group-item">
                    <#list element.before as before>
                        <#if before.hasContent()>
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

                        <@scenario.stepHooks step.before />

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
                        <@scenario.stepHooks step.after />
                    </#list>
                </li>
            </@page.card>
        </#if>

        <#if (element.steps?size > 0)>
            <@page.card width="12" title="Steps" subtitle="" classes="">
                <li class="list-group-item">
                    <#list element.steps as step>

                        <@scenario.stepHooks step.before />

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
                        <@scenario.stepHooks step.after />
                    </#list>
                </li>
            </@page.card>
        </#if>

        <#if (element.after?size > 0 && element.anyAfterHookHasContent())>
            <div class="scenarioHook collapse">
                <@page.card width="12" title="After Hooks" subtitle="" classes="">
                    <li class="list-group-item">
                        <#list element.after as after>
                            <#if after.hasContent()>
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
</@page.page>