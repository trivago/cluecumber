<#macro table status>
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
                <#if
                (status == "skipped" && element.skipped) ||
                (status == "failed" && element.failed) ||
                (status == "passed" && element.passed)>
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
</#macro>