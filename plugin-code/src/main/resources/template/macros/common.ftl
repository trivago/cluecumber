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

<#macro status status>
    <#if status == "failed">
        <#assign class = "color-failed" />
        <#assign icon = "failed" />
    <#elseif status == "skipped">
        <#assign class = "color-skipped" />
        <#assign icon = "skipped" />
    <#else>
        <#assign class = "color-passed" />
        <#assign icon = "passed" />
    </#if>
    <i class="${class} cluecumber-icon icon-${icon}" data-toggle="tooltip"
       title="${status}"><span style="display:none">${status}</span></i>
</#macro>

<#macro pluralize word unitCount>
    <#if unitCount gt 1>
        ${word}s
    <#else>
        ${word}
    </#if>
</#macro>