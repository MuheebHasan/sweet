package my_sweet_management_system;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import my_sweet_management_system.BeneficiaryUsers;

import java.util.Map;

public class ExplorationandPurchase {

    private BeneficiaryUsers beneficiaryUsers = new BeneficiaryUsers();

    @When("the user searches for {string}")
    public void the_user_searches_for(String searchTerm) {
        // تنفيذ البحث عن الوصفة
        beneficiaryUsers.searchForRecipe(searchTerm);
    }

    @Then("the system should display a list of recipes related to {string}")
    public void the_system_should_display_a_list_of_recipes_related_to(String searchTerm) {
        // التحقق من عرض الوصفات المتعلقة بالبحث
        beneficiaryUsers.verifyRecipeList(searchTerm);
    }

    @When("the user applies a filter for {string}")
    public void the_user_applies_a_filter_for(String filter) {
        // تطبيق فلتر للبحث
        beneficiaryUsers.applyFilter(filter);
    }

    @Then("the system should display only {string} recipes")
    public void the_system_should_display_only_recipes(String filter) {
        // التحقق من عرض الوصفات التي تتوافق مع الفلتر
        beneficiaryUsers.verifyFilteredRecipes(filter);
    }

    @Given("the user is on a store owner's page")
    public void the_user_is_on_a_store_owner_s_page() {
        // التحقق من أن المستخدم على صفحة مالك المتجر
        beneficiaryUsers.verifyStoreOwnerPage();
    }

    @When("the user selects a dessert to purchase")
    public void the_user_selects_a_dessert_to_purchase() {
        // اختيار الحلوى للشراء
        beneficiaryUsers.selectDessertForPurchase();
    }

    @When("the user enters the payment details:")
    public void the_user_enters_the_payment_details(io.cucumber.datatable.DataTable dataTable) {
        // إدخال تفاصيل الدفع
        Map<String, String> paymentDetails = dataTable.asMaps().get(0);
        beneficiaryUsers.enterPaymentDetails(paymentDetails);
    }

    @When("the user submits the payment")
    public void the_user_submits_the_payment() {
        // إرسال الدفع
        beneficiaryUsers.submitPayment();
    }

    @Then("the user should receive an email receipt")
    public void the_user_should_receive_an_email_receipt() {
        // التحقق من استلام إيصال الدفع عبر البريد الإلكتروني
        beneficiaryUsers.verifyEmailReceipt();
    }

    @When("the user navigates to the {string} section on a store owner's page")
    public void the_user_navigates_to_the_section_on_a_store_owner_s_page(String section) {
        // التنقل إلى القسم المحدد على صفحة مالك المتجر
        beneficiaryUsers.navigateToStoreOwnerSection(section);
    }

    @When("the user sends a message {string}")
    public void the_user_sends_a_message(String message) {
        // إرسال رسالة إلى مالك المتجر
        beneficiaryUsers.sendMessageToStoreOwner(message);
    }

    @Then("the message should be sent to the store owner")
    public void the_message_should_be_sent_to_the_store_owner() {
        // التحقق من إرسال الرسالة إلى مالك المتجر
        beneficiaryUsers.verifyMessageSentToStoreOwner();
    }

    @When("the user selects an order to provide feedback")
    public void the_user_selects_an_order_to_provide_feedback() {
        // اختيار طلب لتقديم ملاحظات
        beneficiaryUsers.selectOrderForFeedback();
    }

    @When("the user enters the feedback {string}")
    public void the_user_enters_the_feedback(String feedback) {
        // إدخال الملاحظات للطلب
        beneficiaryUsers.enterFeedback(feedback);
    }

    @When("the user submits the feedback")
    public void the_user_submits_the_feedback() {
        // إرسال الملاحظات
        beneficiaryUsers.submitFeedback();
    }

    @Then("the feedback should be visible under the product's reviews")
    public void the_feedback_should_be_visible_under_the_product_s_reviews() {
        // التحقق من ظهور الملاحظات تحت تقييمات المنتج
        beneficiaryUsers.verifyFeedbackUnderProductReviews();
    }

    @When("the user navigates to a shared recipe page")
    public void the_user_navigates_to_a_shared_recipe_page() {
        // التنقل إلى صفحة الوصفة المشتركة
        beneficiaryUsers.navigateToSharedRecipePage();
    }

    @Then("the feedback should be visible under the recipe's reviews")
    public void the_feedback_should_be_visible_under_the_recipe_s_reviews() {
        // التحقق من ظهور الملاحظات تحت تقييمات الوصفة
        beneficiaryUsers.verifyFeedbackUnderRecipeReviews();
    }
}

