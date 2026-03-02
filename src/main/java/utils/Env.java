package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Env {
    private static final Properties props = new Properties();
    private static final String PROP_FILE_PATH = "src/test/resources/login.properties";

    static {
        try (FileInputStream fis = new FileInputStream(PROP_FILE_PATH)) {
            props.load(fis);
            System.out.println("Loaded local config from: " + PROP_FILE_PATH);
        } catch (IOException e) {
            System.out.println("Local properties file not found. Using System/Env variables.");
        }
    }

    public static String get(String propKey, String envName) {
        String value = System.getProperty(propKey);
        if (isNullOrEmpty(value)) {
            value = props.getProperty(propKey);
        }
        if (isNullOrEmpty(value)) {
            value = System.getenv(envName);
        }
        if (isNullOrEmpty(value)) {
            throw new RuntimeException(String.format(
                "CRITICAL CONFIG ERROR: Could not find value for key '%s' in test.properties " +
                "OR Environment Variable '%s'. Please check your local setup or GitHub Secrets!", 
                propKey, envName));
        }

        return value.trim();
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}