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
title="${pageTitle} - Tree View"
base=".."
highlight="tree_view"
headline="Feature and Scenario Tree"
subheadline=""
subsubheadline=""
preheadline=""
preheadlineLink="">

    <#if hasCustomParameters()>
        <@customparams.card customParams=customParameters/>
    </#if>

    <div class="row" id="available-features">
        <@page.card width="12" title="Available Features and Scenarios" subtitle="" classes="">
            <table id="feature_summary" class="table table-hover renderAsDataTable">
                <thead>
                <tr>
                    <th>Feature</th>
                    <th>Total</th>
                    <th class="passedCell"><@common.status status="passed"/></th>
                    <th class="failedCell"><@common.status status="failed"/></th>
                    <th class="skippedCell"><@common.status status="skipped"/></th>
                </tr>
                </thead>
                <tbody>
                <#list featureResultCounts as feature, featureResultCount>
                    <tr>
                        <td class="text-left"><a
                                    href="pages/feature-scenarios/feature_${feature.index?c}.html">${feature.name}</a>
                        </td>
                        <td class="text-right"><strong>${featureResultCount.total}</strong></td>
                        <td class="text-right passedCell">${featureResultCount.passed}</td>
                        <td class="text-right failedCell">${featureResultCount.failed}</td>
                        <td class="text-right skippedCell">${featureResultCount.skipped}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </@page.card>
    </div>
</@page.page>
