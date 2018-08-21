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

<#import "../macros/common.ftl" as common>

<#macro table status>
    <#assign skippedRequested = status == "skipped">
    <#assign failedRequested = status == "failed">
    <#assign passedRequested = status == "passed">

    <#if (skippedRequested && hasSkippedScenarios()) || (failedRequested && hasFailedScenarios()) || (passedRequested && hasPassedScenarios())>
        <div class="row" id="card_${status}">
            <div class="col-sm-12">
                <div class="card">

                    <#switch status>
                        <#case "skipped">
                            <div class="card-header border-warning bg-warning">Skipped Scenarios</div>
                            <#break>
                        <#case "failed">
                            <div class="card-header border-danger bg-danger text-white">Failed Scenarios</div>
                            <#break>
                        <#case "passed">
                            <div class="card-header border-success bg-success text-white">Passed Scenarios</div>
                            <#break>
                    </#switch>

                    <div class="card-body">
                        <table id="results_${status}" class="table table-hover renderAsDataTable">
                            <thead>
                            <tr>
                                <th class="text-left">Feature</th>
                                <th class="text-left">Scenario</th>
                                <th>Duration</th>
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
                                        <#if (skippedRequested && element.skipped) || (failedRequested && element.failed) || (passedRequested && element.passed)>
                                            <tr>
                                                <td class="text-left"><span data-toggle="tooltip"
                                                                            title="${tooltipText}"><a
                                                        href="pages/feature-scenarios/feature_${report.featureIndex}.html">${report.name?html}</a></span>
                                                </td>
                                                <td class="text-left">
                                                    <a href="pages/scenario-detail/scenario_${element.scenarioIndex}.html">${element.name?html}</a>
                                                </td>
                                                <td class="text-right small"
                                                    data-order="${element.totalDuration}">
                                                    <nobr>${element.returnTotalDurationString()}</nobr>
                                                </td>
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
            <div class="row w-100 p-3 m-0">
                <div class="w-100 text-left m-auto">
                    <#if attachment.image>
                        <a class="grouped_elements" rel="images"
                           href="attachments/${attachment.filename}">
                            <img src="attachments/${attachment.filename}"
                                 style="max-width: 100%"/>
                        </a>
                    <#else>
                        ${attachment.data?html}
                    </#if>
                </div>
            </div>
        </#list>
    </#if>
</#macro>

<#macro errorMessage step>
    <#if step.result.hasErrorMessage()>
        <div class="row w-100 p-3 m-0">
            <div class="w-100 text-left border border-danger">
                <pre class="text-danger small p-2">${step.result.errorMessage?html}</pre>
            </div>
        </div>
    </#if>
</#macro>

<#macro output step>
    <#if step.output??>
        <#list step.output as output>
            <#if output?has_content>
                <div class="row w-100 p-3 m-0">
                    <div class="w-100 text-left m-auto border border-dark">
                        <iframe frameborder="0" srcdoc="${output?html}" width="100%" height="1"
                                scrolling="yes" onload="resizeIframe(this);"></iframe>
                    </div>
                </div>
            </#if>
        </#list>
    </#if>
</#macro>

<#macro stepHooks hooks>
    <#list hooks as hook>
        <#if (hook.failed)>
            <div class="row row_${hook.consolidatedStatusString}">
                <div class="col-1"></div>
                <div class="col-8 text-left">
                    <i>${hook.glueMethodName}</i>
                </div>
                <div class="col-2 text-left small">
                    <nobr>${hook.result.returnDurationString()}</nobr>
                </div>
                <div class="col-1 text-right">
                    <@common.status status=hook.consolidatedStatusString/>
                </div>
                <@scenario.errorMessage step=hook/>
                <@scenario.output step=hook/>
                <@scenario.attachments step=hook/>
            </div>
        </#if>
    </#list>
</#macro>