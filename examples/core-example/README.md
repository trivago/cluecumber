<img alt="Cluecumber Core logo" src="../../documentation/img/cluecumber_core.png" width="250"/>

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

- [Cluecumber Core Example](#cluecumber-core-example)
  - [Configuration options](#configuration-options)
  - [Run the project](#run-the-project)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Cluecumber Core Example

This project demonstrates the usage of [Cluecumber Core](../../core).

This test project always uses the latest Cluecumber version.

## Configuration options

This example uses the default configuration for all options. You can add different options to the builder explained in the [Cluecumber Core Readme](../../core).

## Run the project

To run the project you need to have at least Java 11 and Maven 3.3.9 installed on your system.

Just execute the `Main` class to see the report generation of Cluecumber in action.

The example Cucumber JSON files are located in the `../json` directory. These are based on the https://github.com/aslakhellesoy/cucumber-json-formatter project.
The report is generated inside the `target/cluecumber-report` directory.

__Note:__ The report directory is not automatically wiped when regenerating the report.

