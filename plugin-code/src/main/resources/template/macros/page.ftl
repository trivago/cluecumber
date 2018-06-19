<#macro page base links>
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
            <div class="card-header">${title}</div>
            <#if subtitle != "">
                <div class="card-header text-secondary">${subtitle}</div>
            </#if>
            <div class="card-body">
                <#nested>
            </div>
        </div>
    </div>
</#macro>

