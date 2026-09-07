Feature: Products


  @CT2
  Scenario: Search for a product

    Given I am on the products page
    When I enter a product name
    Then the product should be displayed