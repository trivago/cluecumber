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

<#import "../macros/common.ftl" as common>

<#macro table status numberOfScenarios>
    <#assign skippedRequested = status == "skipped">
    <#assign failedRequested = status == "failed">
    <#assign passedRequested = status == "passed">
    <#assign allRequested = status == "all">

    <#if  (skippedRequested && hasSkippedScenarios()) ||
    (failedRequested && hasFailedScenarios()) ||
    (passedRequested && hasPassedScenarios()) ||
    allRequested>
        <a class="anchor" id="anchor-${status}"></a>
        <div class="row" id="card_${status}" data-cluecumber-item="scenario-summary-table">
            <div class=" col-sm-12">
                <div class="card shadow">

                    <#switch status>
                        <#case "skipped">
                            <div class="card-header border-color-skipped">
                                ${numberOfScenarios}
                                ${common.pluralizeFn("skipped Scenario", numberOfScenarios)}
                                <@common.status status="skipped"/>
                            </div>
                            <#break>
                        <#case "failed">
                            <div class="card-header border-color-failed">
                                ${numberOfScenarios}
                                ${common.pluralizeFn("failed Scenario", numberOfScenarios)}
                                <@common.status status="failed"/>
                            </div>
                            <#break>
                        <#case "passed">
                            <div class="card-header border-color-passed">
                                ${numberOfScenarios}
                                ${common.pluralizeFn("passed Scenario", numberOfScenarios)}
                                <@common.status status="passed"/>
                            </div>
                            <#break>
                        <#case "all">
                            <div class="card-header">
                                ${numberOfScenarios} ${common.pluralizeFn("Scenario", numberOfScenarios)}
                            </div>
                            <#break>
                    </#switch>

                    <div class="card-body">
                        <table id="results_${status}" class="table table-hover renderAsDataTable">
                            <thead>
                            <tr>
                                <#if allRequested>
                                    <th class="text-left">#</th>
                                </#if>
                                <th class="text-left" style="width: 20%;">Feature</th>
                                <th class="text-left">Scenario</th>
                                <th class="text-center">Started</th>
                                <th class="text-center">Duration</th>
                                <#if allRequested>
                                    <th class="text-center">Status</th>
                                </#if>
                            </tr>
                            </thead>
                            <tbody>
                            <#assign counter = 0>
                            <#list reports as report>
                                <#assign tooltipText = "">
                                <#if report.description?has_content>
                                    <#assign tooltipText = "${report.description} | ">
                                </#if>
                                <#assign tooltipText = "${tooltipText}${report.uri}">

                                <#list report.elements as element>
                                    <#assign counter = counter + 1>
                                    <#if (skippedRequested && element.skipped) || (failedRequested && element.failed) || (passedRequested && element.passed) || allRequested>
                                        <tr class="table-row-${element.status.statusString}">
                                            <#if allRequested>
                                                <td class="text-right">${counter}</td>
                                            </#if>
                                            <td class="text-left">
                                                <span data-toggle="tooltip" title="${tooltipText}">
                                                    <a href="pages/feature-scenarios/feature_${report.featureIndex?c}.html">${report.name?html}</a>
                                                </span>
                                            </td>
                                            <td class="text-left">
                                                <a href="pages/scenario-detail/scenario_${element.scenarioIndex?c}.html"
                                                   style="word-break: break-all">${element.name?html}</a>

                                                <#if element.isMultiRunParent()>
                                                    <button type="button" class="btn-clipboard multiRunExpansionButton"
                                                            data-toggle="collapse"
                                                            aria-expanded="false"
                                                            data-target="#multiRun_${element.scenarioIndex}">Previous
                                                        Runs
                                                    </button>
                                                </#if>

                                                <#if element.firstExceptionSummary != "">
                                                    <p class="firstException text-left small text-gray"
                                                       style="word-break: break-word">${element.firstExceptionSummary}</p>
                                                </#if>

                                                <#if element.isMultiRunParent()>
                                                    <div id="multiRun_${element.scenarioIndex}"
                                                         class="multiRunChildren collapse">
                                                        <ol type="a" reversed>
                                                            <#list element.getMultiRunChildren() as childElement>
                                                                <li>
                                                                    <a href="pages/scenario-detail/scenario_${childElement.scenarioIndex?c}.html"
                                                                       style="word-break: break-all">Previous run from
                                                                        ${childElement.startDateString}
                                                                        , ${childElement.startTimeString}
                                                                        <@common.status status=childElement.status.statusString/>
                                                                    </a>
                                                                    <#if childElement.firstExceptionSummary != "">
                                                                        <p class="firstException text-left small text-gray"
                                                                           style="word-break: break-word">${childElement.firstExceptionSummary}</p>
                                                                    </#if>
                                                                </li>
                                                            </#list>
                                                        </ol>
                                                    </div>
                                                </#if>
                                            </td>
                                            <td class="text-center small" data-order="${element.startTimestamp}">
                                                ${element.startDateString}<br>${element.startTimeString}
                                            </td>
                                            <td class="text-center small" data-order="${element.totalDuration}">
                                                <span class="nobr">${element.returnTotalDurationString()}</span>
                                            </td>
                                            <#if allRequested>
                                                <td class="text-center"><@common.status status=element.status.statusString/></td>
                                            </#if>
                                        </tr>
                                    </#if>
                                </#list>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </#if>
