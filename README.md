Rebalancer
==========
A Java rebalancing module that takes a csv file as input and prints the rebalanced results.

Build
=====

This project uses Gradle as the build tool. To build the project:

    ./gradlew assemble

This command compiles all java classes and packs all dependencies needed for the project.

Run
===

A shell script has written for running the project against some test inputs.

    ./run.sh

This shell script assembles the project and use the assembled jar file to run against test csv files
under src/test/resources/.

Dependencies
============

The only dependency used is "org.apache.commons:commons-csv:1.2". This is a csv library provided
from Apache commons libraries, which has many more contributors comparing to other csv libraries.


Technical Choices
=================

* CSV: The choice of using csv is made from knowing that Excel is used by WS for modeling. The
program may directly use the data modeled from an Excel file. In addition, this makes
testing easier.

* Use NumberFormat to parse: NumerFormat correctly handles % and $, which are used in the given
example.

* Use BigDecimal: The main reason for using BigDecimal is to avoid precision issued caused by using
doubles. Especially in a context of financial application.

In addition, I had extra caution on the edge cases in the code. This may make the code harder to
read, I could have make code more organized if I were to spend additional time.

Tests
=====

Gradle provides a nice interface to run Java unit tests.

    ./gradlew test

This command compiles and runs all tests. The main purpose of the unit tests is to catch invalid
inputs.

Integration tests are executed using the same command as run.

    ./run.sh

Future Works
============

* More tests: I didn't write a lot of functional unit tests. In terms of integration tests, there
could be more integration tests to cover different code paths.

* Find a better way of handling percentage and currency. Currently BigDecimal and number format are
used to handle these problems, but I think they are not the ideal solutions.
