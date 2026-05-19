package stepDefinitions;

import factory.BaseClass;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.jsoup.Connection;
import org.openqa.selenium.WebDriver;
import pageObjects.ForCampusPage;
import pageObjects.HomePage;

import java.util.Map;

public class ForYouSteps {
    WebDriver driver;
    HomePage home;
    ForCampusPage forCampus;

    @Given("the user is in the home page")
    public void the_user_is_in_the_home_page() {
        BaseClass.getLogger().info("Navigating to Coursera Home Page..");
        driver  = BaseClass.getDriver();
        home = new HomePage(driver);
    }

    @When("User navigates to For Campus section")
    public void user_navigates_to_for_campus_section() {
        BaseClass.getLogger().info("Clicking on 'For Campus' link..");
        home.clickForCampus();
        forCampus = new ForCampusPage(driver);
    }

    @When("User fills the Ready to transform form with the following details:")
    public void user_fills_the_ready_to_transform_form_with_the_following_details(DataTable dataTable) {
        BaseClass.getLogger().info("Parsing form data from DataTable and filling details..");
        // Convert the DataTable to a Map for easy access
        Map<String, String> formData = dataTable.asMap(String.class, String.class);

        // Accessing values using the keys from your feature file
        String firstName = formData.get("FirstName");
        String lastName  = formData.get("LastName");
        String email     = formData.get("Email");
        String phone     = formData.get("Phone");
        String role      = formData.get("Role");
        String dept      = formData.get("Dept");
        String needs     = formData.get("Needs");
        String country   = formData.get("Country");
        String state     = formData.get("State");

        forCampus.enterContactDetails(firstName, lastName, email,phone);
        forCampus.selectRole(role);
        forCampus.selectDepartment(dept);
        forCampus.selectNeeds(needs);
        forCampus.selectCountry(country);
        forCampus.selectState(state);
    }

    @When("User clicks the submit button")
    public void user_clicks_the_submit_button() {
        BaseClass.getLogger().info("Clicking the Form Submit button..");
        forCampus.clickSubmit();
    }

    @Then("User should see the email error message and display it")
    public void user_should_see_the_email_error_message_and_display_it() {
        BaseClass.getLogger().info("Capturing and displaying email error message..");
        String errorMessage = forCampus.getEmailErrorMessage();
        System.out.println("Email error message: " + errorMessage);

        // Also log the actual result to the log file
        BaseClass.getLogger().info("Error message captured: {}", errorMessage);
    }
}
