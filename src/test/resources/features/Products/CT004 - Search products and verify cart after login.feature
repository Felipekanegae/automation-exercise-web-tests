Feature: Products


  @CT4
  Scenario: Search products and verify cart after login

    Given I am logged in
    And I am on the products page
    When I add the products to the cart
    Then the products should be displayed in the cart