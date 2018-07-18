<!--
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

<@page.page base=".." links=["feature_summary", "scenario_summary"] headline="All Tags">
    <div class="row">
        <@page.card width="8" title="Tag Summary Result Chart" subtitle="">
            <@page.graph />
        </@page.card>
        <@page.card width="4" title="Tag Summary" subtitle="">
            <ul class="list-group list-group-flush">
                <li class="list-group-item"><strong>${totalNumberOfTags}</strong> Tag(s)</li>
                <li class="list-group-item"><strong>${totalNumberOfPassedTags}</strong> passed Scenario(s)</li>
                <li class="list-group-item"><strong>${totalNumberOfFailedTags}</strong> failed Scenario(s)</li>
                <li class="list-group-item"><strong>${totalNumberOfSkippedTags}</strong> skipped Scenario(s)</li>
            </ul>
        </@page.card>
    </div>

    <div class="row">
        <@page.card width="12" title="Available Tags" subtitle="">
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
