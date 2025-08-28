package com.example.backend.ui;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }



    @Test
    void login_withValidCredentials_showsTaskTracker() {
        driver.get("http://localhost:5173/login");

        driver.findElement(By.id("email")).sendKeys("test@example.com");
        driver.findElement(By.id("password")).sendKeys("strongpass");
        driver.findElement(By.id("login-btn")).click();

        // Wait for UI update
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        // Check for welcome message
        assertThat(driver.findElement(By.cssSelector(".user-bar span")).getText())
            .contains("Welcome, test@example.com");
    }
}
