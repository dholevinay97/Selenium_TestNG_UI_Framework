package org.automation.pages.base_page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger logger;

    public BasePage(WebDriver driver) {
        Properties prop = loadProperties("browser_config.properties");
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(prop.getProperty("explicitWait", "30")))); // Default to 30 seconds if property is missing
        this.logger = LogManager.getLogger(this.getClass());
    }

    protected void clickElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click element: " + e.getMessage(), e);
        }
    }

    protected void enterText(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter text: " + e.getMessage(), e);
        }
    }

    protected String getElementText(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get element text: " + e.getMessage(), e);
        }
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            throw new RuntimeException("Failed to check element visibility: " + e.getMessage(), e);
        }
    }

    protected boolean isElementEnabled(WebElement element) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(element)).isEnabled();
        } catch (Exception e) {
            throw new RuntimeException("Failed to check element enabled: " + e.getMessage(), e);
        }
    }

    private Properties loadProperties(String fileName) {
        Properties prop = new Properties();
        try (InputStream contentStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            if (contentStream != null) {
                prop.load(contentStream);
            } else {
                throw new IOException("Property file '" + fileName + "' not found in the classpath");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
