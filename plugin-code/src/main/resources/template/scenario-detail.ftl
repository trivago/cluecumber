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
<#import "macros/navigation.ftl" as navigation>

<@page.page base="../.." links=["feature_summary", "tag_summary", "scenario_summary"] headline="Scenario '${element.name?html}'" subheadline="${element.description?html}">

    <script>
        function resizeIframe(obj) {
            obj.style.height = (obj.contentWindow.document.body.scrollHeight + 20) + 'px';
        }
    </script>

    <div class="row">
        <@page.card width="8" title="Scenario Result Chart" subtitle="">
            <@page.graph />
        </@page.card>
        <@page.card width="4" title="Scenario Information" subtitle="">
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><strong>${element.totalNumberOfSteps}</strong> Step(s)</li>
                <li class="list-group-item"><strong>${element.totalNumberOfPassedSteps}</strong> passed</li>
                <li class="list-group-item"><strong>${element.totalNumberOfFailedSteps}</strong> failed</li>
                <li class="list-group-item"><strong>${element.totalNumberOfSkippedSteps}</strong> skipped</li>
                <li class="list-group-item"><strong>Time:</strong> ${element.returnTotalDurationString()}</li>
                <li class="list-group-item"><#list element.tags as tag>
                    <a href="pages/tag-scenarios/tag_${tag.getUrlFriendlyName()}.html">${tag.name}</a><#sep>,
                </#list>
                </li>
            </ul>
        </@page.card>
    </div>

    <ul class="list-group list-group-flush">
        <#if (element.before?size > 0)>
            <@page.card width="12" title="Before Hooks" subtitle="">
                <li class="list-group-item">
                    <#list element.before as before>
                        <div class="row row_${before.consolidatedStatusString}">
                            <div class="col-2 text-left">
                                <span class="text-secondary">
                                    <nobr>Before ${before?counter}</nobr>
                                </span>
                            </div>
                            <div class="col-6 text-left">
                                <i>${before.glueMethodName}</i>
                            </div>
                            <div class="col-2 text-left">
                                <nobr>${before.result.returnDurationString()}</nobr>
                            </div>
                            <div class="col-2 text-right">
                                <@scenario.status step=before/>
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
            <@page.card width="12" title="Steps" subtitle="">
                <li class="list-group-item">
                    <#list element.steps as step>

                        <@scenario.stepHooks step.before />

                        <div class="row row_${step.consolidatedStatusString}">
                            <div class="col-2 text-left">
                                <nobr>Step ${step?counter}</nobr>
                            </div>
                            <div class="col-6 text-left">
                                <#assign stepName=step.returnNameWithArguments()>
                                <span data-toggle="tooltip"
                                      title="${step.glueMethodName}">
                                    ${step.keyword} ${stepName}
                                </span>
                                <#if (step.rows?size > 0) >
                                    <table class="table table-hover table-sm compact">
                                        <#list step.rows as row>
                                            <tr>
                                                <#list row.cells as cell>
                                                    <td>${cell}</td>
                                                </#list>
                                            </tr>
                                        </#list>
                                    </table>
                                </#if>
                            </div>
                            <div class="col-2 text-left">
                                <nobr>${step.result.returnDurationString()}</nobr>
                            </div>
                            <div class="col-2 text-right">
                                <@scenario.status step=step/>
                            </div>
                            <#if (step.docString.value)?? >
                                <div class="row w-100 py-3 m-0">
                                    <div class="col-2"></div>
                                    <div class="col-10 text-left text-black-50">
                                        ${step.docString.value?html}
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
            <@page.card width="12" title="After Hooks" subtitle="">
                <li class="list-group-item">
                    <#list element.after as after>
                        <div class="row row_${after.consolidatedStatusString}">
                            <div class="col-2 text-left">
                                <span class="text-secondary">
                                    <nobr>After ${after?counter}</nobr>
                                </span>
                            </div>
                            <div class="col-6 text-left">
                                <i>${after.glueMethodName}</i>
                            </div>
                            <div class="col-2 text-left">
                                <nobr>${after.result.returnDurationString()}</nobr>
                            </div>
                            <div class="col-2 text-right">
                                <@scenario.status step=after/>
                            </div>
                        <@scenario.errorMessage step=after/>
                        <@scenario.output step=after/>
                        <@scenario.attachments step=after/>
                        </div>
                    </#list>
                </li>
            </@page.card>
        </#if>
    </ul>
</@page.page>