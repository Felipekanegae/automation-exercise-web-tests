Feature: Products


  @CT1
  Scenario: Verify All Products and product detail page

    Given I am on the products page
    When I select a product
    Then the product details should be displayed