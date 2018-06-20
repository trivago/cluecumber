<#import "macros/page.ftl"as page>
<#import "macros/scenario.ftl" as scenarioMacros>
<#import "macros/navigation.ftl" as navigation>

<#if (tagFilter??)>
    <#assign base = "./../..">
    <#assign headline = "Scenarios with Tag ${tagFilter.name}">
<#elseif (featureFilter??)>
    <#assign base = "./../..">
    <#assign headline = "Scenarios in Feature '${featureFilter.name}'">
<#else>
    <#assign base = ".">
    <#assign headline = "All Scenarios">
</#if>

<@page.page base=base links=["feature_summary", "tag_summary"] headline=headline>
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
        <@page.card width="8" title="Scenario Result Chart" subtitle="">
            <@page.graph />
        </@page.card>
        <@page.card width="4" title="Scenario Summary" subtitle="">
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><strong>${totalNumberOfScenarios}</strong> Scenarios</li>
                <li class="list-group-item"><strong>${totalNumberOfPassedScenarios}</strong> passed</li>
                <li class="list-group-item"><strong>${totalNumberOfFailedScenarios}</strong> failed</li>
                <li class="list-group-item"><strong>${totalNumberOfSkippedScenarios}</strong> skipped</li>
                <li class="list-group-item"><strong>Time:</strong> ${totalDurationString}</li>
            </ul>
        </@page.card>
    </div>

    <@scenarioMacros.table status="failed"/>
    <@scenarioMacros.table status="skipped"/>
    <@scenarioMacros.table status="passed"/>
</@page.page>