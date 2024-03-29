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

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Redirecting to ${startPage.pageName}</title>
    <#if redirectToFirstScenario>
        <meta http-equiv="refresh" content="0; url=pages/scenario-detail/scenario_1.html">
    <#else>
        <meta http-equiv="refresh" content="0; url=pages/${startPage.pageName}.html">
    </#if>
</head>
<body>
</body>
</html>


