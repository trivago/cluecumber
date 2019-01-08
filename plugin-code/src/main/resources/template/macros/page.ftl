<#--
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

<#macro page base links headline subheadline>
    <!DOCTYPE html>
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
    <html lang="en">
    <base href="${base}">
    <head>
        <#include "../snippets/common_headers.ftl">
        <#include "../snippets/css.ftl">
        <#include "../snippets/js.ftl">
    </head>
    <body>
        <@navigation.build links=links />
        <main role="main" class="container">
            <div class="cluecumber-template">
                <div class="pb-2 mt-4 mb-2 border-bottom">
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
    <div class="col-sm-${width} ${classes} h-auto">
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
    <div id="canvas-holder" class="w-100 text-center">
        <canvas id="chart-area" class="w-100"></canvas>
    </div>
</#macro>