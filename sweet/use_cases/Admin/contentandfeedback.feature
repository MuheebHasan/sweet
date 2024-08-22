Feature: Content Management and Feedback

  Scenario: Admin adds a recipe
    Given Admin is logged into the system
    When the admin adds a recipe with details:
      | Recipe Name | Ingredient | Quantity |
      | Cake        | Flour       | 2 cups   |
      | Cake        | Sugar       | 1 cup    |
    Then "Recipe added successfully" should be displayed

  Scenario: Admin updates a recipe
    Given Admin is logged into the system
    When the admin updates the recipe details:
      | Recipe Name | Ingredient | Quantity |
      | Cake        | Flour       | 3 cups   |
    Then "Recipe updated successfully" should be displayed

  Scenario: Admin deletes a recipe
    Given Admin is logged into the system
    When the admin deletes a recipe with id "Cake"
    Then "Recipe deleted successfully" should be displayed

  Scenario: Admin views a recipe
    Given Admin is logged into the system
    When the admin views a recipe with id "Cake"
    Then the recipe details should be displayed

  Scenario: Admin adds a post
    Given Admin is logged into the system
    When the admin adds a post with details:
      | Title       | Content         |
      | First Post  | This is the first post. |
    Then "Post added successfully" should be displayed

  Scenario: Admin updates a post
    Given Admin is logged into the system
    When the admin updates the post details:
      | Title       | Content           |
      | First Post  | Updated content. |
    Then "Post updated successfully" should be displayed

  Scenario: Admin deletes a post
    Given Admin is logged into the system
    When the admin deletes a post with id "First Post"
    Then "Post deleted successfully" should be displayed

  Scenario: Admin views user feedback
    Given Admin is logged into the system
    When the admin views user feedback
    Then user feedback should be displayed

  Scenario: Admin responds to feedback
  Given Admin is logged into the system
  When the admin can respond to feedback:
    | Feedback ID | Response                      |
    | 1           | Thank you for your feedback!  |
  Then "Feedback responded successfully" should be displayed
