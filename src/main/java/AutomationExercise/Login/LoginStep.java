package AutomationExercise.Login;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;
import testData.ExcelTestData;


public class LoginStep {


    private LoginPage login;
    private WebDriver driver;
    private ExcelTestData testData;

    @Before
    public void beforeScenario(Scenario scenario) {

        String ct = scenario.getSourceTagNames()
                .stream()
                .filter(tag -> tag.startsWith("@CT"))
                .findFirst()
                .orElse("@CT1")
                .replace("@CT", "");

        WebDriverManager.chromedriver().setup();

        System.out.println("[TEST] Tags do cenário: " + scenario.getSourceTagNames());
        System.out.println("[TEST] CT usado: " + ct);

        ChromeOptions options = new ChromeOptions();

        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        testData = new ExcelTestData();

        testData.loadTestData(
                "src/test/resources/massa/Massa/automationExercise.xlsx",
                "automationExercise", ct);

        driver.get("https://automationexercise.com");

        login = new LoginPage(driver, testData);
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        login.openLoginPage();

    }

    @When("I enter a valid email and password")
    public void i_enter_a_valid_email_and_password() {
        login.login();

    }

    @Then("the user should be logged in successfully")
    public void the_user_should_be_logged_in_successfully() {
        login.validateSuccessfulLogin();

    }

    @Given("I am logged in")
    public void i_am_logged_in() {
        login.openLoginPage();
        login.login();
        login.validateSuccessfulLogin();
    }

    @When("I log out")
    public void i_log_out() {
        login.logout();

    }

    @Then("I should be logged out successfully")
    public void i_should_be_logged_out_successfully() {
        login.validateSuccessfulLogout();

    }

    @When("I enter invalid credentials")
    public void i_enter_invalid_credentials() {
        login.login();

    }

    @Then("an authentication error message should be displayed")
    public void an_authentication_error_message_should_be_displayed() {
        login.validateInvalidCredentialsMessage();

    }

    @Given("I am on the registration page")
    public void i_am_on_the_registration_page() {
        login.openLoginPage();

    }

    @When("I enter an email that is already registered")
    public void i_enter_an_email_that_is_already_registered() {
        login.registerNewUser();

    }

    @Then("an error message should be displayed")
    public void an_error_message_should_be_displayed() {
        login.validateEmailAlreadyRegisteredMessage();

    }


    @After
    public void afterScenario() {
        if (driver != null) {
            driver.quit();

        }
    }

}