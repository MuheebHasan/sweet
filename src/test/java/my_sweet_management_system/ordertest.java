package my_sweet_management_system;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
 
import java.util.Map;

public class ordertest {
    private String orderId;
    private String orderName;
    private String actualMessage;
    private String productId;
    private int quantity;
    private Map<String, String> updatedDetails;

    @Given("the orderId is {string}")
    public void theOrderIdIs(String orderId) {
        this.orderId = orderId;
        System.out.println("Order ID is: " + orderId);
    }

    @Given("the orderName is {string}")
    public void theOrderNameIs(String orderName) {
        this.orderName = orderName;
        System.out.println("Order Name is: " + orderName);
    }

    @When("the owner adds the order")
    public void ownerAddsTheOrder() {
        actualMessage = ooorder.addOrder(orderId, productId,quantity);
        System.out.println(actualMessage);
    }

    @Then("owner can add order")
    public void ownerCanAddOrder() {
        if (actualMessage != null && actualMessage.equals("Order added successfully")) {
            System.out.println("Owner can add order.");
        } else {
            System.out.println("Failed to add order. Message: " + actualMessage);
        }
    }

    @Then("owner can't add order")
    public void ownerCanTAddOrder() {
        if (actualMessage != null && actualMessage.equals("Order ID already exists")) {
            System.out.println("Owner can't add order with an existing ID.");
        } else {
            System.out.println("Unexpected result: " + actualMessage);
        }
    }

    @When("the owner deletes the order")
    public void theOwnerDeletesTheOrder() {
        actualMessage = ooorder.deleteOrder(orderId);
        System.out.println(actualMessage);
    }

    @Then("the order will be deleted")
    public void theOrderWillBeDeleted() {
        if (actualMessage != null && actualMessage.equals("Order deleted successfully")) {
            System.out.println("The order was deleted.");
        } else {
            System.out.println("Failed to delete the order. Message: " + actualMessage);
        }
    }

    @Then("the order will not be deleted")
    public void theOrderWillNotBeDeleted() {
        if (actualMessage != null && actualMessage.equals("Order ID does not exist")) {
            System.out.println("The order could not be deleted because the ID does not exist.");
        } else {
            System.out.println("Unexpected result: " + actualMessage);
        }
    }

    @When("the owner enters the orderId {string} to update an order")
    public void theOwnerEntersTheOrderIdToUpdateAnOrder(String orderId) {
        this.orderId = orderId;
        System.out.println("Order ID to update is: " + orderId);
    }

    @When("the owner updates the order with details:")
    public void theOwnerUpdatesTheOrderWithDetails(io.cucumber.datatable.DataTable dataTable) {
        updatedDetails = dataTable.asMap(String.class, String.class);
        actualMessage = ooorder.updateOrder(orderId, updatedDetails);
        System.out.println("Updating order with details: " + updatedDetails);
    }

    @Then("the order details should be updated successfully")
    public void theOrderDetailsShouldBeUpdatedSuccessfully() {
        if (actualMessage.equals("Order details updated successfully")) {
            System.out.println("Order details updated successfully.");
        } else {
            System.out.println("Failed to update order details. Message: " + actualMessage);
        }
    }

    @Then("the order details should not be updated")
    public void theOrderDetailsShouldNotBeUpdated() {
        if (actualMessage.equals("Order ID does not exist")) {
            System.out.println("Order details were not updated as the order ID does not exist.");
        } else {
            System.out.println("Unexpected result: " + actualMessage);
        }
    }
}



