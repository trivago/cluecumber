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

<#import "page.ftl"as page>

<#macro card customParams>
    <div class="row" id="custom-parameters">
        <@page.card width="12" title="" subtitle="" classes="customParameters">
            <table class="table table-fit">
                <tbody>
                <#list customParams as customParameter>
                    <tr>
                        <#if !customParameter.key?starts_with(" ")>
                            <td class="text-left text-nowrap"><strong>${customParameter.key}:</strong></td>
                            <td class="text-left wrap">
                                <#if customParameter.url>
                                    <a href="${customParameter.value}" style="word-break: break-all;"
                                       target="_blank">${customParameter.value}</a>
                                <#else>
                                    ${customParameter.value}
                                </#if>
                            </td>
                        <#else>
                            <td class="text-left noKey" colspan="2">${customParameter.value}</td>
                        </#if>
                    </tr>
                </#list>
                </tbody>
            </table>
        </@page.card>
    </div>
</#macro>
