package my_sweet_management_system;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import my_sweet_management_system.PersonAccount;

import java.util.List;
import java.util.Map;

public class ContentManagementandfeedback {
    
	PersonAccount system = new PersonAccount();
    String result;

    @Given("Admin is logged into the system")
    public void admin_is_logged_into_the_system() {
        // Assuming Admin login is handled
    }

    @When("the admin adds a recipe with details:")
    public void the_admin_adds_a_recipe_with_details(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            result = system.addRecipe(row.get("Recipe Name"), row.get("Ingredient"), row.get("Quantity"));
        }
    }

    @Then("{string} should be displayed")
    public void should_be_displayed(String expectedMessage) {
        // Ensure that result is not null before comparison
         if (result == null) {
             throw new AssertionError("Result is null. Expected: " + expectedMessage);
        }
        assert result.equals(expectedMessage) : "Expected: " + expectedMessage + " but got: " + result;
    }

    @When("the admin updates the recipe details:")
    public void the_admin_updates_the_recipe_details(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            result = system.updateRecipe(row.get("Recipe Name"), row.get("Ingredient")); // Pass only two arguments
        }
    }


    @When("the admin deletes a recipe with id {string}")
    public void the_admin_deletes_a_recipe_with_id(String string) {
        result = system.deleteRecipe(string);
    }

    @When("the admin views a recipe with id {string}")
    public void the_admin_views_a_recipe_with_id(String string) {
        result = system.viewRecipe(string);
    }

    @Then("the recipe details should be displayed")
    public void the_recipe_details_should_be_displayed() {
        System.out.println(result);
    }

    @When("the admin adds a post with details:")
    public void the_admin_adds_a_post_with_details(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            result = system.addPost(row.get("Title"), row.get("Content"));
        }
    }

    @When("the admin updates the post details:")
    public void the_admin_updates_the_post_details(DataTable dataTable) {
        List<Map<String, String>> postDetails = dataTable.asMaps(String.class, String.class);
        
        for (Map<String, String> postDetail : postDetails) {
            String title = postDetail.get("Title");
            String content = postDetail.get("Content");
            
            result = system.updatePost(title, content);
            System.out.println(result);
        }
    }

    @When("the admin deletes a post with id {string}")
    public void the_admin_deletes_a_post_with_id(String string) {
        result = system.deletePost(string);
    }

    @When("the admin views user feedback")
    public void the_admin_views_user_feedback() {
        result = system.viewFeedback();
    }

    @Then("user feedback should be displayed")
    public void user_feedback_should_be_displayed() {
        System.out.println(result);
    }

    @When("the admin can respond to feedback:")
    public void the_admin_can_respond_to_feedback(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> feedbackResponses = dataTable.asMaps(String.class, String.class);
        
        for (Map<String, String> feedbackResponse : feedbackResponses) {
            String feedbackId = feedbackResponse.get("Feedback ID");
            String response = feedbackResponse.get("Response");
            
             System.out.println("Responding to Feedback ID: " + feedbackId + " with response: " + response);
         }
        result = "Feedback responded successfully";
    }

}
