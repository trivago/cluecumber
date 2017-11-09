![cluecumber logo](documentation/img/cluecumber.png)

[![Apache V2 License](http://img.shields.io/badge/license-Apache%20V2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Twitter URL](https://img.shields.io/twitter/url/http/shields.io.svg?style=social)](https://twitter.com/bischoffdev)

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->


- [Cluecumber Report Maven Plugin](#cluecumber-report-maven-plugin)
  - [Example POM snippet](#example-pom-snippet)
  - [Parameters](#parameters)
  - [Prerequisites](#prerequisites)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->


# Cluecumber Report Maven Plugin

This plugin creates test reports from Cucumber JSON files.

![report_overview](documentation/img/report_overview.png)
![report_detail](documentation/img/report_detail.png)

## Example POM snippet

```xml
<plugin>
    <groupId>com.trivago.rta</groupId>
    <artifactId>cluecumber-report-plugin</artifactId>
    <version>0.0.2</version>
    <executions>
        <execution>
            <id>report</id>
            <phase>post-integration-test</phase>
            <goals>
                <goal>reporting</goal>
            </goals>
            <configuration>
                <sourceJsonReportDirectory>${project.basedir}/src/main/resources/cucumber-report</sourceJsonReportDirectory>
                <generatedHtmlReportDirectory>${project.build.directory}/cluecumber-report</generatedHtmlReportDirectory>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## Parameters

There are two parameters that have to be specified:

| Parameter | Explanation |
|---|---|
| sourceJsonReportDirectory | This specifies source folder of the Cucumber JSON result files |
| generatedHtmlReportDirectory | This points to the root directory of the generated Cluecumber HTML report. |

**Note:**
Typically, both properties point to directories inside the Maven ```target``` directory.

## Prerequisites

In order to have the JSON files as a source for the Cluecumber Report generation, you need to specify this option in your Cucumber runner configuration:
```
@CucumberOptions(
    format = {"json:target/cucumber-report/cucumber.json"}
)
```