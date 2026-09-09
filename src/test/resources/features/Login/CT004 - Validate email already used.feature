Feature: Login


  @CT4
  Scenario: Register with an email already in use

    Given I am on the registration page
    When I enter an email that is already registered
    Then an error message should be displayed
