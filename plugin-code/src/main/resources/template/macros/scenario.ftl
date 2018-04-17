<#macro table status>
    <#assign skippedRequested = status == "skipped">
    <#assign failedRequested = status == "failed">
    <#assign passedRequested = status == "passed">

    <#if (skippedRequested && hasSkippedScenarios()) || (failedRequested && hasFailedScenarios()) || (passedRequested && hasPassedScenarios())>
        <div class="row">
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
                        <table id="results_skipped" class="table table-hover">
                            <thead>
                            <tr>
                                <th>Feature</th>
                                <th>Scenario</th>
                                <th>Duration</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list reports as report>
                                    <#list report.elements as element>
                                        <#if (skippedRequested && element.skipped) || (failedRequested && element.failed) || (passedRequested && element.passed)>
                                            <tr>
                                                <td class="text-left text-capitalize"><span data-toggle="tooltip"
                                                                                            title="${report.uri}">${report.name?html}</span>
                                                </td>
                                                <td class="text-left text-capitalize">
                                                    <a href="pages/scenario-detail/scenario_${element.scenarioIndex}.html">${element.name?html}</a>
                                                </td>
                                                <td class="text-left text-capitalize"
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