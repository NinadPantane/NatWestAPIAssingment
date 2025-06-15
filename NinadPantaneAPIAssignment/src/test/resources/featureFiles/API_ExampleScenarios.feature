Feature: API Test Assingment - Ninad Pantane - Example Scenarios

  Background: 
    Given I have the header "Content-Type" with value "application/json"

  Scenario: Verify an item can be created
    Given a "Apple MacBook Pro 16" item is created
    And is a "Intel Core i9" CPU model
    And has a price of "1849.99"
    When the request to add the item is made
    Then a 200 response code is returned
    And a "Apple MacBook Pro 16" is created

  Scenario: Ability to return an item
    Given "13" is the product id for an item
    When the request is made to fecth an item
    Then a 200 response code is returned
    And return the item deatils

  Scenario: Ability to list multiple items
    When request is made to fecth the list of muliple item
    Then a 200 response code is returned
    And return all Device Names from the list
