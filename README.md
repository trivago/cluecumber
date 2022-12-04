<img alt="Cluecumber logo" src="documentation/img/cluecumber.png" width="250"/>

### Clear and concise JVM and Maven reporting for the Cucumber BDD JSON format

[![Apache V2 License](http://img.shields.io/badge/license-Apache%20V2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/com.trivago.rta/cluecumber-parent.svg)](https://repo1.maven.org/maven2/com/trivago/rta/)
[![Example Report](https://img.shields.io/badge/Example-Report-blue.svg)](http://cluecumber.softwaretester.blog/)
[![Twitter URL](https://img.shields.io/twitter/url/http/shields.io.svg?style=social)](https://twitter.com/bischoffdev)

<table>
<tr style="vertical-align: top;">
 <td width="60%">
 <img src="documentation/img/cluecumber.gif" alt="Cluecumber animation" />
 </td>
 <td width="40%">
  <p>
  <strong>Cluecumber Core</strong><br>
   generates reports from code
  <ul>
   <li><a href="core">Documentation</a></li>
   <li><img src="https://img.shields.io/maven-central/v/com.trivago.rta/cluecumber-core.svg" alt="Cluecumber Core" /></li>
  </ul>
 </p>
 <hr>
 <p>
  <strong>Cluecumber Maven</strong><br>
   generates reports from Maven
  <ul>
   <li><a href="maven">Documentation</a></li>
   <li><img src="https://img.shields.io/maven-central/v/com.trivago.rta/cluecumber-maven.svg" alt="Cluecumber Maven" /></li>
  </ul>
 </p>
 <hr>
 <img src="documentation/img/cucumber-compatible-black-64.png" alt="Cluecumber compatible" width="200" />
 </td> 
</tr>
<table>

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

- [Cluecumber](#cluecumber)
  - [Cluecumber Core (usage from Java code)](#cluecumber-core-usage-from-java-code)
  - [Cluecumber Maven (usage from Maven)](#cluecumber-maven-usage-from-maven)
  - [Example report](#example-report)
  - [Changelog](#changelog)
- [Appendix](#appendix)
  - [Building](#building)
  - [Generated pages](#generated-pages)
  - [Star History](#star-history)
  - [License](#license)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Cluecumber

This project creates aggregated test reports from Cucumber compatible JSON files that are generated
by [Cucumber BDD](https://github.com/cucumber), [Karate](https://github.com/intuit/karate) and other frameworks.

## Cluecumber Core (usage from Java code)

[Cluecumber Core](core) is intended to be used directly from Java code, e.g.
for [Karate](https://github.com/intuit/karate) test runners.

The full documentation can be found here: [Cluecumber Core documentation](core). For an example, please check out the [Cluecumber Core Example](examples/core-example).

## Cluecumber Maven (usage from Maven)

[Cluecumber Maven](maven) is intended to be used through Maven invocation.

The full documentation can be found here: [Cluecumber Maven](maven). For an example, please check out the [Cluecumber Maven Example](examples/maven-example).

## Example report

A fully generated example report can be [viewed online](http://cluecumber.softwaretester.blog/)!

## Changelog

All changes are documented in the [full changelog](CHANGELOG.md).

# Appendix

## Building

Cluecumber requires Java >= 11 and Maven >= 3.3.9.
It is available in [Maven central](https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.trivago.rta%22%20AND%20a%3A%22cluecumber-report-plugin%22).

## Generated pages

* __All Scenarios__: all scenarios grouped by their status `passed`, `failed` or `skipped`.
* __Scenario Sequence__: all scenarios in running order including their individual status information
* __Scenario Details__: all steps, hooks, stack traces and attachments of a single scenario
* __All Features__: an overview of all features
* __All Tags__: all used tags in scenarios, features and example tables including their individual status information
* __All Steps__: all steps in use including their individual status information
* __Scenarios by Tag__: all scenarios including a specific tag
* __Scenarios by Feature__: all scenarios belonging to a specific feature
* __Scenario by Step__: all scenarios that include a specific step

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=trivago/cluecumber-report-plugin&type=Date)](https://star-history.com/#trivago/cluecumber-report-plugin&Date)

## License

Copyright 2018 - 2022 trivago N.V.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "
AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
language governing permissions and limitations under the License.
