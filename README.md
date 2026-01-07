[![Apache V2 License](http://img.shields.io/badge/license-Apache%20V2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/com.trivago.rta/cluecumber-parent.svg)](https://central.sonatype.com/search?q=g%3Acom.trivago.rta++a%3Acluecumber-core+a%3Acluecumber-maven&smo=true)
[![Example Report](https://img.shields.io/badge/Example-Report-blue.svg)](https://softwaretester.blog/cluecumber)
<img src="documentation/img/cucumber-compatible-black-64.png" alt="Cluecumber compatible" width="200" />

<img alt="Cluecumber logo" src="documentation/img/cluecumber.png" width="250"/>

# Cluecumber
_Clear and concise JVM and Maven reporting for the Cucumber BDD and Karate JSON result format._

This project creates aggregated test reports from Cucumber compatible JSON files that are generated
by [Cucumber BDD](https://github.com/cucumber), [Karate](https://github.com/intuit/karate) and other frameworks.

Cluecumber comes in two flavors:
* [__Cluecumber Core__](core) -  generates reports from Java code
* [__Cluecumber Maven__](maven) - generates reports from Maven

The look can be adjusted by setting
* optional custom CSS
* optional custom favicon
* Custom parameters

![Cluecumber animation](documentation/img/cluecumber.gif)

A fully generated example report can be [viewed here](https://softwaretester.blog/cluecumber)!

## Generated pages

Cluecumber generates the following report pages:

* __All Scenarios__: all scenarios grouped by their status `passed`, `failed` or `skipped`.
* __Rerun Scenarios__: all scenarios that had previous runs if the respective option is turned on.
* __Scenario Sequence__: all scenarios in running order including their individual status information
* __Scenario Details__: all steps, hooks, stack traces and attachments of a single scenario
* __All Features__: an overview of all features
* __All Tags__: all used tags in scenarios, features and example tables including their individual status information
* __All Exceptions__: all exception types that occurred in the test suite
* __All Steps__: all steps in use including their individual status information
* __Scenarios by Tag__: all scenarios including a specific tag
* __Scenarios by Feature__: all scenarios belonging to a specific feature
* __Scenario by Step__: all scenarios that include a specific step
* __Scenario by Exception__: all scenarios that failed with a specific exception type
* __Tree View__: all features and scenarios in a tree for an easy overview of the test suite

## Changelog

All changes are documented in the [full changelog](CHANGELOG.md).

# Appendix

## Building

Cluecumber requires Java >= 11 and Maven >= 3.6.3.
It is available in [Maven central](https://central.sonatype.com/search?q=g%3Acom.trivago.rta++a%3Acluecumber-core+a%3Acluecumber-maven&smo=true).

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=trivago/cluecumber-report-plugin&type=Date)](https://star-history.com/#trivago/cluecumber-report-plugin&Date)

## License

Copyright 2018 - 2026 trivago N.V.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "
AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
language governing permissions and limitations under the License.
