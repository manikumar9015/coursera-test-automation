Feature: Web Development Courses Search on Coursera

  Scenario: Search and extract beginner web development courses
    Given the user is on the home page
    When the user searches for "Web development"
    And the user applies "Beginner" level and "English" language filters
    Then the user extracts name, hours, and rating for the first 2 courses