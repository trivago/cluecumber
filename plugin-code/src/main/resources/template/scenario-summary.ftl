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

<#import "macros/page.ftl"as page>
<#import "macros/scenario.ftl" as scenarioMacros>
<#import "macros/navigation.ftl" as navigation>

<#if (tagFilter??)>
    <#assign base = "./../..">
    <#assign headline = "Scenarios Tagged With '${tagFilter.name}'">
    <#assign links = ["feature_summary", "tag_summary", "scenario_summary"]>
<#elseif (featureFilter??)>
    <#assign base = "./../..">
    <#assign headline = "Scenarios in Feature '${featureFilter.name}'">
    <#assign links = ["feature_summary", "tag_summary", "scenario_summary"]>
<#else>
    <#assign base = ".">
    <#assign headline = "All Scenarios">
    <#assign links = ["feature_summary", "tag_summary"]>
</#if>

<@page.page base=base links=links headline=headline>
    <#if hasCustomParameters()>
        <div class="row">
            <@page.card width="12" title="" subtitle="">
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
                <li class="list-group-item"><strong>${totalNumberOfScenarios}</strong> Scenario(s)</li>
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