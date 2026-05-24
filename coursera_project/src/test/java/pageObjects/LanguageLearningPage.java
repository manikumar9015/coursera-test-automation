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
        // Wait up to 8 seconds for the element to be clickable (presence + visible + enabled)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        try {
            element.click();
        } catch (WebDriverException e) {
            // Fallback: JS click if regular click fails (covers intercept/stale/click intercept)
            js.executeScript("arguments[0].click();", element);
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