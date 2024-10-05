package org.automation.pages.para_pages.para_version_1;

import org.automation.constants.Suite_Constants;
import org.automation.pages.base_page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * This class represents the Prov Home page and provides methods to interact with the page elements.
 */

public class Para_HomePage extends BasePage {

    @FindBy(xpath = "//a[text()='Log Out']")
    private WebElement logoutBtn;

    @FindBy(xpath = "//*[@class='title']")
    private WebElement welcomeText;

    public Para_HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean checkRegisterSuccessfully(String username){
        return welcomeText.getText().equals("Welcome " + username);
    }

    /**
     * Checks if the title of the Home page matches the expected title and handles the license popup if displayed.
     *
     * @return true if the title matches, false otherwise
     */
    public boolean getHomeTitle() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(logoutBtn));
            return driver.getTitle().equals(Suite_Constants.PARA_HOME_PAGE_TITLE);
        } catch (Exception e) {
            throw new RuntimeException("Error checking the title of Prov home page 'licenseBtn, falloutManagementBtn'");
        }
    }

    /**
     * Clicks on the Logout button.
     */
    public void clickLogout() {
        try {
            clickElement(logoutBtn);
        } catch (Exception e) {
            throw new RuntimeException("Error clicking the Prov Logout button 'userBtn, logoutBtn'");
        }
    }


}
