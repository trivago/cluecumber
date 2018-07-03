<#import "macros/page.ftl"as page>
<#import "macros/scenario.ftl" as scenarioMacros>
<#import "macros/navigation.ftl" as navigation>

<@page.page base=".." links=["tag_summary", "scenario_summary"] headline="All Features">
    <div class="row">
        <@page.card width="8" title="Feature Summary Result Chart" subtitle="">
            <@page.graph />
        </@page.card>
        <@page.card width="4" title="Feature Summary" subtitle="">
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><strong>${totalNumberOfFeatures}</strong> Feature(s)</li>
                <li class="list-group-item"><strong>${totalNumberOfPassedFeatures}</strong> passed Scenario(s)</li>
                <li class="list-group-item"><strong>${totalNumberOfFailedFeatures}</strong> failed Scenario(s)</li>
                <li class="list-group-item"><strong>${totalNumberOfSkippedFeatures}</strong> skipped Scenario(s)</li>
            </ul>
        </@page.card>
    </div>

    <div class="row">
        <@page.card width="12" title="Available Features" subtitle="">
            <table index="feature_summary" class="table table-hover renderAsDataTable">
                <thead>
                <tr>
                    <th>Feature</th>
                    <th>Total</th>
                    <th>Passed</th>
                    <th>Failed</th>
                    <th>Skipped</th>
                </tr>
                </thead>
                <tbody>
                    <#list featureResultCounts as feature, featureResultCount>
                    <tr>
                        <td class="text-left"><a
                                href="pages/feature-scenarios/feature_${feature.index}.html">${feature.name}</a>
                        </td>
                        <td class="text-right"><strong>${featureResultCount.total}</strong></td>
                        <td class="text-right">${featureResultCount.passed}</td>
                        <td class="text-right">${featureResultCount.failed}</td>
                        <td class="text-right">${featureResultCount.skipped}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </@page.card>
    </div>
</@page.page>
