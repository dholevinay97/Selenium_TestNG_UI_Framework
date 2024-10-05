package org.automation.pages.para_pages.para_version_1;

import org.automation.constants.Suite_Constants;
import org.automation.pages.base_page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Para_RegisterPage extends BasePage {

    @FindBy(xpath = "//input[@id='customer.firstName']")
    private WebElement firstNameTextbox;

    @FindBy(xpath = "//input[@id='customer.lastName']")
    private WebElement lastNameTextbox;

    @FindBy(xpath = "//input[@id='customer.address.street']")
    private WebElement addressTextbox;

    @FindBy(xpath = "//input[@id='customer.address.city']")
    private WebElement cityTextbox;

    @FindBy(xpath = "//input[@id='customer.address.state']")
    private WebElement stateTextbox;

    @FindBy(xpath = "//input[@id='customer.address.zipCode']")
    private WebElement zipCodeTextbox;

    @FindBy(xpath = "//input[@id='customer.ssn']")
    private WebElement ssnTextbox;

    @FindBy(xpath = "//input[@id='customer.username']")
    private WebElement usernameTextbox;

    @FindBy(xpath = "//input[@id='customer.password']")
    private WebElement passwordTextbox;

    @FindBy(xpath = "//input[@id='repeatedPassword']")
    private WebElement confirmTextbox;

    @FindBy(xpath = "//input[@value='Register']")
    private WebElement registerBtn;

    public Para_RegisterPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void enterFirstName(String fName) {
        enterText(firstNameTextbox, fName);
    }

    public void enterLastName(String lName) {
        enterText(lastNameTextbox, lName);
    }

    public void enterAddress(String address) {
        enterText(addressTextbox, address);
    }

    public void enterCity(String city) {
        enterText(cityTextbox, city);
    }

    public void enterState(String state) {
        enterText(stateTextbox, state);
    }

    public void enterZipCode(String zipCode) {
        enterText(zipCodeTextbox, zipCode);
    }

    public void enterSsn(String ssn) {
        enterText(ssnTextbox, ssn);
    }

    public void enterUsername(String username) {
        enterText(usernameTextbox, username);
    }

    public void enterPassword(String password) {
        enterText(passwordTextbox, password);
    }

    public void enterConfirmPassword(String password) {
        enterText(confirmTextbox, password);
    }

    public void clickOnRegister() {
        clickElement(registerBtn);
    }

    public boolean verifyParaRegisterTitle(){
        try {
            wait.until(ExpectedConditions.elementToBeClickable(registerBtn));
            return driver.getTitle().equals(Suite_Constants.PARA_REGISTER_PAGE_TITLE);
        } catch (Exception e) {
            throw new RuntimeException("Error verifying the page title matches the expected Order Management Home Page title");
        }
    }

}
