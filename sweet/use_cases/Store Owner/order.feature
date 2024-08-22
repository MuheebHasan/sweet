Feature: Order
  
  Scenario: owner can add order
    Given the orderId is "12"
    And the orderName is "Chocolate Cake"
    And the Description is "delicious"
    And the price is "120"
    And the availability is "available"
    When the owner adds the order
    Then owner can add order

  Scenario: owner enters an ID that already exists
    Given the orderId is "29"
    And the orderName is "Chocolate Cake"
    And the Description is "delicious"
    And the price is "135"
    And the availability is "available"
    When the owner adds the order
    Then owner can't add order
    
  Scenario: owner can delete order
    Given the orderId is "12"
    When the owner deletes the order
    Then the order will be deleted

  Scenario: owner enters an ID that does not exist to delete
    Given the orderId is "99"
    When the owner deletes the order
    Then the order will not be deleted
    
  Scenario: Owner updates an existing order's details
    Given the owner is logged into the system
    When the owner enters the orderId "29" to update an order
    And the owner updates the order with details:
      | Field        | Value          |
      | orderName    | Vanilla Cupcakes |
      | Description  | very delicious |
      | price        | 270            |
      | availability | unavailable    |
    Then the order details should be updated successfully

  Scenario: Owner tries to update an order with a non-existing orderId
    Given the owner is logged into the system
    When the owner enters the orderId "9999" to update an order
    And the owner updates the order with details:
      | Field        | Value          |
      | orderName    | ice cream      |
      | Description  | cold and sweet |
      | price        | 50             |
      | availability | available      |
    Then the order details should not be updated
