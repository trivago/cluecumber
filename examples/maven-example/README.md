<img alt="Cluecumber Maven logo" src="../../documentation/img/cluecumber_maven.png" width="250"/>

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

- [Cluecumber Maven Example](#cluecumber-maven-example)
    - [Configuration options](#configuration-options)
    - [Run the project](#run-the-project)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Cluecumber Maven Example

This project demonstrates the usage of [Cluecumber Maven](../../maven).

This test project always uses the latest Cluecumber version.

## Configuration options

The whole plugin configuration is managed via the [pom.xml](pom.xml) file in this test project.
You can add different options as explained in the [Cluecumber Maven Readme](../../maven).

## Run the project

To run the project you need to have at least Java 11 and Maven 3.3.9 installed on your system.

Just run the Maven command `mvn clean verify` to see the report generation of Cluecumber in action.
This can also be run via `make test` from Cluecumber's root directory.

The example Cucumber JSON files are located in the `../json` directory. These are based on
the https://github.com/aslakhellesoy/cucumber-json-formatter project.
The report is generated inside the `target/cluecumber-report` directory.

