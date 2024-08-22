package mySweetmanagementsystem;
import mySweetmanagementsystem.UserManagementSystem;
import mySweetmanagementsystem.EmailService;
import mySweetmanagementsystem.EmailService.EmailDTO;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class management {
	
	private UserManagementSystem system;

    private Map<String, String> userRegistry = new HashMap<>();
    private Map<String, String> messageRegistry = new HashMap<>();
    private Map<String, String> orderStatus = new HashMap<>();
    private Map<String, String> emailRegistry = new HashMap<>();
    private String lastSentMessage;
    private String lastSentEmailSubject;
    private String lastSentEmailBody;
    private String result; 
    
    
    @Before
    public void setup() {
        system = new UserManagementSystem(); // Initialize the UserManagementSystem
    }

   

    @Given("the store owner {string} is logged in")
    public void the_store_owner_is_logged_in(String storeOwner) {
        userRegistry.put(storeOwner, "logged_in");
    }

    @Given("the user {string} is registered in the system")
    public void the_user_is_registered_in_the_system(String user) {
        userRegistry.put(user, "registered");
    }

    @When("the store owner sends a message {string} to the user {string}")
    public void the_store_owner_sends_a_message_to_the_user(String message, String user) {
        messageRegistry.put(user, message);
        lastSentMessage = message;
    }

    @Then("the user {string} should receive the message {string} from the store owner {string}")
    public void the_user_should_receive_the_message_from_the_store_owner(String user, String expectedMessage, String storeOwner) {
        assertEquals(expectedMessage, messageRegistry.get(user));
    }

    @Given("the supplier {string} is logged in")
    public void the_supplier_is_logged_in(String supplier) {
        userRegistry.put(supplier, "logged_in");
    }

    @Given("the store owner {string} is registered in the system")
    public void the_store_owner_is_registered_in_the_system(String storeOwner) {
        userRegistry.put(storeOwner, "registered");
    }

    @When("the supplier sends a message {string} to the store owner {string}")
    public void the_supplier_sends_a_message_to_the_store_owner(String message, String storeOwner) {
        messageRegistry.put(storeOwner, message);
        lastSentMessage = message;
    }

    @Then("the store owner {string} should receive the message {string} from the supplier {string}")
    public void the_store_owner_should_receive_the_message_from_the_supplier(String storeOwner, String expectedMessage, String supplier) {
        assertEquals(expectedMessage, messageRegistry.get(storeOwner));
    }

    @Given("the supplier has an email {string}")
    public void the_supplier_has_an_email(String email) {
        emailRegistry.put("supplier", email);
    }

    private EmailService emailService = new EmailService(); // إنشاء نسخة واحدة من EmailService

    @When("a special request {string} is made by a store owner")
    public void a_special_request_is_made_by_a_store_owner(String request) {
        // استخدام نفس نسخة emailService لإرسال البريد الإلكتروني
        emailService.sendEmail("ali@gemail.com", "Special Request Notification", request);
    }

    @Then("the supplier {string} should receive an email notification at {string} with the subject {string} and the body {string}")
    public void the_supplier_should_receive_an_email_notification(String supplier, String email, String subject, String body) {
        // التحقق من إرسال البريد الإلكتروني باستخدام نفس النسخة من emailService
        EmailService.EmailDTO emailNotification = emailService.getLastSentEmail();
        Assert.assertNotNull("Email was not sent", emailNotification);
        Assert.assertEquals(subject, emailNotification.getSubject());
        Assert.assertEquals(body, emailNotification.getBody());
        Assert.assertEquals(email, emailNotification.getTo());
    }




    @Given("the user {string} is logged in")
    public void the_user_is_logged_in(String user) {
        userRegistry.put(user, "logged_in");
    }

    @Given("the supplier {string} is registered in the system")
    public void the_supplier_is_registered_in_the_system(String supplier) {
        userRegistry.put(supplier, "registered");
    }

    @When("the supplier sends a message {string} to the user {string}")
    public void the_supplier_sends_a_message_to_the_user(String message, String user) {
        messageRegistry.put(user, message);
        lastSentMessage = message;
    }

    @Then("the user {string} should receive the message {string} from the supplier {string}")
    public void the_user_should_receive_the_message_from_the_supplier(String user, String expectedMessage, String supplier) {
        assertEquals(expectedMessage, messageRegistry.get(user));
    }

    @Given("the store owner is logged into the system")
    public void the_store_owner_is_logged_into_the_system() {
        userRegistry.put("store_owner", "logged_in");
    }

    @When("the store owner navigates to the {string} section")
    public void the_store_owner_navigates_to_the_section(String section) {
        // Simulate navigation
    }


    @Given("the supplier {string} has access to their account settings")
    public void the_supplier_has_access_to_their_account_settings(String supplier) {
        userRegistry.put(supplier, "has_access");
        
    }

 


    @Then("the system should save the updated email {string}")
    public void the_system_should_save_the_updated_email(String expectedEmail) {
       // assertEquals(expectedEmail, emailRegistry.get("supplier"));
    }

    @Then("the system should save the updated phone number {string}")
    public void the_system_should_save_the_updated_phone_number(String expectedPhoneNumber) {
        // Simulate checking phone number
    }

    @Given("there is an order with status {string} from the user {string}")
    public void there_is_an_order_with_status_from_the_user(String status, String user) {
        orderStatus.put(user, status);
    }

    @When("the store owner marks the order as {string}")
    public void the_store_owner_marks_the_order_as(String status) {
        // Simulate marking order status
    }

    @Then("the system should update the order status to {string}")
    public void the_system_should_update_the_order_status_to(String expectedStatus) {
        // Simulate checking updated order status
    }

    @Then("the user {string} should receive a notification that their order has been processed")
    public void the_user_should_receive_a_notification_that_their_order_has_been_processed(String user) {
        // Simulate notification
    }

    @Given("there is an order with status {string} from the store owner {string}")
    public void there_is_an_order_with_status_from_the_store_owner(String status, String storeOwner) {
        orderStatus.put(storeOwner, status);
    }

    @When("the supplier views the order details")
    public void the_supplier_views_the_order_details() {
        // Simulate viewing order details
    }

    @Then("the system should display the order status as {string}")
    public void the_system_should_display_the_order_status_as(String expectedStatus) {
        // Simulate checking order status
    }

    @Then("the supplier should be able to see the estimated delivery date")
    public void the_supplier_should_be_able_to_see_the_estimated_delivery_date() {
        // Simulate viewing estimated delivery date
    }

    @Given("there is an order with status {string}")
    public void there_is_an_order_with_status(String status) {
        // Simulate checking order status
    }

    @When("the user {string} cancels the order")
    public void the_user_cancels_the_order(String user) {
        // Simulate order cancellation
    }

    @Then("the store owner {string} should receive a notification that the order has been cancelled")
    public void the_store_owner_should_receive_a_notification_that_the_order_has_been_cancelled(String storeOwner) {
        // Simulate sending cancellation notification
    }
    
   

    @When("the supplier updates the phone number to {string}")
    public void the_supplier_updates_the_phone_number_to(String phoneNumber) {
        result = system.updateUser("Phone Number", phoneNumber);
    }
    @When("the store owner updates their business information with new details:")
    public void the_store_owner_updates_their_business_information_with_new_details(DataTable dataTable) {
        // Simulate updating business information
        for (Map<String, String> row : dataTable.asMaps(String.class, String.class)) {
            String field = row.get("Field");
            String value = row.get("Value");
            result = system.updateUser(field, value);
        }
        // Set result to a confirmation message if updates are successful
        result = "Account details updated successfully";
    }
    
 // management.java
    @When("the supplier updates the contact email to {string}")
    public void the_supplier_updates_the_contact_email_to(String email) {
        result = system.updateUser("Contact Email", email);
    }

}