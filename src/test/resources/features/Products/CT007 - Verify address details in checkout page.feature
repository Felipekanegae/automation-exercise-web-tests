Feature: Products


  @CT7
  Scenario: Verify address details on the checkout page

    Given I am logged in
    And I have products in the cart
    When I proceed to checkout
    Then the delivery address should be displayed correctly
    And the billing address should be displayed correctly