Feature: Beneficiary User Exploration and Purchase

  Scenario: User browses and searches for dessert recipes
    Given the user "Farah" is logged in
    When the user navigates to the "Recipes" section
    And the user searches for "Chocolate Cake"
    Then the system should display a list of recipes related to "Chocolate Cake"

  Scenario: User filters recipes based on dietary needs or food allergies
    Given the user "Farah" is logged in
    When the user navigates to the "Recipes" section
    And the user applies a filter for "Gluten-Free"
    Then the system should display only "Gluten-Free" recipes

  Scenario: Purchase desserts directly from store owners
    Given the user is on a store owner's page
    When the user selects a dessert to purchase
    And the user enters the payment details:
      | Field        | Value              |
      | Credit Card  | 1234-5678-9012-3456|
      | Expiry Date  | 8/25              |
      | CVV          | 123                |
    And the user submits the payment
    Then a confirmation message "Purchase completed successfully" should be displayed
    And the user should receive an email receipt



  Scenario: Directly communicate with store owners and suppliers for inquiries or assistance
    Given the user is logged into the system
    When the user navigates to the "Contact" section on a store owner's page
    And the user sends a message "I have a question about your chocolate cake recipe."
    Then the message should be sent to the store owner
    And a confirmation message "Message sent successfully" should be displayed

  Scenario: Provide feedback on purchased products
    Given the user is logged into the system
    When the user navigates to the "Order History" section
    And the user selects an order to provide feedback
    And the user enters the feedback "The chocolate cake was delicious!"
    And the user submits the feedback
    Then a confirmation message "Thank you for your feedback" should be displayed
    And the feedback should be visible under the product's reviews

  Scenario: Provide feedback on shared recipes
    Given the user is logged into the system
    When the user navigates to a shared recipe page
    And the user enters the feedback "Great recipe! Easy to follow."
    And the user submits the feedback
    Then a confirmation message "Thank you for your feedback" should be displayed
    And the feedback should be visible under the recipe's reviews
    
    
    
