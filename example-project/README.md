<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->


- [cluecumber-test-project](#cluecumber-test-project)
  - [Change plugin configuration](#change-plugin-configuration)
  - [Run the project](#run-the-project)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# cluecumber-test-project

Test project to simulate the behavior of Cluecumber - a Maven plugin that generates test reports from [Cucumber](https://cucumber.io) JSON files.

This test project always uses the latest Cluecumber version.

## Change plugin configuration

The whole plugin configuration is managed via the pom.xml file in this test project.

## Run the project

To run the project you need to have at least Java 8 and Maven 3.3.9 installed on your system.

Just run the Maven command ```mvn clean verify``` to see the runner and feature generation of Cluecumber in action.

The example Cucumber JSON files are located in the project's `json` directory. These are based on the https://github.com/aslakhellesoy/cucumber-json-formatter project. 
The report is generated inside the `target/cluecumber-report` directory.