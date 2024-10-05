package org.automation.pages.para_pages.para_version_1;

import org.automation.constants.Suite_Constants;
import org.automation.pages.base_page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Para_LoginPage extends BasePage {

    @FindBy(xpath = "//*[@name='username']")
    private WebElement usernameField;

    @FindBy(xpath = "//*[@name='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@type='submit']")
    private WebElement loginBtn;

    @FindBy(xpath = "//*[text()='Register']")
    private WebElement registerLink;

    public Para_LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean verifyParaLoginTitle() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(loginBtn));
            return driver.getTitle().equals(Suite_Constants.PARA_LOGIN_PAGE_TITLE);
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
        clickElement(loginBtn);
    }

    public void clickOnRegister(){
        clickElement(registerLink);
    }

}
