package org.automation.tests.para.para_version_1;

import com.browserstack.local.Local;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.automation.pages.para_pages.para_version_1.Para_HomePage;
import org.automation.pages.para_pages.para_version_1.Para_LoginPage;
import org.automation.pages.para_pages.para_version_1.Para_RegisterPage;
import org.automation.utilities.EnvConfigFileReader;
import org.automation.utilities.HelperClass;
import org.automation.utilities.ScenarioContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Para_Tests {
    private static final Logger logger = LogManager.getLogger(Para_Tests.class);
    private WebDriver driver;
    private ScenarioContext scenarioContext;
    private EnvConfigFileReader config;
    private Para_HomePage homePage;
    private Para_LoginPage loginPage;
    private Para_RegisterPage registerPage;
    private String username;

    @BeforeSuite
    public void setupExtentReport() {
        scenarioContext = ScenarioContext.getInstance();
        scenarioContext.setContext(Thread.currentThread().getName() + "_ParaTestName", "PARA");
    }

    @BeforeClass
    public void setupTest() {
        config = new EnvConfigFileReader();
        driver = HelperClass.getInstance().getDriver();
        loginPage = new Para_LoginPage(driver);
        homePage = new Para_HomePage(driver);
        registerPage = new Para_RegisterPage(driver);
        driver.get(config.getParaUrl());
        username = HelperClass.getSaltString() + "@gmail.com";
        scenarioContext.setContext(Thread.currentThread().getName() + "_webdriver", driver);
    }

    @Test(priority = -1, groups = "PARA_Test_Cases", description = "Verify to register new user in Para Application")
    public void registerUser(){
        logger.info("[Test Case] Verify to register new user in Para Application");
        Assert.assertTrue(loginPage.verifyParaLoginTitle());
        logger.info("Landed on Para Login Page");
        Reporter.log("Landed on Para Login Page");
        loginPage.clickOnRegister();
        Assert.assertTrue(registerPage.verifyParaRegisterTitle());
        logger.info("Landed on Register user page");
        Reporter.log("Landed on Register user page");
        registerPage.enterFirstName("Vinay");
        registerPage.enterLastName("Dhole");
        registerPage.enterAddress("Wakad");
        registerPage.enterCity("Pune");
        registerPage.enterState("Maharashtra");
        registerPage.enterZipCode("411057");
        registerPage.enterSsn("11-222-3333");
        registerPage.enterUsername(username);
        registerPage.enterPassword(config.getParaPassword());
        registerPage.enterConfirmPassword(config.getParaPassword());
        registerPage.clickOnRegister();
        logger.info("Entered all the user information and clicked on register");
        Reporter.log("Entered all the user information and clicked on register");
        Assert.assertTrue(homePage.checkRegisterSuccessfully(username));
        homePage.clickLogout();
        Assert.assertTrue(loginPage.verifyParaLoginTitle());
        logger.info("Logged out successfully !!");
        Reporter.log("Logged out successfully !!");
    }

    @Test(priority = 0, groups = "PARA_Test_Cases", description = "Verify if Para application is up and running")
    public void checkParaUrl() {
        logger.info("[Test Case] Verify if Para application is up and running");
        try {
            performLogin();
        } catch (Exception e) {
            logger.error("Error during login: ", e);
            Assert.fail("Login failed due to an exception.");
        }
        Reporter.log("Application is up and running");
    }

    private void performLogin() {
        Assert.assertTrue(loginPage.verifyParaLoginTitle());
        logger.info("Landed on Para Login Page");
        Reporter.log("Landed on Para Login Page");
        loginPage.enterUsername(username);
        logger.info("Entered username");
        Reporter.log("Entered username");
        loginPage.enterPassword(config.getParaPassword());
        logger.info("Entered password");
        Reporter.log("Entered password");
        loginPage.clickLoginButton();
        logger.info("Click on Submit button");
        Reporter.log("Click on Submit button");
    }

    @Test(priority = 1, groups = "PARA_Test_Cases", description = "Verify if Para user is able to login")
    public void paraLoginTest() {
        logger.info("[Test Case] Verify if Para user is able to login");
        Assert.assertTrue(homePage.getHomeTitle());
        Reporter.log("Logged In successfully");
        logger.info("Logged In successfully");
        Reporter.log("Verified Para login functionality");
    }

    @Test(priority = 2, groups = "PARA_Test_Cases", description = "Verify Para logout functionality")
    public void checkLogout() {
        logger.info("[Test Case] Verify Para logout functionality");
        homePage.clickLogout();
        Reporter.log("Clicked on Logout button");
        Assert.assertTrue(loginPage.verifyParaLoginTitle());
        Reporter.log("Landed on login page");
        logger.info("Verified Para logout functionality");
        Reporter.log("Verified Para logout functionality");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            HelperClass.closeDriver();
        }
    }
}
