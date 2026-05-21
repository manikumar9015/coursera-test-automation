Feature: For Campus Form Validation

  Description: This feature validates that the "Ready to transform" form
  correctly identifies invalid input data on the For Campus page.

#  Background:
#    Given User is on the Coursera homepage

  Scenario Outline: Verify error message for invalid email format
    Given the user is in the home page
    When User navigates to For Campus section
    And User fills the Ready to transform form with the details for rowindex "<rowindex>"
    And User clicks the submit button
    Then User should see the email error message and display it

    Examples:
      | rowindex |
      | 1        |