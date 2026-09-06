Feature: Login


  @CT3
  Scenario: Login with invalid credentials

    Given I am on the login page
    When I enter invalid credentials
    Then an authentication error message should be displayed
