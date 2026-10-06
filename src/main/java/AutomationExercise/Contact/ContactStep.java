package AutomationExercise.Contact;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import testData.ExcelTestData;


public class ContactStep {

    private ContactPage contact;
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

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        testData = new ExcelTestData();

        testData.loadTestData(
                "src/test/resources/massa/Massa/automationExercise.xlsx",
                "automationExercise",
                ct
        );

        driver.get("https://automationexercise.com");

        contact = new ContactPage(driver, testData);
    }

    @Given("I am on the Contact Us page")
    public void i_am_on_the_contact_us_page() {
        contact.openContactUsPage();
    }

    @When("I fill in the contact form")
    public void i_fill_in_the_contact_form() {
        contact.sendContactMessage();
        contact.acceptAlertMessage();
    }

    @Then("the message is sent successfully")
    public void the_message_is_sent_successfully() {
        contact.validateSuccessMessage();

    }

    @After
    public void afterScenario() {
        if (driver != null) {
            driver.quit();

        }
    }

}