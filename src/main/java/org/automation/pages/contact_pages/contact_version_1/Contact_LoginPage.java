package org.automation.pages.contact_pages.contact_version_1;

import org.automation.constants.Suite_Constants;
import org.automation.pages.base_page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Contact_LoginPage extends BasePage {

    @FindBy(xpath = "//*[@id='email']")
    private WebElement usernameField;

    @FindBy(xpath = "//*[@id='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@id='submit']")
    private WebElement submitBtn;

    public Contact_LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean verifyContactLoginTitle() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
            return driver.getTitle().equals(Suite_Constants.CONTACT_LOGIN_PAGE_TITLE);
        } catch (Exception e) {
            throw new RuntimeException("Error verifying the page title matches the expected Order Management Home Page title");
        }
    }

    public void enterUsername(String username) {
        enterText(usernameField, username);
    }

    public void enterPassword(String password) {
        enterText(passwordField, password);
    }

    public void clickLoginButton() {
        clickElement(submitBtn);
    }

}
