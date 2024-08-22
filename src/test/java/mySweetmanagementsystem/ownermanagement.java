package mySweetmanagementsystem;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;

public class ownermanagement {

@Given("the store owner is logged in")
public void the_store_owner_is_logged_in() {
   
}

@When("the store owner adds a discount with details:")
public void the_store_owner_adds_a_discount_with_details(io.cucumber.datatable.DataTable dataTable) {
   
}

@Then("a {string} message should be displayed")
public void a_message_should_be_displayed(String string) {
  
}

@Given("a discount {string} with rate {string} is added")
public void a_discount_with_rate_is_added(String string, String string2) {
   
}

@When("the store owner adds an order with details:")
public void the_store_owner_adds_an_order_with_details(io.cucumber.datatable.DataTable dataTable) {
    
}

@Then("the order total price should be {string}")
public void the_order_total_price_should_be(String string) {
   
}

}
