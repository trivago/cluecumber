<#import "macros/page.ftl"as page>
<#import "macros/scenario.ftl" as scenarioMacros>
<#import "macros/navigation.ftl" as navigation>

<@page.page base=".." links=["feature_summary", "scenario_summary"] headline="All Tags">
    <div class="row">
        <@page.card width="8" title="Tag Summary Result Chart" subtitle="">
            <@page.graph />
        </@page.card>
        <@page.card width="4" title="Tag Summary" subtitle="">
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><strong>${totalNumberOfTags}</strong> Tags</li>
                <li class="list-group-item"><strong>${totalNumberOfPassedTags}</strong> passed Scenarios</li>
                <li class="list-group-item"><strong>${totalNumberOfFailedTags}</strong> failed Scenarios</li>
                <li class="list-group-item"><strong>${totalNumberOfSkippedTags}</strong> skipped Scenarios</li>
            </ul>
        </@page.card>
    </div>

    <div class="row">
        <@page.card width="12" title="Available Tags" subtitle="">
            <table index="tag_summary" class="table table-hover renderAsDataTable">
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
                    <#list tagResultCounts as tag, tagResultCount>
                    <tr>
                        <td class="text-left"><a
                                href="pages/tag-scenarios/tag_${tag.getUrlFriendlyName()}.html">${tag.name}</a></td>
                        <td class="text-right"><strong>${tagResultCount.total}</strong></td>
                        <td class="text-right">${tagResultCount.passed}</td>
                        <td class="text-right">${tagResultCount.failed}</td>
                        <td class="text-right">${tagResultCount.skipped}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </@page.card>
    </div>
</@page.page>
