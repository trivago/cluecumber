<#macro page base links headline>
    <!--
    <#include "../snippets/license.ftl">
    -->
    <!DOCTYPE html>
    <html lang="en">
    <base href="${base}">
    <head>
        <#include "../snippets/common_headers.ftl">
        <#include "../snippets/css.ftl">
    </head>
    <body>
        <@navigation.build links=links />
    <main role="main" class="container">
        <div class="cluecumber-template">
            <div class="pb-2 mt-4 mb-2 border-bottom">
                <h3>${headline}</h3>
            </div>
            <#nested>
        </div>
    </main>
        <#include "../snippets/footer.ftl">
        <#include "../snippets/js.ftl">
    </body>
    </html>
</#macro>

<#macro card width title subtitle>
    <div class="col-sm-${width}">
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