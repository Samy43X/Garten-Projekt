package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class ConfigurationLoader {

    public static Properties loadApplicationProperties() {
        Properties prop = new Properties();
        
        // Pfad zur Konfigurationsdatei
        String propFileName = "src\\main\\resources\\application.properties";

        try (FileInputStream inputStream = new FileInputStream(propFileName)) {
            prop.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return prop;
    }
}
