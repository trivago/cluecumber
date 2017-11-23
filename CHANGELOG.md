# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

Back to [Readme](README.md).

## [Unreleased]
- Ability to use custom templates
- Support for non-image attachments
- Increased unit test coverage

## [0.0.5] - 2017-11-20

### Fixed

- Tooltips are rendered correctly on data table page switch
- html encoding in stacktraces fixed

### Added

- Before and After hooks are displayed in the report
- Total test time is shown in the start page

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

[Unreleased]: https://github.com/trivago/cluecumber-report-plugin/compare/0.0.5...HEAD
[0.0.5]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.5
[0.0.4]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.4
[0.0.3]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.3
[0.0.2]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.2