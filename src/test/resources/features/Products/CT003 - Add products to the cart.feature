Feature: Products


  @CT3
  Scenario: Add products to the cart

    Given I am on the products page
    When I add the products to the cart
    Then the products should be displayed in the cart