</#macro>

<#macro attachments step>
    <#if step.embeddings??>
        <#list step.embeddings as attachment>
            <div class="row w-100 p-3 m-0 scenarioAttachment">
                <#assign attachmentID = attachment.hashCode()?string["0"]>
                <#assign isTextAttachment = attachment.mimeType == "TXT" || attachment.mimeType == "XML" || attachment.mimeType == "JSON" || attachment.mimeType == "APPLICATION_XML">
                <div class="w-100 p-1 m-0 border-bottom small text-left">
                    <a class="btn-link" data-toggle="collapse" href="#expandable${attachmentID}" role="button"
                       aria-expanded="false" aria-controls="expandable${attachmentID}">Toggle</a> |
                    <#if attachment.name != "">
                        ${attachment.name} (${attachment.mimeType} attachment)
                    <#else>
                        ${attachment.mimeType} attachment
                    </#if>
                    <#if isTextAttachment>
                        <button onclick="copyText('expandable${attachmentID}')" type="button" class="btn-clipboard">
                            Copy to clipboard
                        </button>
                    </#if>
                </div>
                <div class="w-100 text-left m-auto">
                    <div class="w-100 text-left m-auto collapse ${expandAttachments?then("show", "")}"
                         id="expandable${attachmentID}">
                        <#if attachment.image>
                            <a class="grouped_elements" rel="images" href="attachments/${attachment.filename}">
                                <img src="attachments/${attachment.filename}" class="embedded-image"
                                     style="max-width: 45%; border: grey solid 1px; margin: 10px;"
                                     alt="Attachment ${attachment.filename}"/>
                            </a>
                        <#elseif attachment.mimeType == "HTML">
                            <iframe src="attachments/${attachment.filename}"
                                    srcdoc="${attachment.decodedData}" width="100%" height="1"
                                    onload="resizeIframe(this);" class="embedded-html"></iframe>
                        <#elseif isTextAttachment>
                            <pre class="embedding-content small embedded-txt">${attachment.decodedData}</pre>
                        <#elseif attachment.mimeType == "MP4">
                            <#if attachment.externalContent>
                                <video controls="controls" class="embedded-mp4" style="max-width:50%;">
                                    <source src="${attachment.decodedData}" type="video/mp4"/>
                                </video>
                            </#if>
                        <#elseif attachment.mimeType == "UNKNOWN">
                            <p class="small text-danger">Unknown content type.</p>
                        <#else>
                            <embed src="attachments/${attachment.filename}" class="embedded-file" width="100%"
                                   height="500"/>
                        </#if>
                    </div>
                </div>
            </div>
        </#list>
    </#if>
</#macro>

<#macro errorMessage step>
    <#if step.result.hasErrorMessage()>
        <div class="row w-100 p-3 m-0 scenarioErrorMessage">
            <button onclick="copyText('${step.index!0}_errorMessage')" type="button" class="btn-clipboard">
                Copy to clipboard
            </button>
            <div class="w-100 text-left border border-danger" id="${step.index!0}_errorMessage">
                <pre class="text-danger small p-2">${step.result.returnErrorMessageWithClickableLinks()}</pre>
            </div>
        </div>
    </#if>
</#macro>

<#macro output step sectionId>
    <#if step.hasOutputs()>
        <div class="row w-100 p-3 m-0 scenarioOutputs">
            <div class="w-100 p-1 m-0 border-bottom small text-left">
                <a class="btn-link" data-toggle="collapse" href="#expandableOutput${step.index!0}_${sectionId}"
                   role="button"
                   aria-expanded="false" aria-controls="expandableOutput${step.index!0}_${sectionId}">Toggle</a> |
                Step Output
            </div>
            <div class="w-100 text-left m-auto">
                <div class="w-100 text-left-sm m-auto collapse ${expandOutputs?then("show", "")}"
                     id="expandableOutput${step.index!0}_${sectionId}">
                    <button onclick="copyText('expandableOutput${step.index!0}_${sectionId}')" type="button" class="btn-clipboard">
                        Copy to clipboard
                    </button>
                    <pre class="embedding-content small embedded-txt">${step.output?join("<br>")}</pre>
                </div>
            </div>
        </div>
    </#if>
</#macro>

<#macro stepHooks stepIndex hooks>
    <#list hooks as hook>
        <#if hook.hasContent()>
            <div class="stepHook collapse ${expandStepHooks?then("show", "")}" id="step_${stepIndex}_stepHooks">
                <div class="row row_${hook.consolidatedStatusString} table-row-${hook.consolidatedStatusString}">
                    <div class="col-9 text-left">
                        <i>${hook.glueMethodName}</i>
                    </div>
                    <div class="col-2 text-left small">
                        <span class="nobr">${hook.result.returnDurationString()}</span>
                    </div>
                    <div class="col-1 text-right">
                        <@common.status status=hook.consolidatedStatusString/>
                    </div>
                    <@scenario.errorMessage step=hook/>
                    <@scenario.output step=hook sectionId='hook'/>
                    <@scenario.attachments step=hook/>
                </div>
            </div>
        </#if>
    </#list>
</#macro>
