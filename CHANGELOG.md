# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

Back to [Readme](README.md).

## [3.11.1] - 2025-04-25

### Fixed

* Freemarker configuration is now dynamically using the current version (#376)

### CHANGED

* Updated Maven compiler to `3.14.0`
* Updated JUnit to `5.13.0-M2`
* Updated Mockito to `5.16.1`
* Updated Dagger to `2.56.1`
* Downgraded Maven wrapper to stable version `3.9.9` for release process

## [3.11.0] - 2025-02-19

### Added

* "Copy to clipboard" button for step outputs, docstrings, text attachments and exception messages (#371)
* Custom logo capability (#367)
* New failures page that shows scenarios based on their exception (#362) 
* Tree view now takes the directory structure into account (#366)

### Changed

* Updated junit to `5.12.0-RC2`
* Updated javadoc to `3.11.2`
* Updated dagger to `2.55`
* Updated gson to `2.12.1`

### Fixed

* Correct pluralization for zero elements everywhere

## [3.10.0] - 2025-01-06

### Added

* Step and scenario results are now cached to speed up report generation

### Fixed

* Unnecessary console logs for dark mode feature (#363)
* Step was reported as passed when a step hook failed (#364)
* Include Hooks in All Steps report to track failures related to Hooks (#364)
* Fixed absolute path to the finished report in the console log

### Changed

* Strict adherence to the official cucumber guidelines for scenario states
* Google Dagger updated to `2.54`
* Freemarker updated to `2.3.33`
* Javadoc updated to `3.11.2`
* JUnit Jupiter updated to `5.11.4`
* Mockito updated to `5.15.2`

## [3.9.0] - 2024-11-05

### Fixed

* Plugin doesn't support parallel compilation (#358)
* Merging of reports with same test scenario doesn't work when output is different (#359)
* Dark mode flickering on navigation
* Wrong dark mode toggle caption when starting with dark mode active
* Fixed all Javadoc warnings

### Changed

* Shortened dark/light mode button caption
* Allow report generation even if `<customParametersFile>` defined file does not exist (#357)
* Updated JUnit Jupiter, Mockito, JavaDoc and GPG plugins

## [3.8.2] - 2024-10-30

### Added

* Dark mode

## [3.8.1] - 2024-09-12

### Changed

* Much safer detection of multi-run scenarios when using `groupPreviousScenarioRuns`

## [3.8.0] - 2024-09-06

### Fixed

* Better detection of embedded URLs in attachments, docstrings and outputs
* Fixed table alignment for duration column
* All graphs and totals now show the correct numbers based on the current rerun settings
* Aligned hover colors of overview and detail pages

### Added

* Logged report location now includes `file://` prefix
* Added `Rerun Scenarios` page in case of `groupPreviousScenarioRuns` mode
* Added a dedicated `Reruns` button to each scenario with previous runs

### Changed

* Updated JUnit Jupiter to `5.11.0`
* Updated Mockito to `5.13.0`
* Updated Dagger to `5.52`
* Build and test process uses Maven 4 wrapper
* Aligned all dependency versions to non alpha/beta versions

## [3.7.1] - 2024-07-12

### Fixed

* `failedScenariosOnPendingOrUndefinedSteps` setting is ignored (#341)

## [3.7.0] - 2024-07-09

### Added

* Custom favicon definition through property (#340)

## [3.6.3] - 2024-06-10

### Changed

* Cached slow method results to speed up report generation (#343), contributed by @simonstratmann

## [3.6.2] - 2024-03-25

### Fixed

* Sub sections can break the layout (#337)
* Separate Karate steps were shown as one in the step summary (#331)

### Changed

* More pleasant layout of scenario times and previous test runs

## [3.6.1] - 2024-03-15

### Fixed

* expandBeforeAfterHooks setting is ignored [#333]
* Html attachments could have a height of 0 on first load

### Changed

* Toggle buttons for all steps, hooks, attachments, outputs and sub sections have now specific "Hide" and "Show" labels

## [3.6.0] - 2024-03-14

### Fixed

* Long tag names broke the layout
* Reruns should not be displayed in the tree view

### Added

* Support for multiple sub sections (e.g. in Karate scenarios with feature files in feature files)
* Attachments, outputs and docStrings are now collapsible individually
* Step outputs are now toggleable
* New configuration options for toggling step output (`expandStepOutput`)
* New configuration options for toggling sub sections (`expandSubSections`)

### Changed

* Tweaked data table header layout
* Tweaked look of all item cards
* Re-arranged summary and timing information
* Updated dependencies

## [3.5.1] - 2023-11-13

### Changed

* Improved display of scenario groups
* Updated dependencies
* Added missing Javadoc

### Fixed

* Grouping scenarios crashed Cluecumber when no start time existed

## [3.5.0] - 2023-11-02

### Added

* `groupPreviousScenarioRuns` mode for a compact view when there are multiple runs of the same scenario [#245]
* `expandPreviousScenarioRuns` to set default state of expanded or collapsed of the previous runs of the same scenario

## [3.4.0] - 2023-08-10

### Fixed

* Background steps were not considered in the overall scenario outcome [#319]
* Background steps were not shown in the bar charts

### Changed

* Skipped scenarios are now determined to the official Cucumber guidelines
* Charts show small bars for steps with a time of 0 instead of none at all
* Before and after hooks are not shown in the step charts anymore

## [3.3.1] - 2023-08-08

### Fixed

* Wrong relative links from bar charts to detail pages

## [3.3.0] - 2023-08-07

### Fixed

* Scenarios having skipped steps after a passed step are now considered skipped (used to be passed) (#314)
* `skip` parameter in Cluecumber Maven was not recognized anymore (#316)

### Added

* Charts in "All Tags", "All Features" and "All Steps" can now be clicked and redirect to the according detail page

## [3.2.2] - 2023-05-12

### Fixed

* Pie chart always showing results from all features [#311]

### Changed

* Updated dependencies

## [3.2.1] - 2023-03-21

### Fixed

* `expandDocStrings` option did not have an effect [#308]

### Changed

* Code clean-up

## [3.2.0] - 2023-03-10

### Changed

* Updated dependencies
* Small visual tweaks
* Code clean-up

### Added

* Full javadoc coverage

## [3.1.0] - 2023-01-17

### Added

* Added new test overview tree view page

### Fixed

* Aligned counts and use of plurals in headlines
* Fixed misaligned numbers in tag, step and feature overview page tables
* Various small design adjustments and fixes

### Changed

* Clearer custom parameters box
* Tags are now displayed as buttons

## [3.0.2] - 2022-12-22

### Fixed

* Exception message for non-given step was not shown in overview (#302)

## [3.0.1] - 2022-12-15

### Fixed

* Exception messages for skipped scenarios were not displayed (#299)

### Changed

* Separators between scenarios on the overview page
* Changed alignment to top for tables
* Changed error message display to dark red

## [3.0.0] - 2022-12-03

This marks a new chapter in Cluecumber development.

From now on it will be possible to develop separate versions with different invocations and for different platforms
since
the core component is now the reporting engine that is the base for other forms of Cluecumber.

### Changed

* Separated functionality of Cluecumber Maven into three components:
    * Cluecumber Engine (_reporting engine_)
    * Cluecumber Core (_JVM code version_)
    * Cluecumber Maven (_Maven version_)
* Overhauled documentation

### Added

* New example projects for Cluecumber Core and Cluecumber Maven.

## [2.9.4] - 2022-10-19

### Changed

* Using new JQuery because of appsec vulnerability (#294)

## [2.9.3] - 2022-09-29

### Added

* Custom parameter values starting with `./`, `../` or `#` are now interpreted as relative URLs
* Custom navigation links without values are now hidden

## [2.9.2] - 2022-09-27

### Fixed

* #293 - unescaped html in exception messages

## [2.9.1] - 2022-09-26

### Added

* Display error messages on scenario overview pages

## [2.9.0] - 2022-09-24

### Added

* Ability to add custom external links to the navigation bar via `customNavigationLinks` property
* #281 - enable Cluecumber to be invoked without a POM project
* Display error classes on scenario overview pages

### Fixed

* #278 - Chart bars of steps with low times did not render at all

### Changed

* Dependency updates
* Design adjustments
* Reworked navigation for future improvements
* #280 - moved deployment to GitHub actions

## [2.8.0] - 2022-01-18

### Added

* New `customParametersDisplayMode` option to display custom parameters on scenario pages or all pages (#273, base
  implementation contributed by zutshiy)
* New `expandAttachments` option to expand or collapse attachments by default
* Attachments are collapsed by default and can be expanded (#279, base implementation contributed by beirtipol)

## [2.7.1] - 2021-11-05

### Changed

* Updated dependencies

## [2.7.0] - 2021-05-11

### Added

* More css classes for report customization (#270, contributed by GregJohnStewart)

### Changed

* Updated dependencies

## [2.6.1] - 2020-11-27

### Fixed

* After hooks with content were not displayed anymore

## [2.6.0] - 2020-11-27

### Fixed

* Fixed HTML encoding for attachments (#263)

### Added

* Background steps are now displayed in a separate section of the report

### Changed

* Only hooks with outputs are considered and displayed in the scenario detail pages (#211)
* Various design cleanups

## [2.5.0] - 2020-05-11

### Added

* Support for external mp4 video attachments (mime type `video/mp4` with an external URL)

### Changed

* Maximum image / video width is half the report width
* Feature URL is displayed on scenario summary and feature summary pages (#233)
* Automatic redirect to scenario detail page if only one scenario exists

## [2.4.0] - 2020-05-04

### Added

* Optional `startPage` property to set the report start page (#217)

### Fixed

* Cluecumber did not consider line breaks in scenario outputs (#244)
* Cluecumber failed on missing json files (#247)

## [2.3.4] - 2020-02-11

### Added

* Custom CSS classes for keywords and parameters (#235, contributed by Labouh)

### Fixed

* Unknown steps are now reported as skipped by default (unless `failScenariosOnPendingOrUndefinedSteps` is set to
  true) (#236)

## [2.3.3] - 2020-01-27

### Fixed

* Broken links on special characters in tabs (#231)
* Before hook attachments of legacy Cucumber versions were ignored and caused rendering errors (#323)

## [2.3.2] - 2020-01-23

### Fixed

* Missing feature description in scenario details (#222, contributed by Labouh)

### Added

* Explicit html element ids for better custom css styling (#228, contributed by Labouh)

## [2.3.1] - 2019-10-15

### Fixed

* Chart y axis step size in scenario view

## [2.3.0] - 2019-10-14

### Added

* Support for named attachments

### Changed

* Updated dependencies
* Removed `eval` from report template (#199)

### Fixed

* Chart y axis step size adapts to displayed values

## [2.2.0] - 2019-07-17

### Added

* Hide scenarios with matching status on the `Scenario Sequence` page when disabling a status in the diagram (#175)
* Clicking a pie chart slice toggles the according scenarios in `All Scenarios` and `Scenario Sequence` (#175)
* Logging can be configured via the `logLevel` property (#189)

### Changed

* Changed internal chart generation to simplify future chart features
* Scenario runtimes is now displayed in seconds in the `Scenario Detail` page graph (#193)
* Renamed y axis of `All Steps` page graph to `Number of Usages` (#193)

### Fixed

* Wrong wording in `All Tags` page
* Wrong y axis scale labels in stacked bar charts
* Missing `All Features` navigation link

## [2.1.0] - 2019-06-25

### Added

* Fully customizable passed, failed and skipped colors through new properties (#172)
* Proper pluralization of all words within summaries and headers
* Ability to set a custom report title (#176)
* Support for custom parameters with hidden keys (#183)

### Changed

* Better layout with larger graphs for tag, feature and step summary pages (#180)
* Moved timing information to a dedicated box in each report page
* Refined navigation (#177)
* Increased max report width

## [2.0.1] - 2019-06-15

### Fixed

* Error when `customParametersFile` property was not set (#171)
* Wrong log format for `customCssFile` property

### Added

* Better error messages for missing files

## [2.0.0] - 2019-06-14

### Added

* Ability to set custom parameters via a properties file through the `customParametersFile` property (#167, contributed
  by gazler22)

### Fixed

* Custom parameters are now displayed in the order of definition (#157)
* Better logging and error handling

### Changed

* Major internal architecture change to ease future extension

## [1.11.0] - 2019-05-21

### Fixed

* Much higher report generation speed
* Warnings in Java > 8
* Various small code style issues

### Changed

* Updated all dependencies

### Removed

* Cloner dependency
* JSoup dependency

## [1.10.2] - 2019-04-29

### Changed

* Version change for Nexus redeploy error

## [1.10.1] - 2019-04-29

### Fixed

* Incorrect wrapping for urls in custom parameters
* UTC timezone conversion for timestamps

### Changed

* more resilient mime type handling

## [1.10.0] - 2019-04-16

### Fixed

* Steps that are only skipped should not have a link to a scenario from the minimum/maximum time in `All Steps`
* Feature name on `Scenario Details` page was incorrectly linked

### Added

* Additional parallel run time on `All Scenarios` and `Scenario Sequence` pages
* Support of Cucumber 4.3.0's new scenario start timestamps
* Start time and date, end time and date and duration is displayed on each scenario
  in `All Scenarios`, `Scenario Sequence` and `Scenario Detail` pages (Cucumber >= 4.3.0)

### Changed

* `Scenario Sequence` is now not based on start time if it exists in the json file
* Minor design changes

## [1.9.0] - 2019-04-10

### Added

* Minimum and maximum step times on `All Steps` page links to the enclosing scenario (#152)
* New `failScenariosOnPendingOrUndefinedSteps` configuration property for marking scenarios as failed when they contain
  steps with status `pending` or `undefined` (default value is `false`) (#74)

### Changed

* Minimum and maximum step times are now only calculated for non-skipped steps

## [1.8.1] - 2019-04-03

### Fixed

* `All Steps` shows scenario states instead of step states (#147)

## [1.8.0] - 2019-03-27

### Added

* `All Steps` page (#145)
* Scenarios by Step overview page (#145)

### Changed

* Usage of symbols instead of text in tables for `passed`, `failed` and `skipped`

## [1.7.3] - 2019-03-05

### Fixed

* Step times included step hook times even though they are independent (#135)

### Added

* Links starting with `file:` are converted to clickable links (#142, contributed by gazler22)

## [1.7.2] - 2019-03-05

### Fixed

* Step times did not take step hook times into account (#135)

## [1.7.1] - 2019-02-28

### Fixed

* Ordering by status on Scenario Sequence page (#133, contributed by @monofrei)

## [1.7.0] - 2019-02-20

### Added

* Feature name link is now shown on scenario detail pages (#125)
* three new options to expand or collapse hooks and docstrings on scenario detail pages (default: false) (#117)

```xml

<configuration>
    <expandBeforeAfterHooks>true|false</expandBeforeAfterHooks>
    <expandStepHooks>true|false</expandStepHooks>
    <expandDocStrings>true|false</expandDocStrings>
</configuration>
```

* Added data attributes for common elements (`data-cluecumber-item`) to simplify custom css configurations (related to
  #129)

### Fixed

* `All Tags` page shows the correct number of tagged results (#124)
* Layout break on long scenario descriptions without spaces

### Changed

* Updated all js dependencies
* Small design changes for expansion buttons to make them less prominent

## [1.6.5] - 2019-01-21

### Fixed

* HTML attachments in hooks have the wrong size (#121)

## [1.6.4] - 2019-01-16

### Fixed

* Long scenario names break the layout (#119)

## [1.6.3] - 2019-01-08

### Fixed

* Feature tags are now propagated to their included scenarios and the `All Tags` page
* Data table css added unneeded space at the end

### Added

* Step hooks display can now be toggled
* Scenario text attachments are now formatted correctly

### Changed

* Multiple Scenario outputs are now displayed together
* minor design changes

## [1.6.2] - 2018-12-13

### Fixed

* Links to scenario details did not work for scenario indices over 1000
* Long custom parameters (URLs) broke the layout

## [1.6.1] - 2018-12-13

### Fixed

* Long URLs in doc strings and stack traces broke the layout

## [1.6.0] - 2018-12-12

### Added

* URLs in doc strings and stack traces are now clickable
* Increased unit test coverage

### Changed

* Minor design changes
* Custom parameters are now formatted evenly

## [1.5.0] - 2018-11-08

### Added

* Full support for png, gif, png, jpg, svg, html, xml, json, text and pdf attachments (contributed by @Rameshwar244)
* Minor design improvements

## [1.4.2] - 2018-10-16

### Fixed

* Attachments work in step before/after hooks

### Added

* Option to hide/show step hooks on the "Scenario Details" page (only present if the scenario has step hooks in at least
  one step)

## [1.4.1] - 2018-10-09

### Added

* `skip` property for skipping the report generation completely.

### Fixed

* Corrected typo on `All Tags` page.

## [1.4.0] - 2018-09-06

### Changed

* Cleaner layout without unnecessary horizontal lines
* Tables now show 25 entries by default
* Changed internal package structure

### Added

* Scenario Sequence page that shows the order of scenario executions and their states without deviding them into
  separate `passed`, `failed` and `skipped` sections
* Option to show/hide docstrings on the "Scenario Details" page (only present if the scenario has docstrings in at least
  one step)

### Fixed

* Long data tables broke the layout

## [1.3.0] - 2018-08-21

### Added

* `customCSS` property to provide an additional CSS file that is loaded on top of Cluecumber's default styles
* Support for step doc strings

### Changed

* Replaced `passed`, `failed` and `skipped` descriptions by webfont symbols
* Cleaned up information box content on all pages
* Data tables now use the full report width
* Stack traces are now formatted

## [1.2.1] - 2018-07-31

### Fixed

* Scenario calculation is now in line with the official Cucumber recommendations:
    * Scenarios with a mixture of passing and failing steps are now reported as passed
    * Scenarios with ONLY skipped steps and skipped or passing hooks will be reported as skipped
    * Scenarios with failing scenario or step hooks are reported as failed

## [1.2.0] - 2018-07-27

### Fixed

* Support for parameter highlighting in [Karate](https://github.com/intuit/karate/) tests
* Hiding skipped steps from the scenario detail chart now also hides steps that are _considered_ skipped but have a
  status other than `skipped`

### Added

* Failed Cucumber 3 `BeforeStep` and `AfterStep` hooks are shown on failure
* SVG attachment support

### Changed

* Separated `Before` and `After` hooks from steps in detail view
* Cleaned up "All Features" and "All Tags" charts
* Aligned and streamlined design on all pages

## [1.1.1] - 2018-07-03

### Fixed

* Page rendering error on certain step name characters

## [1.1.0] - 2018-07-03

### Added

* Synchronize charts and displayed data for "All Scenarios" and "Scenario Details" pages
* Improved highlighting of steps on mouseover

### Changed

* Charts now show more relevant information

### Fixed

* Parameter highlighting with regex characters does not crash report generation
* Correct chart labels for steps

## [1.0.0] - 2018-06-21

### Added

* New page "All Features"
* New page "All Tags"
* New page "Scenarios by Tag"
* New page "Scenarios by Feature"
* Parameters in steps are now highlighted

## [0.8.0] - 2018-06-10

### Fixed

* Scenario.write outputs with null values lead to rendering exceptions
* Scenario.write outputs are not shown in before and after steps

### Added

* Support for Before and After hock attachments
* Updated example JSON files in example project

### Removed

* Capitalization of scenario names

## [0.7.1] - 2018-05-08

### Added

* Feature description is now shown in the feature tool tip on hover

### Fixed

* Chart was not rendered when a scenario contained step data tables

## [0.7.0] - 2018-04-18

### Changed

* Unified report design
* Updated all dependencies
* Completely changed freemarker code to be better extensible

## [0.6.0] - 2018-04-12

### Added

* Example project

### Changed

* Cluecumber is now a monorepo

### Fixed

* Table header error on tab overview page

## [0.5.0] - 2018-03-19

### Added

* Tag summary page

### Fixed

* Background Scenario steps are now rendered correctly
* Various small bug fixes

## [0.3.0] - 2018-02-19

### Added

* Scenario.output is now displayed in the scenario details

### Fixed

* Scenarios with pending and skipped steps are also considered skipped.
* Background scenarios are now merged to the following scenarios.

### Changed

* Before and after steps have a lower opacity to focus on test steps.
* Internal organization of page types allows easier extension.

## [0.2.0] - 2018-01-16

### Added

- Support for data tables within steps
- Cleaner report headers

### Removed

- Javascript back method is replaced with simple links on the detail pages

### Fixed

- Report generation is now much more resilient if information is missing in the JSON sources

## [0.1.1] - 2017-12-12

### Removed

- Unnecessary log outputs for attachments

## [0.1.0] - 2017-12-12

### Added

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

[3.11.1]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.11.1

[3.11.0]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.11.0

[3.10.0]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.10.0

[3.9.0]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.9.0

[3.8.2]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.8.2

[3.8.1]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.8.1

[3.8.0]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.8.0

[3.7.1]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.7.1

[3.7.0]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.7.0

[3.6.3]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.6.3

[3.6.2]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.6.2

[3.6.1]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.6.1

[3.6.0]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.6.0

[3.5.1]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.5.1

[3.5.0]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.5.0

[3.4.0]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.4.0

[3.3.1]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.3.1

[3.3.0]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.3.0

[3.2.2]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.2.2

[3.2.1]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.2.1

[3.2.0]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.2.0

[3.1.0]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.1.0

[3.0.2]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.0.2

[3.0.1]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.0.1

[3.0.0]: https://github.com/trivago/cluecumber-report-plugin/tree/v3.0.0

[2.9.4]: https://github.com/trivago/cluecumber-report-plugin/tree/2.9.4

[2.9.3]: https://github.com/trivago/cluecumber-report-plugin/tree/2.9.3

[2.9.2]: https://github.com/trivago/cluecumber-report-plugin/tree/2.9.2

[2.9.1]: https://github.com/trivago/cluecumber-report-plugin/tree/2.9.1

[2.9.0]: https://github.com/trivago/cluecumber-report-plugin/tree/2.9.0

[2.8.0]: https://github.com/trivago/cluecumber-report-plugin/tree/2.8.0

[2.7.1]: https://github.com/trivago/cluecumber-report-plugin/tree/2.7.1

[2.7.0]: https://github.com/trivago/cluecumber-report-plugin/tree/2.7.0

[2.6.1]: https://github.com/trivago/cluecumber-report-plugin/tree/2.6.1

[2.6.0]: https://github.com/trivago/cluecumber-report-plugin/tree/2.6.0

[2.5.0]: https://github.com/trivago/cluecumber-report-plugin/tree/2.5.0

[2.4.0]: https://github.com/trivago/cluecumber-report-plugin/tree/2.4.0

[2.3.4]: https://github.com/trivago/cluecumber-report-plugin/tree/2.3.4

[2.3.3]: https://github.com/trivago/cluecumber-report-plugin/tree/2.3.3

[2.3.2]: https://github.com/trivago/cluecumber-report-plugin/tree/2.3.2

[2.3.1]: https://github.com/trivago/cluecumber-report-plugin/tree/2.3.1

[2.3.0]: https://github.com/trivago/cluecumber-report-plugin/tree/2.3.0

[2.2.0]: https://github.com/trivago/cluecumber-report-plugin/tree/2.2.0

[2.1.1]: https://github.com/trivago/cluecumber-report-plugin/tree/2.1.1

[2.1.0]: https://github.com/trivago/cluecumber-report-plugin/tree/2.1.0

[2.0.1]: https://github.com/trivago/cluecumber-report-plugin/tree/2.0.1

[2.0.0]: https://github.com/trivago/cluecumber-report-plugin/tree/2.0.0

[1.11.0]: https://github.com/trivago/cluecumber-report-plugin/tree/1.11.0

[1.10.2]: https://github.com/trivago/cluecumber-report-plugin/tree/1.10.2

[1.10.1]: https://github.com/trivago/cluecumber-report-plugin/tree/1.10.1

[1.10.0]: https://github.com/trivago/cluecumber-report-plugin/tree/1.10.0

[1.9.0]: https://github.com/trivago/cluecumber-report-plugin/tree/1.9.0

[1.8.1]: https://github.com/trivago/cluecumber-report-plugin/tree/1.8.1

[1.8.0]: https://github.com/trivago/cluecumber-report-plugin/tree/1.8.0

[1.7.3]: https://github.com/trivago/cluecumber-report-plugin/tree/1.7.3

[1.7.2]: https://github.com/trivago/cluecumber-report-plugin/tree/1.7.2

[1.7.1]: https://github.com/trivago/cluecumber-report-plugin/tree/1.7.1

[1.7.0]: https://github.com/trivago/cluecumber-report-plugin/tree/1.7.0

[1.6.5]: https://github.com/trivago/cluecumber-report-plugin/tree/1.6.5

[1.6.4]: https://github.com/trivago/cluecumber-report-plugin/tree/1.6.4

[1.6.3]: https://github.com/trivago/cluecumber-report-plugin/tree/1.6.3

[1.6.2]: https://github.com/trivago/cluecumber-report-plugin/tree/1.6.2

[1.6.1]: https://github.com/trivago/cluecumber-report-plugin/tree/1.6.1

[1.6.0]: https://github.com/trivago/cluecumber-report-plugin/tree/1.6.0

[1.5.0]: https://github.com/trivago/cluecumber-report-plugin/tree/1.5.0

[1.4.2]: https://github.com/trivago/cluecumber-report-plugin/tree/1.4.2

[1.4.1]: https://github.com/trivago/cluecumber-report-plugin/tree/1.4.1

[1.4.0]: https://github.com/trivago/cluecumber-report-plugin/tree/1.4.0

[1.3.0]: https://github.com/trivago/cluecumber-report-plugin/tree/1.3.0

[1.2.1]: https://github.com/trivago/cluecumber-report-plugin/tree/1.2.1

[1.2.0]: https://github.com/trivago/cluecumber-report-plugin/tree/1.2.0

[1.1.1]: https://github.com/trivago/cluecumber-report-plugin/tree/1.1.1

[1.1.0]: https://github.com/trivago/cluecumber-report-plugin/tree/1.1.0

[1.0.0]: https://github.com/trivago/cluecumber-report-plugin/tree/1.0.0

[0.8.0]: https://github.com/trivago/cluecumber-report-plugin/tree/0.8.0

[0.7.1]: https://github.com/trivago/cluecumber-report-plugin/tree/0.7.1

[0.6.0]: https://github.com/trivago/cluecumber-report-plugin/tree/0.6.0

[0.5.0]: https://github.com/trivago/cluecumber-report-plugin/tree/0.5.0

[0.3.0]: https://github.com/trivago/cluecumber-report-plugin/tree/0.3.0

[0.2.0]: https://github.com/trivago/cluecumber-report-plugin/tree/0.2.0

[0.1.1]: https://github.com/trivago/cluecumber-report-plugin/tree/0.1.1

[0.1.0]: https://github.com/trivago/cluecumber-report-plugin/tree/0.1.0

[0.7.0]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.7

[0.0.6]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.6

[0.0.5]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.5

[0.0.4]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.4

[0.0.3]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.3

[0.0.2]: https://github.com/trivago/cluecumber-report-plugin/tree/0.0.2
