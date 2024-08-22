Feature: Communication and Notification

  Scenario: Store owner sends a message to a user
    Given the store owner "Reem" is logged in
    And the user "Farah" is registered in the system
    When the store owner sends a message "Special discount on sweets this week!" to the user "Farah"
    Then the user "Farah" should receive the message "Special discount on sweets this week!" from the store owner "Reem"

  Scenario: Supplier sends a message to a store owner
    Given the supplier "Ali" is logged in
    And the store owner "Reem" is registered in the system
    When the supplier sends a message "New batch of ingredients available" to the store owner "Reem"
    Then the store owner "Reem" should receive the message "New batch of ingredients available" from the supplier "Ali"

 
  Scenario: Supplier receives a notification for a special request via email
    Given the supplier "Ali" is logged in
    And the supplier has an email "ali@gemail.com"
    When a special request "Urgent need for sugar supply" is made by a store owner
    Then the supplier "Ali" should receive an email notification at "ali@gemail.com" with the subject "Special Request Notification" and the body "Urgent need for sugar supply"

  Scenario: User receives a message from a store owner
    Given the user "Farah" is logged in
    And the store owner "Reem" is registered in the system
    When the store owner sends a message "Your order is ready for pickup" to the user "Farah"
    Then the user "Farah" should receive the message "Your order is ready for pickup" from the store owner "Reem"

  Scenario: User receives a message from a supplier
    Given the user "Farah" is logged in
    And the supplier "Ali" is registered in the system
    When the supplier sends a message "New ingredients available" to the user "Farah"
    Then the user "Farah" should receive the message "New ingredients available" from the supplier "Ali"

    
    
    
    
    
    
 
  Scenario: Manage account details and update business information
    Given the store owner is logged into the system
    When the store owner navigates to the "Account Management" section
    And the store owner updates their business information with new details:
      | Field            | Value              |
      | Business Name    | Reem's Sweets      |
      | Address          | Nablus Sweet Street |
      | Contact Number   | 123-456-7890       |
    Then a confirmation message "Account details updated successfully" should be displayed
    
    
      Scenario: Supplier updates account details
    Given the supplier "Ali" is logged in
    And the supplier "Ali" has access to their account settings
    When the supplier updates the contact email to "ali@supplier.com"
    And the supplier updates the phone number to "123-456-7890"
    Then the system should save the updated email "ali@supplier.com"
    And the system should save the updated phone number "123-456-7890"
    Then a confirmation message "Account details updated successfully" should be displayed
    
    
    

  Scenario: Store owner processes an order
    Given the store owner "Reem" is logged in
    And there is an order with status "Pending" from the user "Farah"
    When the store owner marks the order as "Processed"
    Then the system should update the order status to "Processed"
    And the user "Farah" should receive a notification that their order has been processed

  Scenario: Supplier tracks an order status
    Given the supplier "Ali" is logged in
    And there is an order with status "Shipped" from the store owner "Reem"
    When the supplier views the order details
    Then the system should display the order status as "Shipped"
    And the supplier should be able to see the estimated delivery date

  Scenario: Customer cancels an order
    Given the user "Farah" is logged in
    And there is an order with status "Pending"
    When the user "Farah" cancels the order
    Then the system should update the order status to "Cancelled"
    And the store owner "Reem" should receive a notification that the order has been cancelled
    
    
    