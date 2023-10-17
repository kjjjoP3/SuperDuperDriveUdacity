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
public class NoteTest {

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

    private static String testFirstName = "Thanh";
    private static String testLastName = "Bui";
    private static String testUserName = "ThanhBT12";
    private static String testPassWord = "123456";
    private static String testNoteTitle = "NgayHomNay";
    private static String testNoteDescription = "Note Description";

    private static String testNoteTitleEdit = "NgayHomQua";
    private static String testNoteDescriptionEdit = "Note Description";


    private void signUp() throws InterruptedException {
        //get localhost signup
        String baseUrl = "http://localhost:" + port;
        driver.get(baseUrl + "/signup");
        Thread.sleep(2000);

        WebElement firstNameField = driver.findElement(By.id("inputFirstName"));
        WebElement lastNameField = driver.findElement(By.id("inputLastName"));
        WebElement userNameField = driver.findElement(By.id("inputUsername"));
        WebElement passWordField = driver.findElement(By.id("inputPassword"));
        WebElement submitButton = driver.findElement(By.id("buttonSignUp"));

        Thread.sleep(1000);
        firstNameField.sendKeys(testFirstName);


        lastNameField.sendKeys(testLastName);


        userNameField.sendKeys(testUserName);


        passWordField.sendKeys(testPassWord);


        submitButton.click();
        Thread.sleep(1000);
    }

    private void login() throws InterruptedException {

        WebElement userNameLoginField = driver.findElement(By.id("inputUsername"));
        WebElement passWordLoginField = driver.findElement(By.id("inputPassword"));
        WebElement submitButtonLogin = driver.findElement(By.id("login-button"));



        userNameLoginField.sendKeys(testUserName);


        passWordLoginField.sendKeys(testPassWord);


        submitButtonLogin.click();
        Thread.sleep(1000);

    }

    @Test
    public void addAndDisplayTest() throws InterruptedException {
        //login and signUp
        signUp();
        login();
        //check title Home
        webDriverWait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", driver.getTitle());

        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        WebElement addNoteBtn = driver.findElement(By.id("addNewNote"));
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        WebElement noteForm = driver.findElement(By.id("noteSubmit"));

        noteTab.click();
        Thread.sleep(1000);
        addNoteBtn.click();

        noteTitle.sendKeys(testNoteTitle);
        noteDescription.sendKeys(testNoteDescription);

        noteForm.submit();
        Thread.sleep(1000);

        editNote();
        Thread.sleep(1000);
        deleteAndConfirmNote();

    }


    public void editNote() throws InterruptedException {
        openNotesTab();
        // Wait for the edit button to become clickable
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("editNoteBtn"))).click();

        WebElement noteTitle = driver.findElement(By.id("note-title"));
        WebElement noteDescription = driver.findElement(By.id("note-description"));

        // Clear and edit the note fields
        noteTitle.clear();
        noteTitle.sendKeys(testNoteTitleEdit);
        noteDescription.clear();
        noteDescription.sendKeys(testNoteDescriptionEdit);

        // Submit the form
        driver.findElement(By.id("noteSubmit")).submit();
    }

    public void deleteAndConfirmNote() throws InterruptedException {
        openNotesTab();
        WebElement deleteCredentialButton = driver.findElement(By.id("deleteNoteButton")); // Change the ID to match your HTML
        deleteCredentialButton.click();
    }

    private void openNotesTab() {
        driver.get("http://localhost:" + port + "/home");
        WebElement credentialsTab = driver.findElement(By.id("nav-notes-tab"));
        credentialsTab.click();
        webDriverWait.until(ExpectedConditions.attributeContains(credentialsTab, "class", "active"));
    }
}
