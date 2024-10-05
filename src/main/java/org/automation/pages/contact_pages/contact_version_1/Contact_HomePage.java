package org.automation.pages.contact_pages.contact_version_1;

import org.automation.constants.Suite_Constants;
import org.automation.pages.base_page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * This class represents the Order Management Home Page and provides methods to interact with the page elements.
 */
public class Contact_HomePage extends BasePage {

    @FindBy(xpath = "//*[@id='logout']")
    private WebElement logoutBtn;

    /**
     * Constructor to initialize the OM_HomePage with the WebDriver and configure WebDriverWait.
     *
     * @param driver the WebDriver instance to interact with the browser
     */
    public Contact_HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Checks if the title of the Home page matches the expected title and handles the license popup if displayed.
     *
     * @return true if the title matches, false otherwise
     */
    public boolean getHomeTitle() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(logoutBtn));
            return driver.getTitle().equals(Suite_Constants.CONTACT_HOME_PAGE_TITLE);
        } catch (Exception e) {
            throw new RuntimeException("Error checking the title of Prov home page");
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
