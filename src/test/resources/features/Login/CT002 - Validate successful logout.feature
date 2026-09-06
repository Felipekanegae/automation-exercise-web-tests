Feature: Login


  @CT2
  Scenario: Validate successful logout

    Given I am logged in
    When I log out
    Then I should be logged out successfully