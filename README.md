# Coursera Automation

This project is a Selenium automation framework built with **Java**, **Cucumber**, and **TestNG**. It is used to automate and validate a few Coursera user flows such as course search, form validation, and course extraction.

## What the project does

The current feature files cover these scenarios:

- **For Campus form validation**
  - Checks validation messages for invalid email input in the "Ready to transform" form.
- **Language Learning course extraction**
  - Extracts up to 12 unique courses for Beginner, Intermediate, and Advanced levels.
- **Web Development course search**
  - Searches for "Web development" courses and extracts course details such as name, hours, and rating.

## Tech stack

- **Java 14**
- **Selenium WebDriver**
- **Cucumber**
- **TestNG**
- **Apache POI** for Excel-based test data
- **Log4j** for logging
- **Extent Reports** for reporting

## Project structure

- `Features/` - Cucumber feature files
- `src/test/java/` - Java test code
  - `stepDefinitions/` - Step definitions for Cucumber scenarios
  - `pageObjects/` - Page Object Model classes
  - `hooks/` - Setup and teardown hooks
  - `testRunner/` - Test runner classes
  - `utilities/` - Reusable helper classes
- `testData/` - Test data files such as Excel sheets
- `reports/` - HTML reports
- `logs/` - Log files
- `test-output/` - TestNG and Cucumber output
- `target/` - Maven build output and rerun files

## Prerequisites

Before running the project, make sure you have:

- **Java 14** installed
- **Maven** installed
- A browser such as **Chrome** installed
- The matching browser driver available if your setup requires it

## Reports and output

After a test run, you can check the following locations:

- `reports/myreport.html` - Cucumber HTML report
- `test-output/` - TestNG and test execution reports
- `target/rerun.txt` - Failed scenarios that can be rerun
- `logs/automation.log` - Application or test logs

## Notes

- The main runner class is `src/test/java/testRunner/TestRunner.java`.
- The runner is configured to execute Cucumber scenarios in parallel.
- Feature file paths in the runner point to the `Features/` directory at the project root.

## Troubleshooting

If tests do not start correctly:

1. Confirm Java 14 is being used.
2. Run `mvn clean test` to rebuild the project from scratch.
3. Make sure the browser and driver versions are compatible.
4. Check the log file in `logs/automation.log` for details.

## Extending the project

If you want to add more tests:

1. Add a new `.feature` file inside `Features/`.
2. Create matching step definitions in `src/test/java/stepDefinitions/`.
3. Reuse or add page objects in `src/test/java/pageObjects/`.
4. Update test data in `testData/` if the scenario needs external input.

---



