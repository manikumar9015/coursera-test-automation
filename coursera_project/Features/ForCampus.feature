Feature: For Campus Form Validation

  Description: This feature validates that the "Ready to transform" form
  correctly identifies invalid input data on the For Campus page.

#  Background:
#    Given User is on the Coursera homepage

  Scenario: Verify error message for invalid email format
    Given the user is in the home page
    When User navigates to For Campus section
    And User fills the Ready to transform form with the following details:
      | FirstName | Mr                   |
      | LastName  | Nags                 |
      | Email     | nagsemail            |
      | Phone     | 9023909823           |
      | Role      | CEO                  |
      | Dept      | International        |
      | Needs     | Courses for myself   |
      | Country   | India                |
      | State     | Karnataka            |
    And User clicks the submit button
    Then User should see the email error message and display it