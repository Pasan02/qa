package com.example.backend.ui;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AddTaskUITest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        
        // Add arguments for CI environment
        String chromeArgs = System.getProperty("webdriver.chrome.args");
        if (chromeArgs != null) {
            for (String arg : chromeArgs.split(",")) {
                options.addArguments(arg);
            }
        }
        
        // Default arguments for stability
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");
        
        driver = new ChromeDriver(options);
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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailInput.sendKeys("test@example.com");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.id("login-btn")).click();

        // Wait for task input to appear
        WebElement taskTitleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("task-title")));
        taskTitleInput.sendKeys("Buy milk");
        driver.findElement(By.id("add-task-btn")).click();

        // Wait for task to appear in body
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Buy milk"));
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertThat(bodyText).contains("Buy milk");
    }
}
