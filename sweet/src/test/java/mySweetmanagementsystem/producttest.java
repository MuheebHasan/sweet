package mySweetmanagementsystem;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

public class producttest {
    private String productId;
    private String productName;
    private String actualMessage;
    private Map<String, String> updatedDetails;

    @Given("the owner is logged into the system")
    public void theOwnerIsLoggedIntoTheSystem() {
        // Assuming login is successful
        System.out.println("Owner is logged into the system.");
    }

    @Given("the productId is {string}")
    public void theProductIdIs(String productId) {
        this.productId = productId;
        System.out.println("Product ID is: " + productId);
    }

    @Given("the productName is {string}")
    public void theProductNameIs(String productName) {
        this.productName = productName;
        System.out.println("Product Name is: " + productName);
    }

    @Given("the Description is {string}")
    public void theDescriptionIs(String description) {
        System.out.println("Description is: " + description);
    }

    @Given("the price is {string}")
    public void thePriceIs(String price) {
        System.out.println("Price is: " + price);
    }

    @Given("the availability is {string}")
    public void theAvailabilityIs(String availability) {
        System.out.println("Availability is: " + availability);
    }

    @When("the owner adds the product")
    public void ownerAddsTheProduct() {
        actualMessage = Product.addProduct(productId, productName, "delicious", "350", "available");
        System.out.println(actualMessage);
    }

    @Then("owner can add product")
    public void ownerCanAddProduct() {
        if (actualMessage != null && actualMessage.equals("Product added successfully")) {
            System.out.println("Owner can add product.");
        } else {
            System.out.println("Failed to add product. Message: " + actualMessage);
        }
    }

    @Then("owner can't add product")
    public void ownerCanTAddProduct() {
        if (actualMessage != null && actualMessage.equals("Product ID already exists")) {
            System.out.println("Owner can't add product with an existing ID.");
        } else {
            System.out.println("Unexpected result: " + actualMessage);
        }
    }

    @When("the owner deletes the product")
    public void theOwnerDeletesTheProduct() {
        actualMessage = Product.deleteProduct(productId);
        System.out.println(actualMessage);
    }

    @Then("the product will be deleted")
    public void theProductWillBeDeleted() {
        if (actualMessage != null && actualMessage.equals("Product deleted successfully")) {
            System.out.println("The product was deleted.");
        } else {
            System.out.println("Failed to delete the product. Message: " + actualMessage);
        }
    }

    @Then("the product not deleted")
    public void theProductNotDeleted() {
        if (actualMessage != null && actualMessage.equals("Product ID does not exist")) {
            System.out.println("The product could not be deleted because the ID does not exist.");
        } else {
            System.out.println("Unexpected result: " + actualMessage);
        }
    }

    @When("the owner updates the details to:")
    public void theOwnerUpdatesTheDetailsTo(io.cucumber.datatable.DataTable dataTable) {
        updatedDetails = dataTable.asMap(String.class, String.class);
        actualMessage = Product.updateProduct(productId, updatedDetails);
        System.out.println("Updating product with details: " + updatedDetails);
    }

    @Then("the product details should be updated successfully")
    public void theProductDetailsShouldBeUpdatedSuccessfully() {
        if (actualMessage.equals("Product details updated successfully")) {
            System.out.println("Product details updated successfully.");
        } else {
            System.out.println("Failed to update product details. Message: " + actualMessage);
        }
    }

    @Then("the product details should not be updated")
    public void theProductDetailsShouldNotBeUpdated() {
        if (actualMessage.equals("Product ID does not exist")) {
            System.out.println("Product details were not updated as the product ID does not exist.");
        } else {
            System.out.println("Unexpected result: " + actualMessage);
        }
    }
}





