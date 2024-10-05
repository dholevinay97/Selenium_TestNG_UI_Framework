package org.automation.utilities;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ScenarioContext {
    // Thread-safe map to store scenario context data
    private Map<String, Object> scenarioContext;

    // Private constructor to prevent instantiation
    private ScenarioContext() {
        scenarioContext = new ConcurrentHashMap<>();
    }

    // Nested static class responsible for holding the singleton instance
    private static class ScenarioContextHolder {
        private static final ScenarioContext INSTANCE = new ScenarioContext();
    }

    // Method to get the singleton instance
    public static ScenarioContext getInstance() {
        return ScenarioContextHolder.INSTANCE;
    }

    // Method to set a context value
    public void setContext(String key, Object value) {
        scenarioContext.put(key, value);
    }

    // Method to get a context value
    public Object getContext(String key) {
        return scenarioContext.get(key);
    }

    // Method to check if a context key is present
    public boolean isContains(String key) {
        return scenarioContext.containsKey(key);
    }
}
