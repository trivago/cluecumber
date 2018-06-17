<#import "macros/page.ftl"as page>
<#import "macros/scenario.ftl" as scenarioMacros>
<#import "macros/navigation.ftl" as navigation>

<@page.page base=".." links=["tag_summary", "feature_summary", "suite_overview"]>
    <div class="row">
        <@page.card width="12" title="Tag Summary Result Chart" subtitle="">
            <div id="canvas-holder" class="w-100 text-center">
                <canvas id="chart-area" class="w-100"></canvas>
            </div>
        </@page.card>
    </div>

    <div class="row">
        <@page.card width="12" title="Tag Summary" subtitle="">
            <table id="tag_summary" class="table table-hover renderAsDataTable">
                <thead>
                <tr>
                    <th>Tag</th>
                    <th>Total</th>
                    <th>Passed</th>
                    <th>Failed</th>
                    <th>Skipped</th>
                </tr>
                </thead>
                <tbody>
                    <#list tagStats as tag, tagStat>
                    <tr>
                        <td class="text-left">${tag}</td>
                        <td class="text-right"><strong>${tagStat.total}</strong></td>
                        <td class="text-right">${tagStat.passed}</td>
                        <td class="text-right">${tagStat.failed}</td>
                        <td class="text-right">${tagStat.skipped}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </@page.card>
    </div>
</@page.page>
