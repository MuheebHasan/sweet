Feature: Product Search

Scenario Outline: Search for a product by ID and found it
  Given the user searches for a product with ID <id>
  Then the system should display the product with ID <id>
  Examples:
    | id  | string |
    | "11" | "1"    |
    | "34" | "2"    |

Scenario: Search for a product by ID but not found
  Given the user searches for a product with ID "0000"
  Then the system should display the product with ID "0000" not found

Scenario: Search for a product by name and found it
  Given the user searches for a product with name "kunafa"
  Then the system should display products with the name "kunafa"

Scenario: Search for a product by name but name not found
  Given the user searches for a product with name "-"
  Then the system should display products with the name "-" not found

Scenario: Search for a product by description or part of it and found it
  Given the user searches for a product with description "delicious"
  Then the system should display products with the description "delicious"

Scenario: Search for a product by description and not found
  Given the user searches for a product with description "-"
  Then the system should display products with the description "-" not found

Scenario: Search for a product by availability
  Given the user searches for a product with availability "available"
  Then the system should display products with the availability "available"

Scenario: Search for a product by availability but not found
  Given the user searches for a product with availability "-"
  Then the system should display products with the availability "-" not found
  
  Scenario: owner show all product 
    Then all product shown