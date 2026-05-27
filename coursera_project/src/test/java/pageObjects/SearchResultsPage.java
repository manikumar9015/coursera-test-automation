package pageObjects;

import factory.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class SearchResultsPage extends BasePage {

    @FindBy(xpath = "//button[@data-testid='filter-dropdown-language']")
    private WebElement languageFilterBtn;

    @FindBy(xpath = "//button[@data-testid='filter-dropdown-productDifficultyLevel']")
    private WebElement levelFilterBtn;

    @FindBy(xpath = "//div[@class='css-ksf52d']")
    private List<WebElement> filterOptions;

    @FindBy(xpath = "//button[@class='cds-149 cds-button-disableElevation cds-button-primary css-jqo50y']")
    private WebElement applyBtn;

    @FindBy(className = "cds-ProductCard-content")
    private List<WebElement> courseCards;

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public void filterByLanguage(String langName) {
        clickFilterDropdown(languageFilterBtn);
        selectOptionFromList(langName);
        clickApply();
    }

    public void filterByLevel(String levelName) {
        clickFilterDropdown(levelFilterBtn);
        selectOptionFromList(levelName);
        clickApply();
    }

    public void extractFirstTwoCourses() {
        wait.until(ExpectedConditions.visibilityOfAllElements(courseCards));

        for (int i = 0; i < 2 && i < courseCards.size(); i++) {
            WebElement card = courseCards.get(i);

            // Extract Title
            String title = card.findElement(By.cssSelector("h3.cds-CommonCard-title")).getText();

            // Extract Rating safely
            String rating;
            try {
                // Using a stable parent-child CSS selector instead of the dynamic class
                rating = card.findElement(By.cssSelector(".cds-RatingStat-meter > span")).getText();
            } catch (org.openqa.selenium.NoSuchElementException e) {
                // Fallback if the course does not have a rating yet
                rating = "No rating available";
            }

            // Extract Duration
            String fullMetadata = card.findElement(By.cssSelector(".cds-CommonCard-metadata p")).getText();
            String[] parts = fullMetadata.split("·");
            String duration = parts[parts.length - 1].trim();

            System.out.println("--- Course " + (i + 1) + " ---");
            System.out.println("Title: " + title);
            System.out.println("Rating: " + rating);
            System.out.println("Learning Duration: " + duration);
        }
    }

    // --- Fixed Helper Method ---
    // --- Safe Click Helper Method ---
    private void clickFilterDropdown(WebElement element) {
        // 1. Wait for visibility to ensure the element is present and has dimensions
        wait.until(ExpectedConditions.visibilityOf(element));

        // 2. Scroll the element to the center of the viewport to avoid sticky headers
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);

        // 3. Wait until the element is technically clickable
        wait.until(ExpectedConditions.elementToBeClickable(element));

        try {
            // Attempt normal Selenium click
            element.click();
        } catch (Exception e) {
            // Fallback: Force click via JavaScript if normal click is intercepted
            BaseClass.getLogger().warn("Normal click failed for filter dropdown, attempting JS Click...");
            js.executeScript("arguments[0].click();", element);
        }
    }

    private void selectOptionFromList(String optionText) {
        wait.until(ExpectedConditions.visibilityOfAllElements(filterOptions));
        for (WebElement option : filterOptions) {
            if (option.getText().toLowerCase().contains(optionText.toLowerCase())) {
                option.click();
                break;
            }
        }
    }

    private void clickApply() {
        wait.until(ExpectedConditions.elementToBeClickable(applyBtn)).click();
        wait.until(ExpectedConditions.invisibilityOf(applyBtn));
    }
}