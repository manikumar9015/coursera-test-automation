package stepDefinitions;

import factory.BaseClass;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.jsoup.Connection;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageObjects.ForCampusPage;
import pageObjects.HomePage;
import utilities.DataReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForCampusSteps {
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

    @When("User fills the Ready to transform form with the details for rowindex {string}")
    public void user_fills_the_ready_to_transform_form_with_the_details_for_rowindex(String rowIndex) {
        BaseClass.getLogger().info("Parsing form data from Excel and filling details..");
        List<HashMap<String, String>> datamap = null;

        try {
            // Note: Update the Excel file name and sheet name to match your actual file
            datamap = DataReader.data(System.getProperty("user.dir") + "\\testData\\formData.xlsx", "Sheet1");
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Failed to read data from excel file");
        }

        // Convert string row index to integer (subtract 1 because list index starts at 0)
        int index = Integer.parseInt(rowIndex) - 1;

        // Accessing values using the column headers from your Excel sheet
        String firstName = datamap.get(index).get("FirstName");
        String lastName  = datamap.get(index).get("LastName");
        String email     = datamap.get(index).get("Email");
        String phone     = datamap.get(index).get("Phone");
        String role      = datamap.get(index).get("Role");
        String dept      = datamap.get(index).get("Dept");
        String needs     = datamap.get(index).get("Needs");
        String country   = datamap.get(index).get("Country");
        String state     = datamap.get(index).get("State");

        // Passing the fetched data to your Page Object methods
        forCampus.enterContactDetails(firstName, lastName, email, phone);
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
