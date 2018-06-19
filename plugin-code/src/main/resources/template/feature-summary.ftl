<#import "macros/page.ftl"as page>
<#import "macros/scenario.ftl" as scenarioMacros>
<#import "macros/navigation.ftl" as navigation>

<@page.page base=".." links=["tag_summary", "scenario_summary"]>
    <div class="row">
        <@page.card width="12" title="Feature Summary Result Chart" subtitle="">
            <div id="canvas-holder" class="w-100 text-center">
                <canvas id="chart-area" class="w-100"></canvas>
            </div>
        </@page.card>
    </div>

    <div class="row">
        <@page.card width="12" title="Feature Summary" subtitle="">
            <table id="feature_summary" class="table table-hover renderAsDataTable">
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
                        <td class="text-left"><a href="feature-scenarios/feature-${feature.id}.htm">${feature.name}</a></td>
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
