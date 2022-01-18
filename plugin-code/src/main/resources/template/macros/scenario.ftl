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

<#import "../macros/common.ftl" as common>

<#macro table status numberOfScenarios>
    <#assign skippedRequested = status == "skipped">
    <#assign failedRequested = status == "failed">
    <#assign passedRequested = status == "passed">
    <#assign allRequested = status == "all">

    <#if
    (skippedRequested && hasSkippedScenarios()) ||
    (failedRequested && hasFailedScenarios()) ||
    (passedRequested && hasPassedScenarios()) ||
    allRequested
    >
        <a class="anchor" id="anchor-${status}"></a>
        <div class="row" id="card_${status}" data-cluecumber-item="scenario-summary-table">
            <div class=" col-sm-12">
                <div class="card">

                    <#switch status>
                        <#case "skipped">
                            <div class="card-header border-color-skipped">
                                ${numberOfScenarios}
                                <@common.pluralize word="skipped Scenario" unitCount=numberOfScenarios/>
                                <@common.status status="skipped"/>
                            </div>
                            <#break>
                        <#case "failed">
                            <div class="card-header border-color-failed">
                                ${numberOfScenarios}
                                <@common.pluralize word="failed Scenario" unitCount=numberOfScenarios/>
                                <@common.status status="failed"/>
                            </div>
                            <#break>
                        <#case "passed">
                            <div class="card-header border-color-passed">
                                ${numberOfScenarios}
                                <@common.pluralize word="passed Scenario" unitCount=numberOfScenarios/>
                                <@common.status status="passed"/>
                            </div>
                            <#break>
                        <#case "all">
                            <div class="card-header">
                                Scenario Sequence (${numberOfScenarios})
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
                                <th class="text-left">Feature</th>
                                <th class="text-left">Scenario</th>
                                <th>Started</th>
                                <th>Duration</th>
                                <#if allRequested>
                                    <th class="text-left">Status</th>
                                </#if>
                            </tr>
                            </thead>
                            <tbody>
                            <#list reports as report>
                                <#assign tooltipText = "">
                                <#if report.description?has_content>
                                    <#assign tooltipText = "${report.description} | ">
                                </#if>
                                <#assign tooltipText = "${tooltipText}${report.uri}">

                                <#list report.elements as element>
                                    <#if (skippedRequested && element.skipped) || (failedRequested && element.failed) || (passedRequested && element.passed) || allRequested>
                                        <tr class="table-row-${element.status.statusString}">
                                            <#if allRequested>
                                                <td class="text-right">${element.scenarioIndex}</td>
                                            </#if>
                                            <td class="text-left">
                                                <span data-toggle="tooltip" title="${tooltipText}">
                                                    <a href="pages/feature-scenarios/feature_${report.featureIndex?c}.html">${report.name?html}</a>
                                                </span>
                                            </td>
                                            <td class="text-left">
                                                <a href="pages/scenario-detail/scenario_${element.scenarioIndex?c}.html"
                                                   style="word-break: break-all">${element.name?html}</a>
                                            </td>
                                            <td class="text-center small" data-order="${element.startTimestamp}">
                                                ${element.startDateString}<br>${element.startTimeString}
                                            </td>
                                            <td class="text-right small"
                                                data-order="${element.totalDuration}">
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
                <div class="w-100 p-1 m-0 border-bottom small text-left">
                    <a class="btn-link" data-toggle="collapse" href="#expandable${attachmentID}" role="button"
                       aria-expanded="false" aria-controls="expandable${attachmentID}">Toggle</a> |
                    <#if attachment.name != "">
                        ${attachment.name} (${attachment.mimeType} attachment)
                    <#else>
                        ${attachment.mimeType} attachment
                    </#if>
                </div>
                <div class="w-100 text-left m-auto">
                    <div class="w-100 text-left m-auto collapse ${expandAttachments?then("show", "")}" id="expandable${attachmentID}">
                        <#if attachment.image>
                            <a class="grouped_elements" rel="images" href="attachments/${attachment.filename}">
                                <img src="attachments/${attachment.filename}" class="embedded-image"
                                     style="max-width: 50%;"
                                     alt="Attachment ${attachment.filename}"/>
                            </a>
                        <#elseif attachment.mimeType == "HTML">
                            <iframe src="attachments/${attachment.filename}"
                                    srcdoc="${attachment.decodedData}" width="100%" height="1"
                                    onload="resizeIframe(this);" class="embedded-html"></iframe>
                        <#elseif attachment.mimeType == "TXT" || attachment.mimeType == "XML" || attachment.mimeType == "JSON" || attachment.mimeType == "APPLICATION_XML">
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
            <div class="w-100 text-left border border-danger">
                <pre class="text-danger small p-2">${step.result.returnErrorMessageWithClickableLinks()}</pre>
            </div>
        </div>
    </#if>
</#macro>

<#macro  output step>
    <#if step.hasOutputs()>
        <div class="row w-100 p-3 m-0 scenarioOutput">
            <div class="w-100 text-left small p-2">${step.returnEscapedOutputs()?join("<br><br>")}</div>
        </div>
    </#if>
</#macro>

<#macro stepHooks hooks>
    <#list hooks as hook>
        <#if hook.hasContent()>
            <div class="stepHook collapse">
                <div class="row row_${hook.consolidatedStatusString} table-row-${hook.consolidatedStatusString}">
                    <div class="col-1"></div>
                    <div class="col-8 text-left">
                        <i>${hook.glueMethodName}</i>
                    </div>
                    <div class="col-2 text-left small">
                        <span class="nobr">${hook.result.returnDurationString()}</span>
                    </div>
                    <div class="col-1 text-right">
                        <@common.status status=hook.consolidatedStatusString/>
                    </div>
                    <@scenario.errorMessage step=hook/>
                    <@scenario.output step=hook/>
                    <@scenario.attachments step=hook/>
                </div>
            </div>
        </#if>
    </#list>
</#macro>
