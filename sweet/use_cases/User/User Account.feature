Feature: Beneficiary User Account Management

  Scenario: Sign up for a new account
    Given the user is on the sign-up page
    When the user enters the required details:
      | Field        | Value              |
      | Username     | Farah              |
      | Password     | 11Aagu58              |
      | Email        | Farah@gemail.com   |
    And the user submits the sign-up form
    Then a confirmation message "User account created successfully" should be displayed
    And the user should receive a confirmation email

  Scenario: Sign in to the platform
    Given the user is on the sign-in page
    When the user enters the username "Farah" and the password "11Aagu58"
    And the user submits the sign-in form
    Then the user should be redirected to their dashboard
    And a welcome message "Welcome, Farah " should be displayed
    
    Scenario: Manage personal account details
    Given the user is logged into the system
    When the user navigates to the "Account Settings" section
    And the user updates their details:
      | Field        | New Value          |
      | Email        | Farah@gemail.com   |
      | Password     | Farah33            |
    And the user submits the update form
    Then a confirmation message "Account details updated successfully" should be displayed

  Scenario: Post and share personal dessert creations
    Given the user is logged into the system
    When the user navigates to the "Post Creation" section
    And the user enters the details of the dessert creation:
      | Field        | Value              |
      | Title        | Chocolate Cake     |
      | Description  | A delicious chocolate cake recipe. |
      
    And the user submits the post
    Then the post should be displayed on the user's profile
    And a confirmation message "Dessert creation shared successfully" should be displayed
    
    
    