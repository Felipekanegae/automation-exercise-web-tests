Feature: Products


  @CT1
  Scenario: View product details

    Given I am on the products page
    When I select a product
    Then the product details should be displayed