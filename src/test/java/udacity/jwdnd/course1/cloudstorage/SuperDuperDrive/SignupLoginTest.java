package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupLoginTest {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 2);
    }

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testSignupAndLogin() throws InterruptedException {
        //get localhost signup
        String baseUrl = "http://localhost:" + port;
        driver.get(baseUrl + "/signup");

        //check tiltle
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
        Assertions.assertEquals("Sign Up", driver.getTitle());

        Thread.sleep(2000);

        WebElement firstNameField = driver.findElement(By.id("inputFirstName"));
        WebElement lastNameField = driver.findElement(By.id("inputLastName"));
        WebElement userNameField = driver.findElement(By.id("inputUsername"));
        WebElement passWordField = driver.findElement(By.id("inputPassword"));
        WebElement submitButton = driver.findElement(By.id("buttonSignUp"));

        String testFirstName = "Thanh";
        String testLastName = "Bui";
        String testUserName = "ThanhBT12";
        String testPassWord = "123456";

        Thread.sleep(1000);
        firstNameField.sendKeys(testFirstName);
        Thread.sleep(1000);

        lastNameField.sendKeys(testLastName);
        Thread.sleep(1000);

        userNameField.sendKeys(testUserName);
        Thread.sleep(2000);

        passWordField.sendKeys(testPassWord);
        Thread.sleep(2000);

        submitButton.click();
        Thread.sleep(2000);


        //check tiltel login
        webDriverWait.until(ExpectedConditions.titleContains("Login"));
        Assertions.assertEquals("Login", driver.getTitle());

        WebElement userNameLoginField = driver.findElement(By.id("inputUsername"));
        WebElement passWordLoginField = driver.findElement(By.id("inputPassword"));
        WebElement submitButtonLogin = driver.findElement(By.id("login-button"));

        Thread.sleep(1000);

        userNameLoginField.sendKeys(testUserName);
        Thread.sleep(1000);

        passWordLoginField.sendKeys(testPassWord);
        Thread.sleep(1000);

        submitButtonLogin.click();
        Thread.sleep(2000);

        //check home
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", driver.getTitle());

        Thread.sleep(2000);

        //logout
        WebElement logoutBtn = driver.findElement(By.id("btn-logout"));
        logoutBtn.click();

        Thread.sleep(2000);

        //check login affter logout
        webDriverWait.until(ExpectedConditions.titleContains("Login"));
        Assertions.assertEquals("Login", driver.getTitle());

        Thread.sleep(2000);

        //get localhost home
        String baseHomeUrl = "http://localhost:" + port;
        driver.get(baseUrl + "/home");

        Thread.sleep(2000);

        //check login
        Assertions.assertEquals("Login", driver.getTitle());

        
    }
}

