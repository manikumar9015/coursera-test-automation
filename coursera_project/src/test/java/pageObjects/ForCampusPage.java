package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ForCampusPage extends BasePage {

    // Locators
    @FindBy(id = "FirstName")
    private WebElement firstName;

    @FindBy(id = "LastName")
    private WebElement lastName;

    @FindBy(id = "Email")
    private WebElement email;

    @FindBy(id = "Phone")
    private WebElement phone;

    @FindBy(id = "Title")
    private WebElement roleDropdown;

    @FindBy(id = "Department")
    private WebElement deptDropdown;

    @FindBy(id = "Self_Reported_Needs__c")
    private WebElement needsDropdown;

    @FindBy(id = "Country")
    private WebElement countryDropdown;

    @FindBy(id = "State")
    private WebElement stateDropdown;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitBtn;

    @FindBy(id = "ValidMsgEmail")
    private WebElement emailError;

    public ForCampusPage(WebDriver driver) {
        super(driver);
    }

    // Actions
    public void enterContactDetails(String fName, String lName, String mail, String ph) {
        wait.until(ExpectedConditions.visibilityOf(firstName)).sendKeys(fName);
        lastName.sendKeys(lName);
        email.sendKeys(mail);
        phone.sendKeys(ph);
    }

    public void selectRole(String role) {
        wait.until(ExpectedConditions.elementToBeClickable(roleDropdown));
        new Select(roleDropdown).selectByVisibleText(role);
    }

    public void selectDepartment(String dept) {
        new Select(deptDropdown).selectByVisibleText(dept);
    }

    public void selectNeeds(String need) {
        new Select(needsDropdown).selectByVisibleText(need);
    }

    public void selectCountry(String country) {
        new Select(countryDropdown).selectByVisibleText(country);
    }

    public void selectState(String state) {
        new Select(stateDropdown).selectByVisibleText(state);
    }

    public void clickSubmit() {
        submitBtn.click();
    }

    public String getEmailErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(emailError)).getText();
    }
}