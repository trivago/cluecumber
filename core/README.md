[![Apache V2 License](http://img.shields.io/badge/license-Apache%20V2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/com.trivago.rta/cluecumber-core.svg)](https://repo1.maven.org/maven2/com/trivago/rta/cluecumber-core)
[![Example Report](https://img.shields.io/badge/Example-Report-blue.svg)](http://cluecumber.softwaretester.blog/)
[![Mastodon Follow](https://img.shields.io/mastodon/follow/109619788534969171?domain=https%3A%2F%2Fhachyderm.io&style=social)](https://hachyderm.io/invite/acrCWhtk)
 <img src="../documentation/img/cucumber-compatible-black-64.png" alt="Cluecumber compatible" width="200" />

<img alt="cluecumber logo" src="../documentation/img/cluecumber_core.png" width="250"/>

# Cluecumber Core
_Clear and concise JVM reporting for the Cucumber BDD JSON format_

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

- [Maven dependency](#maven-dependency)
- [Prerequisites](#prerequisites) 
- [Mandatory Configuration Parameters](#mandatory-configuration-parameters)
- [Karate Example](#karate-example)
- [Optional Configuration Parameters](#optional-configuration-parameters)
  - [Logging](#logging)
  - [Add Custom Information to the Report](#add-custom-information-to-the-report)
    - [Add Custom Information](#add-custom-information)
    - [Add Custom Information Using a File](#add-custom-information-using-a-file)
    - [Where to Display Custom Parameters](#where-to-display-custom-parameters)
  - [Add custom navigation links](#add-custom-navigation-links)
  - [Fail Scenarios on Pending or Undefined Steps](#fail-scenarios-on-pending-or-undefined-steps)
  - [Auto-expand Certain Report Sections](#auto-expand-certain-report-sections)
  - [Auto-expand Attachments](#auto-expand-attachments)
- [Optional Configuration Parameters for Changing the Report Appearance](#optional-configuration-parameters-for-changing-the-report-appearance)
  - [Defining the report start page](#defining-the-report-start-page)
  - [Defining a custom report title](#defining-a-custom-report-title)
  - [Defining a custom CSS file](#defining-a-custom-css-file)
  - [Defining custom passed, skipped and failed colors](#defining-custom-passed-skipped-and-failed-colors)
- [Appendix](#appendix)
  - [Building](#building)
  - [License](#license)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Maven dependency

```xml
<dependency>
  <groupId>com.trivago.rta</groupId>
  <artifactId>cluecumber-core</artifactId>
  <version>3.4.0</version>
</dependency>
```

__Note:__ From version 3.0.0 on, the Maven goal for command line invocation changed to `cluecumber:reporting`!

# Prerequisites

In order to have the JSON files as a source for the Cluecumber Report generation, you need to specify this option in
your Cucumber runner configuration:

```java
@CucumberOptions(
    plugin = {"json:target/cucumber-report/cucumber.json"}
)
```

This will generate JSON results for all Cucumber tests.

# Mandatory Configuration Parameters

There are two mandatory parameters that have to be passed to the `generateReports` method:

```java
String jsonDirectory = "examples/core-example/json";
String reportDirectory = "examples/core-example/target/cluecumber_report";

new CluecumberCore.Builder()
        .build().generateReports(jsonDirectory, reportDirectory);
```

* `jsonDirectory` specifies the source folder of the Cucumber JSON result files.
* `reportDirectory` points to the root directory of the generated Cluecumber HTML report.

__Note:__ Typically, both properties point to directories inside the Maven ```target``` directory.

# Karate example

For use in Karate, just place the Cluecumber code in between the test runner code and the final assertion as seen below:

```java
@Test
void testParallel() throws CluecumberException {
  final int threads = 10;

  Results results= Runner.path("classpath:karate/features")
      .outputCucumberJson(true)
      .outputHtmlReport(false)
      .parallel(threads);

  new CluecumberCore.Builder()
      .setCustomPageTitle("My cool report")
      .build()
      .generateReports(
          "target/karate-reports",
          "target/cluecumber");

  assertEquals(0, results.getFailCount(), results.getErrorMessages());
}
```

This will generate a test report regardless of any failure of tests since the overall test run failure state is determined afterwards.

# Optional Configuration Parameters

## Logging

By default, Cluecumber logs all information including

* its own name and version
* all passed property values
* the generated report location

This can be configured by passing the `logLevel` property:

```java
new CluecumberCore.Builder()
        .setLogLevel(CluecumberLogger.CluecumberLogLevel.MINIMAL)
        .build().generateReports(jsonDirectory, reportDirectory);
```

* `CluecumberLogger.CluecumberLogLevel.DEFAULT` will log all the mentioned information
* `CluecumberLogger.CluecumberLogLevel.COMPACT` will only log the source and target directories, plugin name and version and the generated report location
* `CluecumberLogger.CluecumberLogLevel.MINIMAL` will only log the generated report location
* `CluecumberLogger.CluecumberLogLevel.OFF` will prevent any logging

## Add Custom Information to the Report

### Add Custom Information

`setCustomParameters` can be used to define custom information that should be displayed on the report start page.

__Note:__ Underscores in the parameter names are automatically turned into spaces in the report.
Valid URLs that start with a protocol (http, https, ftp) are automatically recognized and turned into clickable links.
If a parameter name starts with an underscore (`_`), only the value is displayed.

```java
new CluecumberCore.Builder()
        .setCustomParameters(parameters)
        .build().generateReports(jsonDirectory, reportDirectory);
```

The property definitions above are shown in the report like this:

![custom parameters](../documentation/img/custom_params.png)

### Add Custom Information Using a File

You can also set custom parameters by specifying the path to a `.properties` file in the `customParametersFile` property
like this:

```java
new CluecumberCore.Builder()
        .setCustomParametersFile("c:/myProperties.properties")
        .build().generateReports(jsonDirectory, reportDirectory);
```

This file needs to have a format like this:

```properties
Custom_Parameter=This is a test
Custom_URL=http://www.google.com
_Text=This is a long text that is displayed without the key. This can be used to display longer texts in the report!
```

__Note:__ These custom parameters behave exactly like the ones defined by the `setCustomParameters` method and will be
added on top of already defined properties.
If a property has the same name as an existing one, its value will be overwritten!

The property definitions above are shown in the report like this:

![custom parameters](../documentation/img/custom_params.png)

### Where to Display Custom Parameters

You can decide how to display the custom parameters in the report using the `customParametersDisplayMode` property.

The following display modes are available for displaying the custom parameters:

* `Settings.CustomParamDisplayMode.SCENARIO_PAGES`: Displays only on the scenario and scenario sequence pages. (_default_)
* `Settings.CustomParamDisplayMode.ALL_PAGES`: Display on all the pages in the report.

```java
new CluecumberCore.Builder()
        .setCustomParameters(parameters)
        .setCustomParametersDisplayMode(Settings.CustomParamDisplayMode.ALL_PAGES)
        .build().generateReports(jsonDirectory, reportDirectory);
```

The default value for this property is `SCENARIO_PAGES`.

## Add custom navigation links

If you have other pages or files you want to make accessible from the central navigation bar,
this is possible via the `customNavigationLinks` property.

```java
LinkedHashMap<String, String> links = new LinkedHashMap<>();
links.put("Test Blog", "https://www.softwaretester.blog");
links.put("Twitter", "https://twitter.com/BischoffDev");

new CluecumberCore.Builder()
        .setCustomNavigationLinks(links)
        .build().generateReports(jsonDirectory, reportDirectory);
```

These links will be added to the right of the navigation bar. If there are underscores ("_") in the property key,
these are replaces with spaces for the link name:

![Custom link](../documentation/img/custom_link.png)

## Fail Scenarios on Pending or Undefined Steps

The optional `setFailScenariosOnPendingOrUndefinedSteps` property can be set to `true` if you scenarios should be marked
as `failed` when they contain `pending` or `skipped` steps.
The default setting is `false`, meaning that those scenarios will be marked as `skipped`.

```java
new CluecumberCore.Builder()
        .setFailScenariosOnPendingOrUndefinedSteps(true)
        .build().generateReports(jsonDirectory, reportDirectory);
```

## Auto-expand Certain Report Sections

The `expandBeforeAfterHooks`, `expandStepHooks` and `expandDocStrings` options can be set to `true` to expand or
collapse before/after hooks, step hooks or docstrings respectively on scenario detail pages.

If they are not set, they default to false. This means that the report user has to use the buttons on a scenario detail
page to expand those sections on demand.

```java
new CluecumberCore.Builder()
        .setExpandBeforeAfterHooks(true)
        .setExpandStepHooks(true)
        .setExpandDocStrings(true)
        .build().generateReports(jsonDirectory, reportDirectory);
```

## Auto-expand Attachments

By default, attachments are collapsed and can be toggled individually. If the `expandAttachments` options is set
to `true`, they are automatically expanded.

```java
new CluecumberCore.Builder()
        .setExpandAttachments(true)
        .build().generateReports(jsonDirectory, reportDirectory);
```

# Optional Configuration Parameters for Changing the Report Appearance

## Defining the report start page

The default start page of the reports (if not overwritten by the `startPage` property) is the scenario overview page.

```java
new CluecumberCore.Builder()
        .setStartPage(Settings.StartPage.ALL_SCENARIOS)
        .build().generateReports(jsonDirectory, reportDirectory);
```

This can be customized with one of the following values:

* `Settings.StartPage.ALL_SCENARIOS` (scenario overview page, default)
* `Settings.StartPage.SCENARIO_SEQUENCE` (scenario sequence page)
* `Settings.StartPage.ALL_TAGS` (tag overview page)
* `Settings.StartPage.ALL_STEPS` (step overview page)
* `Settings.StartPage.ALL_FEATURES` (feature overview page)
* `Settings.StartPage.TREE_VIEW` (tree view of features and scenarios)

## Defining a custom report title

By default, the page html title of the report pages is `Cluecumber Report` plus the current page name,
e.g. `Cluecumber Report - All Tags`.

By setting the property `customPageTitle`, this can be changed:

```java
new CluecumberCore.Builder()
        .setCustomPageTitle("My Report")
        .build().generateReports(jsonDirectory, reportDirectory);
```

This would lead to a report title like this:

![Custom Title](../documentation/img/custom_title.png)

## Defining a custom CSS file

The `setCustomCss` method can be used to define a custom CSS file that will be automatically loaded on top of
Cluecumber's default styles.

If you have a custom CSS file called `custom/custom.css` in your project, you could use it to change the report's
background and header colors:

```css
body {
    background-color: black;
}

h3, h4, h5 {
    color: white;
}
```

To use this files, specify it like so in your pom file or as a system property:

```java
new CluecumberCore.Builder()
        .setCustomCssFile("custom/custom.css")
        .build().generateReports(jsonDirectory, reportDirectory);
```

When generating the report, this file is automatically included as ```cluecumber_custom.css``` and applied on top of all
other styles:

![Custom CSS](../documentation/img/custom_css.png)

Likewise, if you want to hide elements from the report, you can also add this to the custom css like so:

```css
.some_element {
    display: none;
}
```

## Defining custom passed, skipped and failed colors

It is possible to set these properties to change the color scheme for passed, failed and skipped steps and scenarios
including the displayed diagrams. The values have to be valid hex colors:

```java
new CluecumberCore.Builder()
        .setCustomStatusColorPassed("#017FAF")
        .setCustomStatusColorFailed("#C94A38")
        .setCustomStatusColorSkipped("#F48F00")
        .build().generateReports(jsonDirectory, reportDirectory)
```

The result of this customization is:

| Before | After |
|---|---|
| ![Chart Before](../documentation/img/chart_before.png) | ![Chart After](../documentation/img/chart_after.png) |


# Appendix

## Building

Cluecumber requires Java >= 11.
It is available in [Maven central](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.trivago.rta%22%20AND%20a%3A%22cluecumber-report-plugin%22)
.

## License

Copyright 2018 - 2022 trivago N.V.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "
AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
language governing permissions and limitations under the License.
