package org.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EnvConfigFileReader {

    private Properties properties;
    private static final Logger LOGGER = LogManager.getLogger(EnvConfigFileReader.class);
    private static final String CONFIG_FILE_PATH = "src/test/resources/env_config.properties";

    public EnvConfigFileReader() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE_PATH))) {
            properties = new Properties();
            properties.load(reader);
            LOGGER.info("Environment configuration loaded successfully from {}", CONFIG_FILE_PATH);
        } catch (IOException e) {
            LOGGER.error("Failed to load environment configuration from {}", CONFIG_FILE_PATH, e);
            throw new RuntimeException("Failed to load environment configuration", e);
        }
    }

    public String getContactVersionToBeUsed() {
        return properties.getProperty("contact_version_to_be_used");
    }

    public String getParaVersionToBeUsed() {
        return properties.getProperty("para_version_to_be_used");
    }

    public String getContactUsername() {
        return properties.getProperty("contact_username");
    }

    public String getContactPassword() {
        return properties.getProperty("contact_password");
    }

    public String getContactUrl() {
        return properties.getProperty("contact_url");
    }

    public String getParaUsername() {
        return properties.getProperty("para_username");
    }

    public String getParaPassword() {
        return properties.getProperty("para_password");
    }

    public String getParaUrl() {
        return properties.getProperty("para_url");
    }

    public String getReportingType() {
        return properties.getProperty("reportingType");
    }

    public String getEnv() {
        return properties.getProperty("env");
    }

    public String getContactExcludeTest() {
        return properties.getProperty("contact_excludeTest");
    }

    public String getParaExcludeTest() {
        return properties.getProperty("para_excludeTest");
    }

}
