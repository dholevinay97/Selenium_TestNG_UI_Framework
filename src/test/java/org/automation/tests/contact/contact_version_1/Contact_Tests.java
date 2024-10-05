package org.automation.tests.contact.contact_version_1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.automation.pages.contact_pages.contact_version_1.*;
import org.automation.utilities.EnvConfigFileReader;
import org.automation.utilities.HelperClass;
import org.automation.utilities.ScenarioContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.net.URL;

public class Contact_Tests {

    private static final Logger logger = LogManager.getLogger(Contact_Tests.class);
    private WebDriver driver;
    private ScenarioContext scenarioContext;
    private EnvConfigFileReader config;
    private Contact_HomePage homePage;
    private Contact_LoginPage loginPage;

    @BeforeSuite
    public void setupExtentReport() {
        scenarioContext = ScenarioContext.getInstance();
        scenarioContext.setContext(Thread.currentThread().getName() + "_ContactTestName", "CONTACT");
        logger.info("Extent report setup completed.");
    }

    @BeforeClass
    public void setupTest() {
        try {
            config = new EnvConfigFileReader();
            scenarioContext = ScenarioContext.getInstance();
            driver = HelperClass.getInstance().getDriver();
            driver.get(config.getContactUrl());
            loginPage = new Contact_LoginPage(driver);
            homePage = new Contact_HomePage(driver);
            scenarioContext.setContext(Thread.currentThread().getName() + "_webdriver", driver);
            logger.info("Test setup completed.");
        } catch (Exception e) {
            logger.error("Error during test setup", e);
            throw new SkipException("Skipping tests due to setup error: " + e.getMessage());
        }
    }

    @Test(priority = 0, groups = "Contact_Test_Cases", description = "Verify if Contact application is up and running")
    public void checkContactUrl() {
        logger.info("[Test Case] Verify if Contact application is up and running");
        Assert.assertTrue(loginPage.verifyContactLoginTitle());
        logger.info("Landed on Contact Login Page");
        loginPage.enterUsername(config.getContactUsername());
        logger.info("Entered username");
        loginPage.enterPassword(config.getContactPassword());
        logger.info("Entered password");
        loginPage.clickLoginButton();
        Reporter.log("Application is up and running");
        logger.info("Clicked on Submit button");
    }

    @Test(priority = 1, groups = "Contact_Test_Cases", description = "Verify if Contact user is able to login")
    public void contactLoginTest() {
        logger.info("[Test Case] Verify if Contact user is able to login");
        Assert.assertTrue(homePage.getHomeTitle());
        Reporter.log("Logged In successfully");
        logger.info("Logged In successfully");
    }

    @Test(priority = 2, groups = "Contact_Test_Cases", description = "Verify Contact logout functionality")
    public void checkLogout() {
        logger.info("[Test Case] Verify Contact logout functionality");
        homePage.clickLogout();
        Assert.assertTrue(loginPage.verifyContactLoginTitle());
        Reporter.log("Verified Contact logout functionality");
        logger.info("Verified Contact logout functionality");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Driver closed successfully.");
        }
    }
}
