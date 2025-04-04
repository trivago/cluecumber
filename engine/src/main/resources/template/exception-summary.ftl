<#--
Copyright 2023 trivago N.V.

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
title="${pageTitle} - All Exceptions"
base=".."
highlight="exception_summary"
headline="All Exceptions"
subheadline=""
subsubheadline=""
preheadline=""
preheadlineLink="">

    <#if hasCustomParameters()>
        <@customparams.card customParams=customParameters/>
    </#if>

    <div class="row" id="exception-summary">
        <@page.card width="9" title="Exception Summary Result Chart" subtitle="" classes="">
            <@page.graph />
        </@page.card>
        <@page.card width="3" title="Exception Summary" subtitle="" classes="">
            <ul class="list-group list-group-flush" data-cluecumber-item="exception-summary">
                <li class="list-group-item">${totalNumberOfExceptions} ${common.pluralizeFn("Exception", totalNumberOfExceptions)} in
                    <br>
                    ${totalNumberOfScenarios} ${common.pluralizeFn("Scenario", totalNumberOfScenarios)}
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

    <div class="row" id="available-exceptions">
        <@page.card width="12" title="${totalNumberOfExceptions} ${common.pluralizeFn('Exception', totalNumberOfExceptions)}" subtitle="" classes="">
            <table id="exception_summary" class="table table-hover renderAsDataTable" data-cluecumber-item="exception-summary-table">
                <thead>
                <tr>
                    <th>Exception</th>
                    <th>Total</th>
                    <th class="passedCell"><@common.status status="passed"/></th>
                    <th class="failedCell"><@common.status status="failed"/></th>
                    <th class="skippedCell"><@common.status status="skipped"/></th>
                </tr>
                </thead>
                <tbody>
                <#list exceptionResultCounts as exception, exceptionResultCount>
                    <tr>
                        <td class="text-left"><a
                                    href="pages/exception-scenarios/exception_${exception}.html">${exception}</a></td>
                        <td class="text-center"><strong>${exceptionResultCount.total}</strong></td>
                        <td class="text-center passedCell">${exceptionResultCount.passed}</td>
                        <td class="text-center failedCell">${exceptionResultCount.failed}</td>
                        <td class="text-center skippedCell">${exceptionResultCount.skipped}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </@page.card>
    </div>
</@page.page>
