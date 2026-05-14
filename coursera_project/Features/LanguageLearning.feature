Feature: Language Learning Course Extraction

  Scenario: Extract courses for different levels
    Given the user is on the Coursera homepage
    When the user navigates to the Language Learning section
    Then the user extracts up to 12 unique courses for "Beginner", "Intermediate", and "Advanced" levels