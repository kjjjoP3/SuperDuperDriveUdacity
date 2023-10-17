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
        Thread.sleep(1000);

        lastNameField.sendKeys(testLastName);
        Thread.sleep(1000);

        userNameField.sendKeys(testUserName);
        Thread.sleep(1000);

        passWordField.sendKeys(testPassWord);
        Thread.sleep(1000);

        submitButton.click();
        Thread.sleep(1000);
    }

    private void login() throws InterruptedException {

        WebElement userNameLoginField = driver.findElement(By.id("inputUsername"));
        WebElement passWordLoginField = driver.findElement(By.id("inputPassword"));
        WebElement submitButtonLogin = driver.findElement(By.id("login-button"));

        Thread.sleep(1000);

        userNameLoginField.sendKeys(testUserName);
        Thread.sleep(1000);

        passWordLoginField.sendKeys(testPassWord);
        Thread.sleep(1000);

        submitButtonLogin.click();
        Thread.sleep(1000);

    }

    @Test
    @Order(1)
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
        Thread.sleep(1000);
        noteTitle.sendKeys(testNoteTitle);
        Thread.sleep(1000);
        noteDescription.sendKeys(testNoteDescription);
        Thread.sleep(1000);
        noteForm.submit();
        Thread.sleep(1000);

    }

    @Test
    @Order(2)
    public void editNote() throws InterruptedException {
        Assertions.assertDoesNotThrow(() -> {
            driver.findElement(By.xpath("//th[text()='Note title']"));
            driver.findElement(By.xpath("//td[text()='Note Description']"));
        });
        WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
        WebElement editBtn = driver.findElement(By.id("editNoteBtn"));
        WebElement noteTitle = driver.findElement(By.id("note-title"));
        WebElement noteDescription = driver.findElement(By.id("note-description"));
        WebElement noteForm = driver.findElement(By.id("noteSubmit"));

        Thread.sleep(1000);
        noteTab.click();
        Thread.sleep(1000);
        editBtn.click();
        Thread.sleep(1000);
        noteTitle.clear();
        Thread.sleep(1000);
        noteTitle.sendKeys(testNoteTitleEdit);
        Thread.sleep(1000);
        noteDescription.sendKeys(testNoteDescriptionEdit);
        Thread.sleep(1000);
        noteForm.submit();
        Thread.sleep(1000);
        noteTab.click();
        Thread.sleep(1000);
    }

    @Test
    @Order(3)
    public void deleteAndConfirmNote() throws InterruptedException {

        Assertions.assertDoesNotThrow(() -> {
            driver.findElement(By.xpath("//th[text()='Edit Note title']"));
            driver.findElement(By.xpath("//td[text()='Edit Note Description']"));
        });

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//th[text()='Edit Note title']")));
        WebElement noteTitle = driver.findElement(By.xpath("//th[text()='Edit Note title']"));
        Assertions.assertEquals("Edit Note title", noteTitle.getText());
        WebElement noteDescription = driver.findElement(By.xpath("//td[text()='Edit Note Description']"));
        Assertions.assertEquals("Edit Note Description", noteDescription.getText());

        WebElement deleteBtn = driver.findElement(By.xpath("//*[@id='noteTable']/tbody/tr/td[1]/a"));
        Assertions.assertEquals("Delete", deleteBtn.getText());
        deleteBtn.click();

    }
}
