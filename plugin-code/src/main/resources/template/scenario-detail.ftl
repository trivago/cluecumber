<#--
Copyright 2018 trivago N.V.

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

<@page.page
base="../.."
links=["feature_summary", "tag_summary", "scenario_sequence", "scenario_summary"]
headline="${element.name?html}"
subheadline="${element.description?html}"
preheadline="${element.featureName?html}"
preheadlineLink="pages/feature-scenarios/feature_${element.featureIndex?c}.html">

    <div class="row">
        <@page.card width="8" title="Scenario Result Chart" subtitle="" classes="">
            <@page.graph />
        </@page.card>
        <@page.card width="4" title="Scenario Information" subtitle="" classes="">
            <ul class="list-group list-group-flush">
                <li class="list-group-item">${element.totalNumberOfSteps} Step(s)</li>
                <li class="list-group-item">
                    ${element.totalNumberOfPassedSteps} <@common.status status="passed"/>
                    ${element.totalNumberOfFailedSteps} <@common.status status="failed"/>
                    ${element.totalNumberOfSkippedSteps} <@common.status status="skipped"/>
                </li>
                <li class="list-group-item">Duration: ${element.returnTotalDurationString()}</li>
                <li class="list-group-item"><#list element.tags as tag>
                        <a href="pages/tag-scenarios/tag_${tag.getUrlFriendlyName()}.html">${tag.name}</a><#sep>,
                    </#list>
                </li>
            </ul>
            <#if element.hasHooks()>
                <button class="btn btn-outline-secondary btn-block collapsed" type="button" data-toggle="collapse"
                        aria-expanded="true"
                        data-target=".scenarioHook">Before/After Hooks
                </button>
            </#if>
            <#if element.hasStepHooks()>
                <button class="btn btn-outline-secondary btn-block collapsed" type="button" data-toggle="collapse"
                        aria-expanded="true"
                        data-target=".stepHook">Step Hooks
                </button>
            </#if>
            <#if element.hasDocStrings()>
                <button class="btn btn-outline-secondary btn-block collapsed" type="button" data-toggle="collapse"
                        aria-expanded="true"
                        data-target=".scenarioDocstring">DocStrings
                </button>
            </#if>
        </@page.card>
    </div>

    <ul class="list-group list-group-flush">
        <#if (element.before?size > 0)>
            <@page.card width="12" title="Before Hooks" subtitle="" classes="scenarioHook collapse">
                <li class="list-group-item">
                    <#list element.before as before>
                        <div class="row row_${before.consolidatedStatusString}">
                            <div class="col-1 text-left">${before?counter}.</div>
                            <div class="col-8 text-left">
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
                    </#list>
                </li>
            </@page.card>
        </#if>

        <#if (element.steps?size > 0)>
            <@page.card width="12" title="Steps" subtitle="" classes="">
                <li class="list-group-item">
                    <#list element.steps as step>

                        <@scenario.stepHooks step.before />

                        <div class="row row_${step.consolidatedStatusString}">
                            <div class="col-1 text-left">${step?counter}.</div>
                            <div class="col-8 text-left">
                                <#assign stepName=step.returnNameWithArguments()>
                                <span data-toggle="tooltip"
                                      title="${step.glueMethodName}">
                                    ${step.keyword} ${stepName}
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

        <#if (element.after?size > 0)>
            <div class="scenarioHook collapse">
                <@page.card width="12" title="After Hooks" subtitle="" classes="">
                    <li class="list-group-item">
                        <#list element.after as after>
                            <div class="row row_${after.consolidatedStatusString}">
                                <div class="col-1 text-left">${after?counter}.</div>
                                <div class="col-8 text-left">
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
                        </#list>
                    </li>
                </@page.card>
            </div>
        </#if>
    </ul>
</@page.page>