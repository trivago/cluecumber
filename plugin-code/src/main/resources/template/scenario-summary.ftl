<#import "macros/page.ftl"as page>
<#import "macros/scenario.ftl" as scenarioMacros>
<#import "macros/navigation.ftl" as navigation>

<@page.page base="." links=["feature_summary", "tag_summary"]>

    <@page.card width="12" title="Filters" subtitle="">
        <p>Tag: ${filterTag!"none"}</p>
        <p>Feature: ${filterFeature!"none"}</p>
    </@page.card>

    <#if hasCustomParameters()>
        <div class="row">
            <@page.card width="12" title="Custom Parameters" subtitle="">
                <ul class="list-group list-group-flush">
                <#list customParameters as customParameter>
                    <li class="list-group-item"><strong>${customParameter.key}:</strong>
                        <#if customParameter.url>
                            <a href="${customParameter.value}"
                               target="_blank">${customParameter.value}</a>
                        <#else>
                            ${customParameter.value}
                        </#if>
                    </li>
                </#list>
                </ul>
            </@page.card>
        </div>
    </#if>

    <div class="row">
        <@page.card width="7" title="Scenario Result Chart" subtitle="">
            <div id="canvas-holder" class="w-100 text-center">
                <canvas id="chart-area" class="w50"></canvas>
            </div>
        </@page.card>
        <@page.card width="5" title="Scenario Summary" subtitle="">
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><strong>${totalNumberOfScenarios}</strong> Scenarios in
                    <strong>${totalNumberOfFeatures}</strong> Features
                </li>
                <li class="list-group-item"><strong>${totalNumberOfPassedScenarios}</strong> passed /
                    <strong>${totalNumberOfFailedScenarios}</strong> failed /
                    <strong>${totalNumberOfSkippedScenarios}</strong> skipped
                </li>
                <li class="list-group-item"><strong>Total Time:</strong> ${totalDurationString}</li>
            </ul>
        </@page.card>
    </div>

    <@scenarioMacros.table status="failed"/>
    <@scenarioMacros.table status="skipped"/>
    <@scenarioMacros.table status="passed"/>
</@page.page>