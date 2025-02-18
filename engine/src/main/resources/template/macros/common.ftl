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

<#macro status status>
    <#if status == "failed">
        <#assign colorClass = "color-failed" />
    <#elseif status == "skipped">
        <#assign colorClass = "color-skipped" />
    <#else>
        <#assign colorClass = "color-passed" />
    </#if>
    <i class="${colorClass} cluecumber-icon icon-${status}" data-toggle="tooltip"
       title="${status}"><span style="display:none">${status}</span></i>
</#macro>

<#function pluralizeFn word unitCount>
    <#if unitCount == 0>
        <#return "${word}s" />
    <#elseif unitCount gt 1>
        <#return "${word}s" />
    <#else>
        <#return "${word}" />
    </#if>
</#function>