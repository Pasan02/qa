package com.example.backend.ui;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AddTaskUITest {

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
    void addTask_displaysInList() {
        driver.get("http://localhost:5173/login");

        // Login first
        driver.findElement(By.id("email")).sendKeys("test@example.com");
        driver.findElement(By.id("password")).sendKeys("strongpass");
        driver.findElement(By.id("login-btn")).click();

        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        // Add task
        driver.findElement(By.id("task-title")).sendKeys("Buy milk");
        driver.findElement(By.id("add-task-btn")).click();

        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

        // Verify task appears
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertThat(bodyText).contains("Buy milk");
    }
}
