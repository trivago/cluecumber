# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

Back to [Readme](README.md).

## [0.7.1] - 2018-05-08

# Added

* Feature description is now shown in the feature tool tip on hover

# Fixed

* Chart was not rendered when a scenario contained step data tables

## [0.7.0] - 2018-04-18

# Changed

* Unified report design
* Updated all dependencies
* Completely changed freemarker code to be better extensible

## [0.6.0] - 2018-04-12

# Added

* Example project

# Changed

* Cluecumber is now a monorepo

# Fixed

* Table header error on tab overview page

## [0.5.0] - 2018-03-19

# Added

* Tag summary page

# Fixed

* Background Scenario steps are now rendered correctly
* Various small bug fixes

## [0.3.0] - 2018-02-19

# Added

* Scenario.output is now displayed in the scenario details

# Fixed

* Scenarios with pending and skipped steps are also considered skipped.
* Background scenarios are now merged to the following scenarios.

# Changed

* Before and after steps have a lower opacity to focus on test steps.
* Internal organization of page types allows easier extension.

## [0.2.0] - 2018-01-16

# Added

- Support for data tables within steps
- Cleaner report headers

# Removed

- Javascript back method is replaced with simple links on the detail pages

# Fixed

- Report generation is now much more resilient if information is missing in the JSON sources

## [0.1.1] - 2017-12-12

# Removed

- Unnecessary log outputs for attachments

## [0.1.0] - 2017-12-12

# Added

- Support for Cucumber 2 attachments

## [0.0.6] - 2017-11-29

### Fixed

- missing hook durations could crash during report generation
- back link fix for iframed report

### Added

- More unit tests

## [0.0.5] - 2017-11-20

### Fixed

- Tooltips are rendered correctly on data table page switch
- html encoding in stacktraces fixed
- back link also supports iframes

### Added

- Custom properties can be added to the report, URL will automatically be clickable
- Before and After hooks are displayed in the report
- Total test time is shown in the start page
- Tool tips for feature file names
- Tool tips for scenario step method signatures

## [0.0.4] - 2017-11-20

### Added

- Scenario duration is displayed on start page
- Feature file name is displayed on feature name mouse over

### Fixed

- Corrupt or empty JSON files do not fail report generation anymore

## [0.0.3] - 2017-11-15

### Fixed

- handling of scenarios without steps
- handling of undefined steps

## [0.0.2] - 2017-11-14

Initial project version on GitHub and Maven Central.

[0.6.0]: https://github.com/trivago/cluecumber-report-plugin/tree/0.6.0
[0.5.0]: https://github.com/trivago/cluecumber-report-plugin/tree/0.5.0
[0.3.0]: https://github.com/trivago/cluecumber-report-plugin/tree/0.3.0
[0.2.0]: https://github.com/trivago/cluecumber-report-plugin/tree/0.2.0
[0.1.1]: https://github.com/trivago/cluecumber-report-plugin/tree/0.1.1
[0.1.0]: https://github.com/trivago/cluecumber-report-plugin/tree/0.1.0
[0.0.6]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.6
[0.0.5]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.5
[0.0.4]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.4
[0.0.3]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.3
[0.0.2]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.2