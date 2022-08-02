
@us01
Feature: As a data consumer, I want the user information are stored in mySql DB correctly in users table.
  Background:
    Given Establish the database connection
  @db
  Scenario: verify users has unique IDs
    When Execute query to get all IDs from users
    Then verify all users has unique ID

  @db@ui
  Scenario: verify users table columns
    When Execute query to get all columns
    Then verify the below columns are listed in result

      | id            |
      | full_name     |
      | email         |
      | password      |
      | user_group_id |
      | image         |
      | extra_data    |
      | status        |
      | is_admin      |
      | start_date    |
      | end_date      |
      | address       |
