Feature: Store Owner Management

  Scenario: Implement dynamic discounts
    Given the store owner is logged in
    When the store owner adds a discount with details:
      | Field        | Value           |
      | Discount Name| Summer Sale      |
      | Discount Rate| 20%              |
      | Products     | All products     |
    Then a "Discount added successfully" message should be displayed

  Scenario: Apply discount to an order
    Given the store owner is logged in
    And a discount "Summer Sale" with rate "20%" is added
    When the store owner adds an order with details:
      | Field        | Value           |
      | Order ID     | 001             |
      | Product ID   | 10              |
      | Quantity     | 5               |
    Then the order total price should be "80.0"
