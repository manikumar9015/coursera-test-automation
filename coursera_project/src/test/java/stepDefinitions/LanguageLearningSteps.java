package stepDefinitions;

import factory.BaseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HomePage;
import pageObjects.LanguageLearningPage;

public class LanguageLearningSteps {
    HomePage hp;
    LanguageLearningPage lp;

    @Given("the user is on the Coursera homepage")
    public void user_is_on_homepage() {
        hp = new HomePage(BaseClass.getDriver());
    }

    @When("the user navigates to the Language Learning section")
    public void navigate_to_language_learning() {
        hp.clickExplore();
        hp.clickLanguageLearning();
    }

    @Then("the user extracts up to {int} unique courses for {string}, {string}, and {string} levels")
    public void extract_courses(Integer max, String lv1, String lv2, String lv3) throws InterruptedException {
        lp = new LanguageLearningPage(BaseClass.getDriver());
        String[] levels = {lv1, lv2, lv3};

        for (String level : levels) {
            lp.selectLevel(level);
            lp.clickShowMore();
            lp.extractCourseDetails(level);
            lp.unselectLevel(level);
        }
    }
}