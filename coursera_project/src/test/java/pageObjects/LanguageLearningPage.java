package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LanguageLearningPage extends BasePage {

    public LanguageLearningPage(WebDriver driver) {
        super(driver);
    }

    // Dynamic helper to handle Coursera's unstable sidebar
    private void safeClickFilter(String levelName) throws InterruptedException {
        By locator = By.xpath("//span[text()='" + levelName + "']/preceding-sibling::input | //span[contains(text(),'" + levelName + "')]");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < 3; i++) { // Retry up to 3 times
            try {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                Thread.sleep(1000);
                js.executeScript("arguments[0].click();", element);
                return; // Exit if success
            } catch (StaleElementReferenceException e) {
                Thread.sleep(1000); // Wait for DOM to stabilize and retry
            }
        }
    }

    public void selectLevel(String levelName) throws InterruptedException {
        System.out.println("Selecting level: " + levelName);
        safeClickFilter(levelName);
        Thread.sleep(5000); // Crucial: wait for the results to actually load
    }

    public void clickShowMore() {
        try {
            // Targets the 'Show More' specifically within the 'Level' filter group
            WebElement showMore = driver.findElement(By.cssSelector("button[aria-label^='Show'][aria-label*='more']"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", showMore);
            Thread.sleep(2000);
        } catch (Exception e) {
            // If it's already expanded, this will just skip gracefully
        }
    }

    public void extractCourseDetails(String levelName) {
        // Re-find the cards every time to avoid stale references
        List<WebElement> freshCards = driver.findElements(By.cssSelector("li.cds-9"));
        int count = 0;
        System.out.println("\n--- Level: " + levelName + " ---");

        for (WebElement card : freshCards) {
            if (count >= 12) break;
            try {
                String title = card.findElement(By.cssSelector(".cds-CommonCard-title")).getText();
                String partner = card.findElement(By.cssSelector(".cds-ProductCard-partnerNames")).getText();
                System.out.println((count + 1) + ". " + title + " (" + partner + ")");
                count++;
            } catch (Exception e) {
                // Skip cards that fail to load
            }
        }
    }

    public void unselectLevel(String levelName) throws InterruptedException {
        System.out.println("Unselecting level: " + levelName);
        safeClickFilter(levelName);
        Thread.sleep(2000);
    }
}