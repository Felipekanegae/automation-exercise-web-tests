Feature: Products


  @CT6
  Scenario: Add a review to a product

    Given I am on the product details page
    When I submit a product review
    Then a review confirmation message should be displayed