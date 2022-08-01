
Feature: As a data consumer, I want UI and DB book information are match.

  Scenario: Verify book information with DB
    Given the "librarian" on the home page
    And the user navigates to "Books" page
    When the user searches for "Clean Code" book
    And  the user clicks edit book button
    Then book information must match the Database