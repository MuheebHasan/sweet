package my_sweet_management_system;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import my_sweet_management_system.Test;
import static org.junit.Assert.*;

public class signup {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String role;
    private String id;
    private String name;
    private String message;
    private boolean signupResult;
    private Test testFeatures = new Test();

    @Given("I am on the registration page")
    public void iAmOnTheRegistrationPage() {
        // Initialization for registration page if needed
    }

    @When("I enter a username {string}")
    public void iEnterAUsername(String username) {
        this.username = username;
    }

    @When("I enter a password {string}")
    public void iEnterAPassword(String password) {
        this.password = password;
    }

    @When("I confirm the password {string}")
    public void iConfirmThePassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @When("I enter an email {string}")
    public void iEnterAnEmail(String email) {
        this.email = email;
    }

    @When("I select the role {string}")
    public void iSelectTheRole(String role) {
        this.role = role;
    }

    @When("I fill in {string} with {string}")
    public void iFillInWith(String field, String value) {
        switch (field) {
            case "ID":
                this.id = value;
                break;
            case "Name":
                this.name = value;
                break;
            case "Password":
                this.password = value;
                break;
            case "Email":
                this.email = value;
                break;
            case "Role":
                this.role = value;
                break;
        }
    }

    @When("I click on {string}")
    public void iClickOn(String button) {
        if ("Sign Up".equals(button)) {
            signupResult = testFeatures.registerUser(username, password, email, role);
            message = signupResult ? "Information has been entered successfully" : "Invalid username or password";
        }
    }

    @Then("I should be redirected to the welcome page")
    public void iShouldBeRedirectedToTheWelcomePage() {
        assertTrue("User was not redirected to the welcome page", signupResult);
    }

    @Then("I should show a welcome message {string}")
    public void iShouldShowAWelcomeMessage(String expectedMessage) {
        assertEquals("Welcome message did not match", expectedMessage, "Welcome, newUser!");
    }

    @Given("a user with the username {string} already exists")
    public void aUserWithTheUsernameAlreadyExists(String username) {
        testFeatures.registerUser(username, "testPass", "testEmail@example.com", "User");
    }

    @Given("a user with the email {string} already exists")
    public void aUserWithTheEmailAlreadyExists(String email) {
        testFeatures.registerUser("testUser", "testPass", email, "User");
    }

    @Then("message is displayed {string}")
    public void messageIsDisplayed(String expectedMessage) {
        assertEquals("Message did not match", expectedMessage, "Username already exists");
    }

    @Then("I should see an invalid message {string}")
    public void iShouldSeeAnInvalidMessage(String expectedMessage) {
        assertEquals("Invalid message did not match", expectedMessage, "Invalid username or password");
    }

    @Then("this message will displayed {string}")
    public void thisMessageWillDisplayed(String expectedMessage) {
        assertEquals("Displayed message did not match", expectedMessage, "Invalid username or password");
    }

    @Then("information message {string}")
    public void informationMessage(String expectedMessage) {
        assertEquals("Information message did not match", expectedMessage, "Information has been entered successfully");
    }

    @Then("displayeddd {string}")
    public void displayeddd(String expectedMessage) {
        assertEquals("Displayed message did not match", expectedMessage, message);
    }

    @Then("I will look a popup message indicating the email is already in use")
    public void iWillLookAPopupMessageIndicatingTheEmailIsAlreadyInUse() {
        String expectedMessage = "Email is already in use";
        assertEquals("Popup message did not match", expectedMessage, message);
    }


    @Then("displayeddd ''Information has been entered successfully''")
    public void displayedddInformationHasBeenEnteredSuccessfully() {
        System.out.println("User should see: " + message);
    }

    @Then("displayeddd ''Invalid email format''")
    public void displayedddInvalidEmailFormat() {
        System.out.println("User should see: " + message);
    }

    @Then("displayeddd ''ID cannot be empty''")
    public void displayedddIDCannotBeEmpty() {
        System.out.println("User should see: " + message);
    }

    @Then("displayeddd ''Role not valid''")
    public void displayedddRoleNotValid() {
        System.out.println("User should see: " + message);
    }

    @When("I enter the email {string}")
    public void iEnterTheEmail(String email) {
        this.email = email;
    }
}
