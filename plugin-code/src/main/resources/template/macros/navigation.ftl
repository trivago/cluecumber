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
<#macro build highlight>
    <nav class="navbar navbar-expand-lg navbar-dark fixed-top">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <#assign links = ['scenario_summary', 'scenario_sequence', 'tag_summary', 'step_summary', 'feature_summary']>
                <#list links as link>
                    <li class="nav-item">
                        <#assign highlightClass="">
                        <#if link == highlight>
                            <#assign highlightClass="text-white">
                        </#if>
                        <#switch link>
                            <#case "scenario_summary">
                                <a class="nav-link ${highlightClass}" href="pages/scenario-summary.html">All Scenarios</a>
                                <#break>
                            <#case "scenario_sequence">
                                <a class="nav-link ${highlightClass}" href="pages/scenario-sequence.html">Scenario Sequence</a>
                                <#break>
                            <#case "tag_summary">
                                <a class="nav-link ${highlightClass}" href="pages/tag-summary.html">All Tags</a>
                                <#break>
                            <#case "step_summary">
                                <a class="nav-link ${highlightClass}" href="pages/step-summary.html">All Steps</a>
                                <#break>
                            <#case "feature_summary">
                                <a class="nav-link ${highlightClass}" href="pages/feature-summary.html">All Features</a>
                                <#break>
                        </#switch>
                    </li>
                </#list>
            </ul>
            <span class="text-light">${reportDetails.date}</span>
        </div>
    </nav>
</#macro>