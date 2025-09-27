package com.example.backend.ui;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.github.bonigarcia.wdm.WebDriverManager;
import com.example.backend.service.UserService;
import com.example.backend.service.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class AddTaskUITest {
@SpringBootTest

    @Autowired
    private UserService userService;

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        // Only create test user in CI environment
        if (System.getenv("GITHUB_ACTIONS") != null) {
            try {
                userService.signup(new SignupRequest("test@example.com", "password123"));
            } catch (Exception ignored) {}
        }
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

    // Login first
    driver.findElement(By.id("email")).sendKeys("test@example.com");
    driver.findElement(By.id("password")).sendKeys("password123");
    driver.findElement(By.id("login-btn")).click();

    // Wait for task title input to appear
    WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("task-title")));

    // Add task
    driver.findElement(By.id("task-title")).sendKeys("Buy milk");
    driver.findElement(By.id("add-task-btn")).click();

    // Wait for task to appear in list
    wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Buy milk"));

    // Verify task appears
    String bodyText = driver.findElement(By.tagName("body")).getText();
    assertThat(bodyText).contains("Buy milk");
    }
}
