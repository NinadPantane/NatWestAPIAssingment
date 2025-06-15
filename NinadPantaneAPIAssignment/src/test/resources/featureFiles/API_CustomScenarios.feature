Feature: API Test Assingment - Ninad Pantane - Custom Scenarios

  Background: 
    Given I have the header "Content-Type" with value "application/json"

  Scenario Outline: Verify Delete API Call for both Success and Error Message
    Given an item to be created with specification "<name>","<Generation>" and "<Price>"
    When the request to add the item is made
    Then a 200 response code is returned
    And a "<name>" is created
    When the created item is deleted
    Then a 200 response code is returned
    And verify the DELETE API Success message
    When the created item is deleted again
    Then a 404 response code is returned
    And verify the DELETE API Error message

    Examples: 
      | name           | Generation | Price  |
      | Apple iPad Air | 5th        | 519.99 |

  Scenario: Verify GET API Call Error Message
    Given "xyz" is the product id for an item
    When the request is made to fecth an item
    Then a 404 response code is returned
    And verify the GET API Error message

  Scenario: Verify POST API Call Error Message
    When POST call is invoked without Request Body
    Then a 400 response code is returned
    And verify the POST API Error message

  Scenario: Ability to list total number of items with Price
    When request is made to fecth the list of muliple item
    Then a 200 response code is returned
    And return total number of Devices from the list which has associated Price

  Scenario: Ability to list total number of items without Price
    When request is made to fecth the list of muliple item
    Then a 200 response code is returned
    And return total number of Devices from the list which dont have any associated Price
