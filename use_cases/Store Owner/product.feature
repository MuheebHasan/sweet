Feature: product
  
  Scenario: owner can add product
    Given the productId is "0000"
    And the productName is "kunafa"
    And the Description is "delicious"
    And the price is "350"
    And the availability is "available"
    Then owner can add product

  Scenario: owner enters an ID that already exists
    Given the productId is "2"
    And the productName is "kunafa"
    And the Description is "delicious"
    And the price is "35"
    And the availability is "available"
    Then owner can't add product
    
    Scenario: owner can delete product
    Given the productId is "123"
    And the productName is "Old Product"
    Then owner can add product
    When the owner deletes the product
    Then the product will be deleted

  Scenario: owner enters an ID that does not exist to delete
    Given the productId is "999"
    Then the product not deleted
    
      Scenario: Owner updates an existing product's details
    Given the owner is logged into the system
    And the productId is "123"
    And the productName is "Old Product"
    When the owner updates the details to:
      | Product Name  | New Product Name |
      | Description   | New Description  |
      | Price          | 150              |
      | Availability   | unavailable      |
    Then the product details should be updated successfully

  Scenario: Owner tries to update a product with a non-existing productId
    Given the owner is logged into the system
    And the productId is "999"
    When the owner updates the details to:
      | Product Name  | New Product Name |
      | Description   | New Description  |
      | Price          | 150              |
      | Availability   | unavailable      |
    Then the product details should not be updated
    