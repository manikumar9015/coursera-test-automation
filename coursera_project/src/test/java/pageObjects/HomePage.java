package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    // Locators
    @FindBy(linkText = "For Campus")
    private WebElement forCampusLink;

    @FindBy(xpath = "(//span[normalize-space()='Explore'])[1]")
    WebElement btnExplore;

    @FindBy(xpath = "//a[normalize-space()='Language Learning']")
    WebElement linkLanguageLearning;

    @FindBy(id = "search-autocomplete-input")
    private WebElement searchInput;

    @FindBy(xpath = "//button[@aria-label='Search']")
    private WebElement searchButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickForCampus() {
        wait.until(ExpectedConditions.elementToBeClickable(forCampusLink));

        try {
            // 2. Try the normal click first
            forCampusLink.click();
        } catch (Exception e) {
            // 3. If it fails or times out, force the click via JavaScript
            js.executeScript("arguments[0].click();", forCampusLink);
        }
    }

    public void clickExplore() {
        Actions actions = new Actions(driver);
        actions.moveToElement(btnExplore).perform();
    }

    public void clickLanguageLearning() {
        linkLanguageLearning.click();
    }

    public void enterSearchTopic(String topic) {
        wait.until(ExpectedConditions.visibilityOf(searchInput)).sendKeys(topic);
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }
}