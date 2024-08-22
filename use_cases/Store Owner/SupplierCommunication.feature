Feature: Supplier Communication

  Scenario: Supplier tries to send a message to a non-existing owner
    Given the supplier is logged for communication
    And the supplier tries to send a message to an owner named "NonExistentOwner" with the message "Hello!" for communication
    When the supplier clicks on the "Communicate" button for communication
    Then the supplier should see a message for communication "Owner not found."

  Scenario: Supplier successfully sends a message to an existing owner
    Given the supplier is logged for communication
    And the supplier tries to send a message to an owner named "OwnerName" with the message "Hello!" for communication
    When the supplier clicks on the "Communicate" button for communication
    Then the supplier should see a message for communication "Owner not found."