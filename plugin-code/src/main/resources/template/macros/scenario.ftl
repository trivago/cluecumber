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
                                                <td class="text-right"
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
            <div class="row col-12">
                <div class="col-1"></div>
                <div class="col-10 text-left">
                    <#if attachment.image>
                        <a class="grouped_elements" rel="images"
                           href="attachments/${attachment.filename}">
                            <img src="attachments/${attachment.filename}"
                                 style="width: 100%"/>
                        </a>
                    <#else>
                        ${attachment.data?html}
                    </#if>
                </div>
                <div class="col-1"></div>
            </div>
        </#list>
    </#if>
</#macro>

<#macro status step>
    <#if step.failed>
        <#assign class = "text-danger" />
    <#elseif step.skipped>
        <#assign class = "text-warning" />
    <#else>
        <#assign class = "text-success" />
    </#if>
    <span class="${class}">${step.status.statusString}</span>
</#macro>

<#macro errorMessage step>
    <#if step.result.hasErrorMessage()>
        <div class="row col-12">
            <div class="col-1"></div>
            <div class="col-10 text-left border border-danger">
                <code>${step.result.errorMessage?html}</code>
            </div>
            <div class="col-1"></div>
        </div>
    </#if>
</#macro>

<#macro output step>
    <#if step.output??>
        <#list step.output as output>
            <#if output?has_content>
                <div class="row col-12">
                    <div class="col-1"></div>
                    <div class="col-10 text-left">
                        <iframe srcdoc="${output?html}" width="100%" height="1"
                                scrolling="yes" onload="resizeIframe(this);"></iframe>
                    </div>
                    <div class="col-1"></div>
                </div>
            </#if>
        </#list>
    </#if>
</#macro>