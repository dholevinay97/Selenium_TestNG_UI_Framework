package org.automation.utilities;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class HelperClass {
    private static volatile HelperClass instance;
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static final Logger LOGGER = Logger.getLogger(HelperClass.class.getName());

    private HelperClass() {
        // Private constructor to prevent instantiation
    }

    private void initDriver() {
        BrowserConfigFileReader browserConfigFileReader = new BrowserConfigFileReader();
        String browser = browserConfigFileReader.getBrowser();
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = getChromeOptions(browserConfigFileReader);
                    WebDriverManager.chromedriver().setup();
                    tlDriver.set(new ChromeDriver(chromeOptions));
                    break;
                case "firefox":
                    FirefoxOptions firefoxOptions = getFirefoxOptions(browserConfigFileReader);
                    WebDriverManager.firefoxdriver().setup();
                    tlDriver.set(new FirefoxDriver(firefoxOptions));
                    break;
                case "edge":
                    EdgeOptions edgeOptions = getEdgeOptions(browserConfigFileReader);
                    WebDriverManager.edgedriver().setup();
                    tlDriver.set(new EdgeDriver(edgeOptions));
                    break;
                default:
                    throw new IllegalStateException("Unexpected browser value: " + browser);
            }
            LOGGER.info("Driver initialized for browser: " + browser);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error initializing driver for browser: " + browser, e);
            throw new RuntimeException("Driver initialization failed", e);
        }
    }

    private static FirefoxOptions getFirefoxOptions(BrowserConfigFileReader browserConfigFileReader) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setAcceptInsecureCerts(true);
        firefoxOptions.addArguments("start-maximized");
        configureCommonOptions(firefoxOptions, browserConfigFileReader);
        return firefoxOptions;
    }

    private static EdgeOptions getEdgeOptions(BrowserConfigFileReader browserConfigFileReader) {
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setAcceptInsecureCerts(true);
        edgeOptions.addArguments("start-maximized");
        configureCommonOptions(edgeOptions, browserConfigFileReader);
        return edgeOptions;
    }

    private static ChromeOptions getChromeOptions(BrowserConfigFileReader browserConfigFileReader) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setAcceptInsecureCerts(true);
        chromeOptions.addArguments("start-maximized");
        configureCommonOptions(chromeOptions, browserConfigFileReader);
        return chromeOptions;
    }

    private static void configureCommonOptions(Object options, BrowserConfigFileReader browserConfigFileReader) {
        if (browserConfigFileReader.getHeadless().equalsIgnoreCase("true")) {
            if (options instanceof ChromeOptions) {
                ((ChromeOptions) options).addArguments("--headless=new");
            } else if (options instanceof FirefoxOptions){
                ((FirefoxOptions) options).addArguments("--headless");
            } else if (options instanceof EdgeOptions) {
                ((EdgeOptions) options).addArguments("--headless");
            }
        }
        if (browserConfigFileReader.getIncognito().equalsIgnoreCase("incognito")) {
            if (options instanceof ChromeOptions) {
                ((ChromeOptions) options).addArguments("--incognito");
            } else if (options instanceof FirefoxOptions) {
                ((FirefoxOptions) options).addArguments("--incognito");
            } else if (options instanceof EdgeOptions) {
                ((EdgeOptions) options).addArguments("--incognito");
            }
        }
    }

    public static HelperClass getInstance() {
        if (instance == null) {
            synchronized (HelperClass.class) {
                if (instance == null) {
                    instance = new HelperClass();
                }
            }
        }
        if (tlDriver.get() == null) {
            instance.initDriver();
        }
        return instance;
    }

    public WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void closeDriver() {
        WebDriver driver = tlDriver.get();
        if (driver != null) {
            driver.quit();
            tlDriver.remove();
            LOGGER.info("Driver closed and removed from thread local");
        }
    }

    public static String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}
