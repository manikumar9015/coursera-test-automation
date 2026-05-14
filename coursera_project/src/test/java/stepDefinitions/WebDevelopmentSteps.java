package stepDefinitions;

import factory.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageObjects.HomePage;
import pageObjects.SearchResultsPage;

public class WebDevelopmentSteps {

    WebDriver driver;
    HomePage homePage;
    SearchResultsPage searchResultsPage;

    @Given("the user is on the home page")
    public void the_user_is_on_the_home_page() {
        BaseClass.getLogger().info("Initializing driver and navigating to Coursera Home Page..");
        driver = BaseClass.getDriver();
        homePage = new HomePage(driver);
    }

    @When("the user searches for {string}")
    public void the_user_searches_for(String topic) {
        BaseClass.getLogger().info("Searching for course topic: {}", topic);
        homePage.enterSearchTopic(topic);
        homePage.clickSearch();
        searchResultsPage = new SearchResultsPage(driver);
    }

    @When("the user applies {string} level and {string} language filters")
    public void the_user_applies_filters(String level, String language) {
        BaseClass.getLogger().info("Applying filters - Level: {}, Language: {}", level, language);

        BaseClass.getLogger().info("Selecting level filter..");
        searchResultsPage.filterByLevel(level);

        BaseClass.getLogger().info("Selecting language filter..");
        searchResultsPage.filterByLanguage(language);
    }

    @Then("the user extracts name, hours, and rating for the first 2 courses")
    public void the_user_extracts_course_data() {
        BaseClass.getLogger().info("Extracting metadata for the first two course results..");
        searchResultsPage.extractFirstTwoCourses();
        BaseClass.getLogger().info("Course data extraction completed successfully.");
    }
}