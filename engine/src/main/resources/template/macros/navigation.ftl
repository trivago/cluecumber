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
<#macro build highlight>
    <nav class="navbar navbar-expand-lg navbar-dark fixed-top">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
            <#list navigationLinks as link>
                <li class="nav-item">
                    <#assign highlightClass="">
                    <#if link.name == highlight>
                        <#assign highlightClass="text-white">
                    </#if>
                    <#if link.type == "INTERNAL">
                        <#switch link.name>
                            <#case "scenario_summary">
                                <#assign linkName="All Scenarios">
                                <#break>
                            <#case "scenario_sequence">
                                <#assign linkName="Scenario Sequence">
                                <#break>
                            <#case "tag_summary">
                                <#assign linkName="All Tags">
                                <#break>
                            <#case "step_summary">
                                <#assign linkName="All Steps">
                                <#break>
                            <#case "feature_summary">
                                <#assign linkName="All Features">
                                <#break>
                            <#case "tree_view">
                                <#assign linkName="Tree View">
                                <#break>
                        </#switch>
                        <a class="nav-link ${highlightClass}" href="${link.target}">${linkName}</a>
                    <#elseif link.type == "EXTERNAL">
                        <#if link.target??>
                            <a class="nav-link customLink" href="${link.target}" target="_blank">${link.name}</a>
                        </#if>
                    </#if>
                </li>
            </#list>
            </ul>
            <span class="text-light">${reportDetails.date}</span>
        </div>
    </nav>
</#macro>