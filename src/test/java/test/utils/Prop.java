package test.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import lombok.NonNull;

public class Prop {
    @NonNull
    public static Properties loadPropertiesFromFile(String path) {
        Properties prop = new Properties();
        path = System.getProperty("user.dir")+"/src/test/resources/"+path;
        try (FileInputStream fis = new FileInputStream(path)) {
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties from file: " + path, e);
        }
        return prop;
    }

    @NonNull
    public static String get(String path, String key) {
        Properties prop = loadPropertiesFromFile(path);
        return prop.getProperty(key);
    }
}