package mySweetmanagementsystem;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

public class SupplierComm {

    private SupplierService supplierService;
    private String result;
    private static final String LOGGED_IN_USER = "supplier@example.com"; // Replace with a valid test email

    @Given("the supplier is logged for communication")
    public void the_supplier_is_logged_for_communication() {
        // Create SupplierService instance with the logged-in user email
        supplierService = new SupplierService(LOGGED_IN_USER);
        // Ensure the file is set up correctly in your test environment
    }

    @Given("the supplier tries to send a message to an owner named {string} with the message {string} for communication")
    public void the_supplier_tries_to_send_a_message_to_an_owner_named_with_the_message_for_communication(String ownerName, String message) {
        result = supplierService.sendMessage(ownerName, message);
    }

    @When("the supplier clicks on the {string} button for communication")
    public void the_supplier_clicks_on_the_button_for_communication(String buttonName) {
        // Simulate clicking the button if needed
    }

    @Then("the supplier should see a message for communication {string}")
    public void the_supplier_should_see_a_message_for_communication(String expectedMessage) {
        assertEquals(expectedMessage, result);
    }
}

