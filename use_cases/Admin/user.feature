Feature:  Accounts

################################add########################################################################
  Scenario: Admin adds a new  owner
    Given the admin is logged into the system
    When the admin enters the id "1", the name "Reem", the address " Nablus ", and the phone "5551234"
    Then a confirmation message "User account created successfully" should be displayed

  Scenario: Admin tries to add a owner with an existing id
    Given the admin is logged into the system
    When the admin enters the id "1", the name "Reem", the address " Nablus", and the phone "5551234"
    Then an error message "ID already exists" should be displayed

  Scenario: Admin adds a new user
    Given the admin is logged into the system
    When the admin enters the id "2", the name "Farah", the address " Nablus ", and the phone "5555678"
    Then a confirmation message "User account created successfully" should be displayed

  Scenario: Admin tries to add a user with an existing id
    Given the admin is logged into the system
    When the admin enters the id "2", the name "Farah", the address " Nablus ", and the phone "5555678"
    Then an error message "ID already exists" should be displayed

  Scenario: Admin adds a new supplier
    Given the admin is logged into the system
    When the admin enters the id "3", the name "Ali", the address " Jenin ", and the phone "5559101"
    Then a confirmation message "User account created successfully" should be displayed

  Scenario: Admin tries to add a supplier with an existing id
    Given the admin is logged into the system
    When the admin enters the id "3", the name "Ali", the address " Jenin ", and the phone "5559101"
    Then an error message "ID already exists" should be displayed

#############################################update############################################
   
 Scenario: Admin updates a  owner's details
    Given the admin is logged into the system
    When the admin enters the id "1" to update a store owner
    And the admin updates the details to:
      | Field     | Value          |
      | Name      | Reem           |
      | Address   | Ramallah       |
      | Phone     | 5551235        |
    Then a confirmation message "User account updated successfully" should be displayed

  Scenario: Admin tries to update a  owner with a non-existing id
    Given the admin is logged into the system
    When the admin enters the id "999" to update a store owner
    And the admin updates the details to:
      | Field     | Value          |
      | Name      | Reem           |
      | Address   | Ramallah       |
      | Phone     | 5551235        |
    Then an error message "ID does not exist" should be displayed

  Scenario: Admin updates a user's details
    Given the admin is logged into the system
    When the admin enters the id "2" to update a user
    And the admin updates the details to:
      | Field     | Value          |
      | Name      | Farah B        |
      | Address   | Hebron         |
      | Phone     | 5555679        |
    Then a confirmation message "User account updated successfully" should be displayed

  Scenario: Admin tries to update a user with a non-existing id
    Given the admin is logged into the system
    When the admin enters the id "999" to update a user
    And the admin updates the details to:
      | Field     | Value          |
      | Name      | Farah B        |
      | Address   | Hebron         |
      | Phone     | 5555679        |
    Then an error message "ID does not exist" should be displayed

  Scenario: Admin updates a supplier's details
    Given the admin is logged into the system
    When the admin enters the id "3" to update a supplier
    And the admin updates the details to:
      | Field     | Value          |
      | Name      | Ali A          |
      | Address   | Bethlehem      |
      | Phone     | 5559102        |
    Then a confirmation message "User account updated successfully" should be displayed

  Scenario: Admin tries to update a supplier with a non-existing id
    Given the admin is logged into the system
    When the admin enters the id "999" to update a supplier
    And the admin updates the details to:
      | Field     | Value          |
      | Name      | Ali A          |
      | Address   | Bethlehem      |
      | Phone     | 5559102        |
    Then an error message "ID does not exist" should be displayed

########################################delete#####################################################
    Scenario: Admin deletes a owner
    Given the admin is logged into the system
    When the admin enters the id "1" to delete a store owner
    Then a confirmation message "User account deleted successfully" should be displayed

  Scenario: Admin tries to delete a owner with a non-existing id
    Given the admin is logged into the system
    When the admin enters the id "999" to delete a store owner
    Then an error message "ID does not exist" should be displayed

  Scenario: Admin deletes a user
    Given the admin is logged into the system
    When the admin enters the id "2" to delete a user
    Then a confirmation message "User account deleted successfully" should be displayed

  Scenario: Admin tries to delete a user with a non-existing id
    Given the admin is logged into the system
    When the admin enters the id "999" to delete a user
    Then an error message "ID does not exist" should be displayed

  Scenario: Admin deletes a supplier
    Given the admin is logged into the system
    When the admin enters the id "3" to delete a supplier
    Then a confirmation message "User account deleted successfully" should be displayed

  Scenario: Admin tries to delete a supplier with a non-existing id
    Given the admin is logged into the system
    When the admin enters the id "999" to delete a supplier
    Then an error message "ID does not exist" should be displayed