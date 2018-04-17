<!-- <#include "snippets/license.ftl"> -->

<#import "macros/scenario.ftl" as scenarioMacros>
<#import "macros/navigation.ftl" as navigation>

<!DOCTYPE html>
<html lang="en">
<head>
    <base href=".">
    <#include "snippets/common_headers.ftl">
    <#include "snippets/css.ftl">
</head>
<body>

<@navigation.build links=["tag_summary"] />

<main role="main" class="container">
    <div class="cluecumber-template">
        <#if hasCustomParameters()>
            <div class="row">
                <div class="col-sm-12">
                    <div class="card h-100">
                        <div class="card-header">Custom Parameters</div>
                        <div class="card-body">
                            <ul class="list-group list-group-flush">
                                <#list customParameters as customParameter>
                                    <li class="list-group-item"><strong>${customParameter.key}:</strong>
                                        <#if customParameter.url>
                                            <a href="${customParameter.value}"
                                               target="_blank">${customParameter.value}</a>
                                        <#else>
                                            ${customParameter.value}
                                        </#if>
                                    </li>
                                </#list>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </#if>

        <div class="row">
            <div class="col-sm-6">
                <div class="card h-100">
                    <div class="card-header">Test Suite Result Chart</div>
                    <div class="card-body">
                        <div id="canvas-holder" class="w-100 text-center">
                            <canvas id="chart-area" class="w50"></canvas>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="card h-100">
                    <div class="card-header">Test Suite Summary</div>
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"><strong>${totalNumberOfScenarios}</strong> Scenarios in
                                <strong>${totalNumberOfFeatures}</strong>
                                Features.
                            </li>
                            <li class="list-group-item"><strong>${totalNumberOfPassedScenarios}</strong> passed /
                                <strong>${totalNumberOfFailedScenarios}</strong> failed /
                                <strong>${totalNumberOfSkippedScenarios}</strong>
                                skipped.
                            </li>
                            <li class="list-group-item"><strong>Total Time:</strong> ${totalDurationString}</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <#if hasFailedScenarios()>
            <div class="row">
                <div class="col-sm-12">
                    <div class="card border-danger">
                        <div class="card-header border-danger bg-danger text-white">Failed Scenarios</div>
                        <div class="card-body">
                           <@scenarioMacros.table status="failed"/>
                        </div>
                    </div>
                </div>
            </div>
        </#if>

        <#if hasSkippedScenarios()>
            <div class="row">
                <div class="col-sm-12">
                    <div class="card border-warning">
                        <div class="card-header border-warning bg-warning">Skipped Scenarios</div>
                        <div class="card-body">
                            <@scenarioMacros.table status="skipped"/>
                        </div>
                    </div>
                </div>
            </div>
        </#if>

        <#if hasPassedScenarios()>
            <div class="row">
                <div class="col-sm-12">
                    <div class="card border-success">
                        <div class="card-header border-success bg-success text-white">Passed Scenarios</div>
                        <div class="card-body">
                           <@scenarioMacros.table status="passed"/>
                        </div>
                    </div>
                </div>
            </div>
        </#if>
    </div>
</main>

<#include "snippets/footer.ftl">
<#include "snippets/js.ftl">

</body>
</html>
