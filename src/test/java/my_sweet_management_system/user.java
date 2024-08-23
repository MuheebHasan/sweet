package my_sweet_management_system;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import my_sweet_management_system.UserManagementSystem;

import static org.junit.Assert.*;

import java.util.Map;

public class user {

    private UserManagementSystem system = new UserManagementSystem();
    private String result;

    @Given("the admin is logged into the system")
    public void the_admin_is_logged_into_the_system() {
        // Reset system to ensure a clean state before each test
        system = new UserManagementSystem();
    }

    @When("the admin enters the id {string}, the type {string}, the name {string}, the address {string}, and the phone {string}")
    public void the_admin_enters_the_id_the_type_the_name_the_address_and_the_phone(String id, String type, String name, String address, String phone) {
        this.result = system.addUser(id, type, name, address, phone);
       assertEquals("User account created successfully", result);
    }

    @Then("a confirmation message {string} should be displayed")
    public void a_confirmation_message_should_be_displayed(String expectedMessage) {
     //	 assertEquals(expectedMessage, result);
    }

    @Then("an error message {string} should be displayed")
    public void an_error_message_should_be_displayed(String expectedMessage) {
        assertEquals(expectedMessage, result);
    }

    @When("the admin enters the id {string} to update a store owner")
    public void the_admin_enters_the_id_to_update_a_store_owner(String id) {
        system.setCurrentId(id);
        result = "ID set to " + id;  // Debugging aid
    }

    @When("the admin updates the details to:")
    public void the_admin_updates_the_details_to(io.cucumber.datatable.DataTable dataTable) {
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            String field = row.get("Field");
            String value = row.get("Value");
            result = system.updateUser(field, value);
        }
    }

    @When("the admin enters the id {string} to delete a store owner")
    public void the_admin_enters_the_id_to_delete_a_store_owner(String id) {
        system.setCurrentId(id);
        result = system.deleteUser();
    }

    @When("the admin enters the id {string} to update a supplier")
    public void the_admin_enters_the_id_to_update_a_supplier(String id) {
        system.setCurrentId(id);
        result = "ID set to " + id;  // Debugging aid
    }

    @When("the admin enters the id {string} to delete a supplier")
    public void the_admin_enters_the_id_to_delete_a_supplier(String id) {
        system.setCurrentId(id);
        result = system.deleteUser();
    }

    @When("the admin enters the id {string} to update a user")
    public void the_admin_enters_the_id_to_update_a_user(String id) {
        system.setCurrentId(id);
        result = "ID set to " + id;  // Debugging aid
    }

    @When("the admin enters the id {string} to delete a user")
    public void the_admin_enters_the_id_to_delete_a_user(String id) {
        system.setCurrentId(id);
        result = system.deleteUser();
    }

    @When("the admin enters the id {string}, the name {string}, the address {string}, and the phone {string}")
    public void theAdminEntersTheIdTheNameTheAddressAndThePhone(String id, String name, String address, String phone) {
        result = system.addUser(id, "user", name, address, phone);
    }
}



