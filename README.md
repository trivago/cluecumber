# Cluecumber Report Maven Plugin

This plugin creates test reports from Cucumber JSON files.

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
    format = {"json:target/trupi-basic-report/cucumber.json"}
)
```