package AutomationExercise.Login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import testData.ExcelTestData;


import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private ExcelTestData testData;

    public LoginPage(WebDriver driver, ExcelTestData testData) {

        this.driver = driver;
        this.testData = testData;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);

    }

    // ===ELEMENTS===

    @FindBy(xpath = "//*[normalize-space(text())='Signup / Login']")
    private WebElement signupLoginButton;

    @FindBy(css = "[data-qa='login-email']")
    private WebElement loginEmailField;

    @FindBy(css = "[data-qa='login-password']")
    private WebElement loginPasswordField;

    @FindBy(css = "[data-qa='signup-name']")
    private WebElement signupNameField;

    @FindBy(css = "[data-qa='signup-email']")
    private WebElement signupEmailField;

    @FindBy(css = "[data-qa='login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//*[normalize-space(text())='Logout']")
    private WebElement logoutButton;

    @FindBy(css = "[data-qa='signup-button']")
    private WebElement signupButton;

    @FindBy(xpath = "//*[contains(normalize-space(.), 'Logged in as')]")
    private WebElement messageLogged;

    @FindBy(xpath = "//*[text()=\"Your email or password is incorrect!\"]")
    private WebElement invalidCredentialsMessage;

    @FindBy(xpath = "//*[text()=\"Email Address already exist!\"]")
    private WebElement emailAlreadyRegisteredMessage;


    //===ACTIONS===

    public void openLoginPage() {
        wait.until(ExpectedConditions.visibilityOf(signupLoginButton));
        signupLoginButton.click();

    }

    public void login() {
        fillLoginForm();
        loginButton.click();

    }

    public void logout(){
        wait.until(ExpectedConditions.visibilityOf(logoutButton));
        logoutButton.click();

    }

    public void registerNewUser() {
        fillNewUserForm();
        signupButton.click();

    }

    //===FORM FILLING===
    private void fillLoginForm() {
        wait.until(ExpectedConditions.visibilityOf(loginEmailField));

        String email = testData.getStringOf("EMAIL");
        String password = testData.getStringOf("PASSWORD");
        System.out.println("[TEST] EMAIL: " + email);

        loginEmailField.sendKeys(email);
        loginPasswordField.sendKeys(password);

    }

    private void fillNewUserForm() {
        wait.until(ExpectedConditions.visibilityOf(signupNameField));

        String email = testData.getStringOf("EMAIL");
        String name = testData.getStringOf("NAME");
        System.out.println("[TEST] EMAIL: " + email);
        System.out.println("[TEST] NAME: " + name);

        signupNameField.sendKeys(name);
        signupEmailField.sendKeys(email);

    }


    //===VALIDATIONS===

    public void validateSuccessfulLogin(){
        wait.until(ExpectedConditions.visibilityOf(messageLogged));
        wait.until(ExpectedConditions.visibilityOf(logoutButton));

        System.out.println("[TEST] SUCCESSFUL LOGIN!!!");

    }

    public void validateSuccessfulLogout(){
        wait.until(ExpectedConditions.visibilityOf(loginEmailField));
        wait.until(ExpectedConditions.visibilityOf(loginPasswordField));

        System.out.println("[TEST] SUCCESSFUL LOGOUT!!!");
    }

    public void validateInvalidCredentialsMessage(){
        wait.until(ExpectedConditions.visibilityOf(invalidCredentialsMessage));
        String msg = invalidCredentialsMessage.getText();

        Assert.assertTrue(msg.contains("Your email or password is incorrect!"));
        System.out.println("[TEST] " + msg);

    }

    public void validateEmailAlreadyRegisteredMessage(){
        wait.until(ExpectedConditions.visibilityOf(emailAlreadyRegisteredMessage));
        String msg = emailAlreadyRegisteredMessage.getText();

        Assert.assertTrue(msg.contains("Email Address already exist!"));
        System.out.println("[TEST] " + msg);

    }

}