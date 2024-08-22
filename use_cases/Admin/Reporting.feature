Feature: Monitoring and Reporting

  Scenario: Admin monitors profits and generates financial reports
    Given the admin is logged into the system
    When the admin navigates to the "Financial Reports" section
    Then a financial report showing the profits should be generated
    And the report should include total profits, expenses, and net profit

  Scenario: Admin identifies best-selling products in each store
    Given the admin is logged into the system
    When the admin navigates to the "Sales Analysis" section
    Then a list of best-selling products in each store should be displayed
    And the list should include product names, quantities sold, and total sales for each store

  Scenario: Admin gathers and displays statistics on registered users by city
    Given the admin is logged into the system
    When the admin navigates to the "User Statistics" section
    Then a statistical report showing the number of registered users in each city should be displayed
    And the report should include city names (e.g., Nablus, Jenin) and the number of registered users for each city
