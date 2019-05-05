package de.hfu.pms.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    private static final String FILE_NAME = "conf/config.properties";

    private static Properties config = new Properties();

    static {
        // load properties file
        try {
            InputStream inputStream = AppConfig.class.getClassLoader().getResourceAsStream(FILE_NAME);
            if (inputStream == null) {
                throw new IOException("Failed to load file: " + FILE_NAME);
            }
            config.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return config.getProperty(key);
    }

}
