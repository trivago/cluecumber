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

<#macro page title base highlight headline subheadline subsubheadline preheadline preheadlineLink>
    <!--
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
    <!DOCTYPE html>
    <html lang="en">
    <base href="${base}">
    <head title="${title}">
        <title>${title}</title>
        <#include "../snippets/common_headers.ftl">
        <#include "../snippets/css.ftl">
        <#include "../snippets/js.ftl">
    </head>
    <body>
    <@navigation.build highlight=highlight />
    <main role="main" class="container">
        <div class="cluecumber-template">
            <div class="pb-2 mt-4 mb-2 border-bottom">
                <#if preheadline != "">
                    <h5>
                        <#if preheadlineLink != "">
                            <a href="${preheadlineLink}">${preheadline}</a>
                        <#else>
                            ${preheadline}
                        </#if>
                        <span class="small"><#if subsubheadline != "">${subsubheadline}</#if></span>
                    </h5>
                </#if>
                <#if headline != "">
                    <h3>${headline}</h3>
                </#if>
                <#if subheadline != "">
                    <h5>${subheadline}</h5>
                </#if>
            </div>
            <#nested>
        </div>
    </main>
    <#include "../snippets/footer.ftl">
    </body>
    </html>
</#macro>

<#macro card width title subtitle classes>
    <div class="col-sm-${width} ${classes} h-auto" data-cluecumber-item="card">
        <div class="card h-100">
            <#if title != "">
                <div class="card-header">${title}</div>
            </#if>
            <#if subtitle != "">
                <div class="card-header text-secondary">${subtitle}</div>
            </#if>
            <div class="card-body">
                <#nested>
            </div>
        </div>
    </div>
</#macro>

<#macro graph>
    <div id="canvas-holder" class="w-100 text-center" style="min-height: 15rem;" data-cluecumber-item="chart">
        <canvas id="chart-area" class="w-100"></canvas>
    </div>
</#macro>
