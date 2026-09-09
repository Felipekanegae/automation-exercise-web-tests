Feature: Contact


  @CT1
  Scenario: Send a message through the Contact Us form

    Given I am on the Contact Us page
    When I fill in the contact form
    Then the message is sent successfully
