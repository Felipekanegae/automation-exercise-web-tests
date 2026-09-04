package AutomationExercise.Contact;

import testData.ExcelTestData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ContactPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private ExcelTestData testData;

    public ContactPage(WebDriver driver, ExcelTestData testData) {

        this.driver = driver;
        this.testData = testData;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);

    }

    //===ELEMENTS===

    @FindBy(name = "name")
    private WebElement contactNameField;

    @FindBy(name = "email")
    private WebElement contactEmailField;

    @FindBy(name = "subject")
    private WebElement contactSubjectField;

    @FindBy(name = "message")
    private WebElement contactMessageField;

    @FindBy(xpath = "//*[text()=\" Contact us\"]")
    private WebElement contactUsButton;

    @FindBy(name = "submit")
    private WebElement submitButton;

    @FindBy(className = "alert-success")
    private WebElement successMessage;

    //===ACTIONS===
    public void openContactUsPage(){
        wait.until(ExpectedConditions.visibilityOf(contactUsButton));
        contactUsButton.click();

    }

    public void sendContactMessage(){
        fillContactForm();
        submitButton.click();
        
    }

    //===FORM FILLING===

    private void fillContactForm(){
        wait.until(ExpectedConditions.visibilityOf(contactEmailField));

        String name = testData.getStringOf("NAME");
        String email = testData.getStringOf("EMAIL");
        String subject = testData.getStringOf("SUBJECT");
        String message = testData.getStringOf("MESSAGE");

        System.out.println("[TEST] NAME: " + name);
        System.out.println("[TEST] EMAIL: " + email);
        System.out.println("[TEST] SUBJECT: " + subject);
        System.out.println("[TEST] MESSAGE: " + message);

        contactNameField.sendKeys(name);
        contactEmailField.sendKeys(email);
        contactSubjectField.sendKeys(subject);
        contactMessageField.sendKeys(message);
        
    }

    //===MESSAGES===

    public void acceptAlertMessage(){
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void validateSuccessMessage(){
        String message = successMessage.getText();

        Assert.assertTrue(message.contains("Success! Your details have been submitted successfully."));
        System.out.println("[TEST] " + message);
    }

}