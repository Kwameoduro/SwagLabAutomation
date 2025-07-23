package com.swaglabs.base;

import com.swaglabs.utils.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class BaseTest {

    protected WebDriver driver;
    protected Properties config;

    @BeforeEach
    public void setUp() {
        loadConfig();
        String browser = config.getProperty("browser", "chrome");
        boolean headless = Boolean.parseBoolean(config.getProperty("headless", "false"));

        driver = WebDriverFactory.createDriver(browser, headless);
        driver.get(config.getProperty("baseUrl"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void loadConfig() {
        config = new Properties();
        try (FileInputStream input = new FileInputStream("src/test/resources/config.properties")) {
            config.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties file", e);
        }
    }
}
