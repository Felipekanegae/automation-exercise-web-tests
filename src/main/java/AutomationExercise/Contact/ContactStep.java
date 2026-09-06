package AutomationExercise.Contact;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import testData.ExcelTestData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ContactStep {

    private static final Logger log = LoggerFactory.getLogger(ContactStep.class);
    private ContactPage contact;
    private WebDriver driver;
    private ExcelTestData massa;
    private String ct;

    @Before
    public void iniciar(Scenario scenario) {

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

        massa = new ExcelTestData();

        massa.carregarMassa(
                "src/test/resources/massa/Massa/automationExercise.xlsx",
                "automationExercise",
                ct
        );

        driver.get("https://automationexercise.com");

        contact = new ContactPage(driver, massa);
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
    public void finalizar() {
        if (driver != null) {
            driver.quit();

        }
    }

}