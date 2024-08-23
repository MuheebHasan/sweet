package my_sweet_management_system;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import my_sweet_management_system.BeneficiaryUsers;

import java.util.Map;

public class useraccounttt {

    private BeneficiaryUsers beneficiaryUsers = new BeneficiaryUsers();

    @Given("the user is on the sign-up page")
    public void the_user_is_on_the_sign_up_page() {
        // التحقق من أن المستخدم على صفحة التسجيل
        beneficiaryUsers.navigateToSignUpPage();
    }

    @When("the user enters the required details:")
    public void the_user_enters_the_required_details(io.cucumber.datatable.DataTable dataTable) {
        // إضافة تفاصيل التسجيل من البيانات المدخلة
        Map<String, String> userDetails = dataTable.asMaps().get(0);
        beneficiaryUsers.enterSignUpDetails(userDetails);
    }

    @When("the user submits the sign-up form")
    public void the_user_submits_the_sign_up_form() {
        // إرسال نموذج التسجيل
        beneficiaryUsers.submitSignUpForm();
    }

    @Then("the user should receive a confirmation email")
    public void the_user_should_receive_a_confirmation_email() {
        // التحقق من استلام رسالة التأكيد
        beneficiaryUsers.verifyConfirmationEmail();
    }

    @Given("the user is on the sign-in page")
    public void the_user_is_on_the_sign_in_page() {
        // التحقق من أن المستخدم على صفحة تسجيل الدخول
        beneficiaryUsers.navigateToSignInPage();
    }

    @When("the user enters the username {string} and the password {string}")
    public void the_user_enters_the_username_and_the_password(String username, String password) {
        // إدخال اسم المستخدم وكلمة المرور
        beneficiaryUsers.enterSignInCredentials(username, password);
    }

    @When("the user submits the sign-in form")
    public void the_user_submits_the_sign_in_form() {
        // إرسال نموذج تسجيل الدخول
        beneficiaryUsers.submitSignInForm();
    }

    @Then("the user should be redirected to their dashboard")
    public void the_user_should_be_redirected_to_their_dashboard() {
        // التحقق من إعادة توجيه المستخدم إلى لوحة التحكم
        beneficiaryUsers.verifyDashboardRedirect();
    }

    @Then("a welcome message {string} should be displayed")
    public void a_welcome_message_should_be_displayed(String message) {
        // التحقق من عرض رسالة الترحيب
        beneficiaryUsers.verifyWelcomeMessage(message);
    }

    @Given("the user is logged into the system")
    public void the_user_is_logged_into_the_system() {
        // التحقق من تسجيل دخول المستخدم إلى النظام
        beneficiaryUsers.verifyUserLoggedIn();
    }

    @When("the user navigates to the {string} section")
    public void the_user_navigates_to_the_section(String section) {
        // التحقق من تنقل المستخدم إلى القسم المحدد
        beneficiaryUsers.navigateToSection(section);
    }

    @When("the user updates their details:")
    public void the_user_updates_their_details(io.cucumber.datatable.DataTable dataTable) {
        // تحديث تفاصيل المستخدم استناداً إلى البيانات المدخلة
        Map<String, String> updatedDetails = dataTable.asMaps().get(0);
        beneficiaryUsers.updateUserDetails(updatedDetails);
    }

    @When("the user submits the update form")
    public void the_user_submits_the_update_form() {
        // إرسال نموذج التحديث
        beneficiaryUsers.submitUpdateForm();
    }

    @When("the user enters the details of the dessert creation:")
    public void the_user_enters_the_details_of_the_dessert_creation(io.cucumber.datatable.DataTable dataTable) {
        // إدخال تفاصيل إنشاء الحلوى
        Map<String, String> dessertDetails = dataTable.asMaps().get(0);
        beneficiaryUsers.enterDessertDetails(dessertDetails);
    }

    @When("the user submits the post")
    public void the_user_submits_the_post() {
        // إرسال المشاركة
        beneficiaryUsers.submitPost();
    }

    @Then("the post should be displayed on the user's profile")
    public void the_post_should_be_displayed_on_the_user_s_profile() {
        // التحقق من عرض المشاركة على ملف المستخدم
        beneficiaryUsers.verifyPostDisplayed();
    }
}

