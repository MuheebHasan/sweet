package my_sweet_management_system;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import my_sweet_management_system.ProductSearchtest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductSearch {

     private List<ProductSearchtest> searchResults;

    @Given("the user searches for a product with ID {string}")
    public void the_user_searches_for_a_product_with_id(String id) {
        searchResults = ProductSearchtest.searchProductById(id);
    }

    @Then("the system should display the product with ID {string}")
    public void the_system_should_display_the_product_with_id(String id) {
        searchResults = ProductSearchtest.searchProductById(id);
    	 
          assertEquals(id, searchResults.get(0).getProductId());
    }

    @Then("the system should display the product with ID {string} not found")
    public void the_system_should_display_the_product_with_id_not_found(String id) {
        assertTrue(searchResults.isEmpty());
    }

    @Given("the user searches for a product with name {string}")
    public void the_user_searches_for_a_product_with_name(String name) {
        searchResults = ProductSearchtest.searchProductByName(name);
    }

    @Then("the system should display products with the name {string}")
    public void the_system_should_display_products_with_the_name(String name) {
        assertTrue(searchResults.stream().allMatch(product -> product.getProductName().equalsIgnoreCase(name)));
    }

    @Then("the system should display products with the name {string} not found")
    public void the_system_should_display_products_with_the_name_not_found(String name) {
        assertTrue(searchResults.isEmpty());
    }

    @Given("the user searches for a product with description {string}")
    public void the_user_searches_for_a_product_with_description(String description) {
        searchResults = ProductSearchtest.searchProductByDescription(description);
    }

    @Then("the system should display products with the description {string}")
    public void the_system_should_display_products_with_the_description(String description) {
        assertTrue(searchResults.stream().allMatch(product -> product.getDescription().equalsIgnoreCase(description)));
    }

    @Then("the system should display products with the description {string} not found")
    public void the_system_should_display_products_with_the_description_not_found(String description) {
        assertTrue(searchResults.isEmpty());
    }

    @Given("the user searches for a product with availability {string}")
    public void the_user_searches_for_a_product_with_availability(String availability) {
        searchResults = ProductSearchtest.searchProductByAvailability(availability);
    }

    @Then("the system should display products with the availability {string}")
    public void the_system_should_display_products_with_the_availability(String availability) {
        assertTrue(searchResults.stream().allMatch(product -> product.getAvailability().equalsIgnoreCase(availability)));
    }

    @Then("the system should display products with the availability {string} not found")
    public void the_system_should_display_products_with_the_availability_not_found(String availability) {
        assertTrue(searchResults.isEmpty());
    }

    @Then("all product shown")
    public void all_product_shown() {
        ProductSearchtest.allProductShown();
    }
}
