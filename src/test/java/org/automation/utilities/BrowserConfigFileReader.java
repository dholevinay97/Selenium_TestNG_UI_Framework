package org.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class BrowserConfigFileReader {
    private Properties properties;
    private static final Logger LOGGER = LogManager.getLogger(BrowserConfigFileReader.class);
    private static final String CONFIG_FILE_PATH = "src/test/resources/browser_config.properties";

    public BrowserConfigFileReader() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE_PATH))) {
            properties = new Properties();
            properties.load(reader);
            LOGGER.info("Browser configuration loaded successfully from {}", CONFIG_FILE_PATH);
        } catch (IOException e) {
            LOGGER.error("Failed to load browser configuration from {}", CONFIG_FILE_PATH, e);
            throw new RuntimeException("Failed to load browser configuration", e);
        }
    }

    public String getBrowser() {
        return properties.getProperty("browser");
    }

    public String getHeadless() {
        return properties.getProperty("headless");
    }

    public String getIncognito() {
        return properties.getProperty("incognito");
    }

}
