Feature: Login

  @CT1
  Scenario: Login with valid credentials

    Given I am on the login page
    When I enter a valid email and password
    Then the user should be logged in successfully