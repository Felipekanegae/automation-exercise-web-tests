Feature: Products


  @CT5
  Scenario: Remove products from the cart

    Given I have products in the cart
    When I remove a product from the cart
    Then the product should no longer be displayed in the cart