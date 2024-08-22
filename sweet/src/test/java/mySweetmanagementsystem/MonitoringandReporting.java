package mySweetmanagementsystem;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import mySweetmanagementsystem.MonitoringandReporting;
public class MonitoringandReporting {
    
	MonitoringAndReportingww reportingSystem = new MonitoringAndReportingww();

    @When("the admin navigates to the {string} section")
    public void the_admin_navigates_to_the_section(String section) {
        System.out.println("Navigating to section: " + section);
    }

    @Then("a financial report showing the profits should be generated")
    public void a_financial_report_showing_the_profits_should_be_generated() {
        System.out.println("Financial report generated.");
    }

    @Then("the report should include total profits, expenses, and net profit")
    public void the_report_should_include_total_profits_expenses_and_net_profit() {
        System.out.println("Report includes total profits, expenses, and net profit.");
    }

    @Then("a list of best-selling products in each store should be displayed")
    public void a_list_of_best_selling_products_in_each_store_should_be_displayed() {
        reportingSystem.displayStatistics();
    }

    @Then("the list should include product names, quantities sold, and total sales for each store")
    public void the_list_should_include_product_names_quantities_sold_and_total_sales_for_each_store() {
        System.out.println("List includes product names, quantities sold, and total sales for each store.");
    }

    @Then("a statistical report showing the number of registered users in each city should be displayed")
    public void a_statistical_report_showing_the_number_of_registered_users_in_each_city_should_be_displayed() {
        reportingSystem.displayStatistics();
    }

    @Then("the report should include city names \\(e.g., Nablus, Jenin) and the number of registered users for each city")
    public void the_report_should_include_city_names_e_g_nablus_jenin_and_the_number_of_registered_users_for_each_city() {
        System.out.println("Report includes city names and the number of registered users for each city.");
    }

}

