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

        <@page.card width="3" title="Summary" subtitle="" classes="">
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
            <#if (element.tags?size > 0)>
                <hr>
                <#list element.tags as tag>
                    <a href="pages/tag-scenarios/tag_${tag.getUrlFriendlyName()}.html"
                       class="btn btn-link" style="word-break: break-all;">${tag.name}</a>
                </#list>
            </#if>
        </@page.card>

        <@page.card width="3" title="Scenario Info" subtitle="" classes="">
            <li class="list-group-item">
                <#if element.startTimestamp?has_content>
                    <b>Start</b><br>${element.startDateString} ${element.startTimeString}<br>
                </#if>
                <#if element.startTimestamp?has_content>
                    <b>End</b><br>${element.endDateString} ${element.endTimeString}<br>
                </#if>
                <b>Total</b><br>${element.returnTotalDurationString()}
            </li>
            <#assign numberOfChildren = element.getMultiRunChildren()?size>
            <#if groupPreviousScenarioRuns && element.multiRunParent>
                <hr>
                <div class="alert alert-secondary" role="alert">
                    Final of ${numberOfChildren + 1} ${common.pluralizeFn("run", numberOfChildren + 1)}, previous:
                    <ol style="list-style: none; padding: 0; margin: 0;" reversed>
                        <#list element.getMultiRunChildren() as childElement>
                            <li>
                                <a href="pages/scenario-detail/scenario_${childElement.scenarioIndex?c}.html"
                                   style="word-break: break-all">
                                    ${childElement.startDateString}, ${childElement.startTimeString}
                                    <@common.status status=childElement.status.statusString/>
                                </a>
                            </li>
                        </#list>
                    </ol>
                </div>
            </#if>
            <#if groupPreviousScenarioRuns && element.multiRunChild>
                <hr>
                <div class="alert alert-secondary" role="alert">
                    This is part
                    of ${element.getMultiRunParent().getMultiRunChildren()?size + 1}
                    ${common.pluralizeFn("run", element.getMultiRunParent().getMultiRunChildren()?size + 1)}.
                    <br>Latest: <a
                            href="pages/scenario-detail/scenario_${element.getMultiRunParent().scenarioIndex?c}.html">
                        ${element.getMultiRunParent().startDateString}, ${element.getMultiRunParent().startTimeString}
                        <@common.status status=element.getMultiRunParent().status.statusString/>
                    </a>
                </div>
            </#if>

            <#if (element.hasHooks() && element.hasHooksWithContent()) ||
            element.hasSubSections() ||
            (element.hasStepHooks() && element.hasStepHooksWithContent()) ||
            element.hasDocStrings()>
                <hr>
            </#if>

            <#if element.hasSubSections()>
                <button class="btn w-75 m-2" type="button" id="sub-sections-button"
                        data-cluecumber-item="sub-sections-button">${expandSubSections?then('Hide', 'Show')} Sub
                    Sections
                </button>
            </#if>
            <#if element.hasHooks() && element.hasHooksWithContent()>
                <button class="btn w-75 m-2" type="button" id="before-after-hooks-button"
                        data-cluecumber-item="before-after-hooks-button">${expandBeforeAfterHooks?then('Hide', 'Show')}
                    Hooks
                </button>
            </#if>
            <#if element.hasStepHooks() && element.hasStepHooksWithContent()>
                <button class="btn w-75 m-2" type="button" id="step-hooks-button"
                        data-cluecumber-item="step-hooks-button">${expandStepHooks?then('Hide', 'Show')} Step Hooks
                </button>
            </#if>
            <#if element.hasDocStrings()>
                <button class="btn w-75 m-2" type="button" id="doc-strings-button"
                        data-cluecumber-item="doc-strings-button">${expandDocStrings?then('Hide', 'Show')}
                    DocStrings
                </button>
            </#if>
            <#if element.hasAttachments()>
                <button class="btn w-75 m-2" type="button" id="attachments-button"
                        data-cluecumber-item="attachments-button">${expandAttachments?then('Hide', 'Show')}
                    Attachments
                </button>
            </#if>
            <#if element.hasOutputs()>
                <button class="btn w-75 m-2" type="button" id="outputs-button"
                        data-cluecumber-item="outputs-button">${expandOutputs?then('Hide', 'Show')} Step
                    Outputs
                </button>
            </#if>
        </@page.card>
    </div>

    <ul class="list-group list-group-flush">
        <#if (element.before?size > 0 && element.anyBeforeHookHasContent())>
            <div class="scenarioHook collapse ${expandBeforeAfterHooks?then("show", "")}">
                <@page.card width="12" title="Before Hooks" subtitle="" classes="">
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
                                    <@scenario.output step=before sectionId='before'/>
                                    <@scenario.attachments step=before/>
                                </div>
                            </#if>
                        </#list>
                    </li>
                </@page.card>
            </div>
        </#if>

        <#if (element.backgroundSteps?size > 0)>
            <@page.card width="12" title="Background Steps" subtitle="" classes="">
                <li class="list-group-item">

                    <#assign oldCollapseLevel = 0>
                    <#assign lastLevelChange = 0>
                    <#assign openDivs = 0>

                    <#list element.backgroundSteps as step>
                        <@scenario.stepHooks step.index step.before />

                        <#assign sectionChange = step.collapseLevel - oldCollapseLevel>
                        <#assign oldCollapseLevel = step.collapseLevel>

                        <#if (sectionChange > 0) >
                            <#list 1..sectionChange as n>
                                <#assign openDivs = openDivs + 1>
                                <div style="padding-left: 2em;" id="section_${step?counter}"
                                class="scenarioSubSection collapse ${expandSubSections?then("show", "")}">
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
                                        <button onclick="copyText('step_${step.index}_docstring')" type="button"
                                                class="btn-clipboard">
                                            Copy to clipboard
                                        </button>
                                        <div class="w-100 text-left border">
                                            <pre class="text-secondary small p-2">${step.docString.returnWithClickableLinks()}</pre>
                                        </div>
                                    </div>
                                </div>
                            </#if>
                            <@scenario.errorMessage step=step/>
                            <@scenario.output step=step sectionId='background'/>
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
                                <div style="padding-left: 2em;" id="section_${step?counter}"
                                class="scenarioSubSection collapse ${expandSubSections?then("show", "")}">
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
                                        <button onclick="copyText('step_${step.index}_docstring')" type="button"
                                                class="btn-clipboard">
                                            Copy to clipboard
                                        </button>
                                        <div class="w-100 text-left border">
                                            <pre class="text-secondary small p-2">${step.docString.returnWithClickableLinks()}</pre>
                                        </div>
                                    </div>
                                </div>
                            </#if>
                            <@scenario.errorMessage step=step/>
                            <@scenario.output step=step sectionId='main'/>
                            <@scenario.attachments step=step/>
                        </div>
                        <@scenario.stepHooks step.index step.after />
                    </#list>
                </li>
            </@page.card>
        </#if>

        <#if (element.after?size > 0 && element.anyAfterHookHasContent())>
            <div class="scenarioHook collapse ${expandBeforeAfterHooks?then("show", "")}">
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
                                    <@scenario.output step=after sectionId='after'/>
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
        let classState = {};

        function toggleHooks(className, defaultValue) {
            if (classState[className] === undefined) {
                classState[className] = defaultValue;
            }
            classState[className] = !classState[className];
            let rootElements = document.getElementsByClassName(className);
            for (const rootElement of rootElements) {
                if (classState[className]) {
                    rootElement.classList.add('show');
                } else {
                    rootElement.classList.remove('show');
                }
            }
        }

        function toggleCollapsableSection(className, defaultValue) {
            if (classState[className] === undefined) {
                classState[className] = defaultValue;
            }
            classState[className] = !classState[className];
            if (classState[className]) {
                $(className).collapse('show');
            } else {
                $(className).collapse('hide');
            }
        }

        function toggleCollapsableAttachments(className, defaultValue) {
            if (classState[className] === undefined) {
                classState[className] = defaultValue;
            }
            classState[className] = !classState[className];
            let rootElements = document.getElementsByClassName(className);
            for (const rootElement of rootElements) {
                let collapsableElements = rootElement.getElementsByClassName('collapse');
                for (const collapsableElement of collapsableElements) {
                    if (classState[className]) {
                        collapsableElement.classList.add('show');
                    } else {
                        collapsableElement.classList.remove('show');
                    }
                }
            }
        }

        <#if element.hasSubSections()>
        document.getElementById('sub-sections-button').addEventListener('click', function (event) {
            toggleCollapsableSection('.scenarioSubSection', ${expandSubSections?c});
            event.target.textContent = classState['.scenarioSubSection'] ? 'Hide Sub Sections' : 'Show Sub Sections';
        });
        </#if>

        <#if element.hasHooks() && element.hasHooksWithContent()>
        document.getElementById('before-after-hooks-button').addEventListener('click', function (event) {
            toggleHooks('scenarioHook', ${expandBeforeAfterHooks?c});
            event.target.textContent = classState['scenarioHook'] ? 'Hide Hooks' : 'Show Hooks';
        });
        </#if>

        <#if element.hasDocStrings()>
        document.getElementById('doc-strings-button').addEventListener('click', function (event) {
            toggleCollapsableSection('.scenarioDocstring', ${expandDocStrings?c});
            event.target.textContent = classState['.scenarioDocstring'] ? 'Hide DocStrings' : 'Show DocStrings';
        });
        </#if>
        <#if element.hasAttachments()>
        document.getElementById('attachments-button').addEventListener('click', function (event) {
            toggleCollapsableAttachments('scenarioAttachment', ${expandAttachments?c});
            event.target.textContent = classState['scenarioAttachment'] ? 'Hide Attachments' : 'Show Attachments';
        });
        </#if>
        <#if element.hasOutputs()>
        document.getElementById('outputs-button').addEventListener('click', function (event) {
            toggleCollapsableAttachments('scenarioOutputs', ${expandOutputs?c});
            event.target.textContent = classState['scenarioOutputs'] ? 'Hide Step Outputs' : 'Show Step Outputs';
        });
        </#if>

        <#if element.hasStepHooks() && element.hasStepHooksWithContent()>
        document.getElementById('step-hooks-button').addEventListener('click', function (event) {
            toggleCollapsableSection('.stepHook', ${expandStepHooks?c});
            event.target.textContent = classState['.stepHook'] ? 'Hide Step Hooks' : 'Show Step Hooks';
        });
        </#if>
    </script>
</@page.page>