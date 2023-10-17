package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void beforeEach() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 5);
    }

    @AfterEach
    void afterEach() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testAddEditDeleteCredential() throws InterruptedException {
        loginOrSignUp();
        openCredentialsTab();
        openCredentialsTab();
        Thread.sleep(1000);
        addCredential("http://example.com", "username123", "password123");
        Thread.sleep(1000);
        openCredentialsTab();
        Thread.sleep(1000);
        editCredential("http://example.com", "username123", "password123", "http://edited.com", "editedUser", "editedPass");
        Thread.sleep(1000);
        openCredentialsTab();
        Thread.sleep(1000);
        deleteCredential();
        Thread.sleep(1000);
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//th[text()='http://edited.com']")));
    }

    private void loginOrSignUp() {
        driver.get("http://localhost:" + port + "/signup");
        if (driver.getTitle().equals("Sign Up")) {
            WebElement firstNameField = driver.findElement(By.id("inputFirstName"));
            WebElement lastNameField = driver.findElement(By.id("inputLastName"));
            WebElement usernameField = driver.findElement(By.id("inputUsername"));
            WebElement passwordField = driver.findElement(By.id("inputPassword"));
            WebElement signUpButton = driver.findElement(By.id("buttonSignUp"));

            firstNameField.sendKeys("John");
            lastNameField.sendKeys("Doe");
            usernameField.sendKeys("yourUsername");
            passwordField.sendKeys("yourPassword");
            signUpButton.click();
        }

        driver.get("http://localhost:" + port + "/login");

        WebElement usernameField = driver.findElement(By.id("inputUsername"));
        WebElement passwordField = driver.findElement(By.id("inputPassword"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("yourUsername");
        passwordField.sendKeys("yourPassword");
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));
    }

    private void addCredential(String url, String username, String password) throws InterruptedException {
        Thread.sleep(1000);
        WebElement addCredentialButton = driver.findElement(By.id("addNewCredential")); // Change the ID to match your HTML
        addCredentialButton.click();

        WebElement credentialUrlField = driver.findElement(By.id("credential-url"));
        WebElement credentialUsernameField = driver.findElement(By.id("credential-username"));
        WebElement credentialPasswordField = driver.findElement(By.id("credential-password"));
        WebElement credentialSubmitButton = driver.findElement(By.id("credentialSubmit"));

        credentialUrlField.sendKeys(url);
        credentialUsernameField.sendKeys(username);
        credentialPasswordField.sendKeys(password);
        Thread.sleep(1000);
        credentialSubmitButton.submit();

    }

    private void editCredential(String originalUrl, String originalUsername, String originalPassword, String newUrl, String newUsername, String newPassword) throws InterruptedException {

        WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        credentialsTab.click();

        Thread.sleep(1000);
        WebElement editCredentialButton = driver.findElement(By.id("editCredentialBtn")); // Change the ID to match your HTML
        editCredentialButton.click();

        WebElement credentialUrlField = driver.findElement(By.id("credential-url"));
        WebElement credentialUsernameField = driver.findElement(By.id("credential-username"));
        WebElement credentialPasswordField = driver.findElement(By.id("credential-password"));
        WebElement credentialSubmitButton = driver.findElement(By.id("credentialSubmit"));
        Thread.sleep(1000);
        credentialUrlField.clear();
        credentialUrlField.sendKeys(newUrl);
        credentialUsernameField.clear();
        credentialUsernameField.sendKeys(newUsername);
        credentialPasswordField.clear();
        credentialPasswordField.sendKeys(newPassword);
        Thread.sleep(1000);
        credentialSubmitButton.submit();

    }

    private void deleteCredential() throws InterruptedException {
        WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        credentialsTab.click();
        Thread.sleep(1000);
        WebElement deleteCredentialButton = driver.findElement(By.id("deleteCredentialButton")); // Change the ID to match your HTML
        deleteCredentialButton.click();
    }

    private void openCredentialsTab() {
        driver.get("http://localhost:" + port + "/home");
        WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        credentialsTab.click();
        webDriverWait.until(ExpectedConditions.attributeContains(credentialsTab, "class", "active"));
    }
}

