Feature: Login


  @CT4
  Scenario: Validate email already used

    Given I am on the registration page
    When I enter an email that is already registered
    Then an error message should be displayed
