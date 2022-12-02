<#--
Copyright 2019 trivago N.V.

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
<#import "macros/common.ftl" as common>
<#import "macros/navigation.ftl" as navigation>
<#import "macros/custom-parameters.ftl" as customparams>

<@page.page
title="${pageTitle} - All Tags"
base=".."
highlight="tag_summary"
headline="All Tags"
subheadline=""
subsubheadline=""
preheadline=""
preheadlineLink="">

    <#if hasCustomParameters()>
        <@customparams.card customParams=customParameters/>
    </#if>

    <div class="row" id="tag-summary">
        <@page.card width="9" title="Tag Summary Result Chart" subtitle="" classes="">
            <@page.graph />
        </@page.card>
        <@page.card width="3" title="Tag Summary" subtitle="" classes="">
            <ul class="list-group list-group-flush" data-cluecumber-item="tag-summary">
                <li class="list-group-item">${totalNumberOfTags} <@common.pluralize word="Tag" unitCount=totalNumberOfTags/> in
                    <br>
                    ${totalNumberOfScenarios} Tagged <@common.pluralize word="Scenario" unitCount=totalNumberOfScenarios/>
                </li>
                <li class="list-group-item">
                    ${totalNumberOfPassed} passed <@common.status status="passed"/>
                    <br>
                    ${totalNumberOfFailed} failed <@common.status status="failed"/>
                    <br>
                    ${totalNumberOfSkipped} skipped <@common.status status="skipped"/>
                </li>
            </ul>
        </@page.card>
    </div>

    <div class="row" id="available-tags">
        <@page.card width="12" title="Available Tags" subtitle="" classes="">
            <table id="tag_summary" class="table table-hover renderAsDataTable" data-cluecumber-item="tag-summary-table">
                <thead>
                <tr>
                    <th>Tag</th>
                    <th>Total</th>
                    <th class="passedCell"><@common.status status="passed"/></th>
                    <th class="failedCell"><@common.status status="failed"/></th>
                    <th class="skippedCell"><@common.status status="skipped"/></th>
                </tr>
                </thead>
                <tbody>
                <#list tagResultCounts as tag, tagResultCount>
                    <tr>
                        <td class="text-left"><a
                                    href="pages/tag-scenarios/tag_${tag.getUrlFriendlyName()}.html">${tag.name}</a></td>
                        <td class="text-right"><strong>${tagResultCount.total}</strong></td>
                        <td class="text-right passedCell">${tagResultCount.passed}</td>
                        <td class="text-right failedCell">${tagResultCount.failed}</td>
                        <td class="text-right skippedCell">${tagResultCount.skipped}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </@page.card>
    </div>
</@page.page>
