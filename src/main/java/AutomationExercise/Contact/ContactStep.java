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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v154.network.Network;

import java.util.Arrays;
import java.util.Optional;

import java.util.HashMap;
import java.util.Map;


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

        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.ads", 2);

        options.setExperimentalOption("prefs", prefs);

        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();

        devTools.send(
                Network.enable(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()));

        devTools.send(
                Network.setBlockedURLs(
                        Optional.empty(),
                        Optional.of(Arrays.asList(
                                "*googlesyndication.com*",
                                "*doubleclick.net*",
                                "*googleads.g.doubleclick.net*"))));

        devTools.addListener(Network.requestWillBeSent(), request -> {
            String url = request.getRequest().getUrl();

            if (url.contains("google") ||
                    url.contains("doubleclick") ||
                    url.contains("adservice") ||
                    url.contains("ads")) {

                System.out.println("[NETWORK] " + url);
            }
        });

        devTools.addListener(Network.responseReceived(), response -> {
            String url = response.getResponse().getUrl();

            if (url.contains("googlesyndication") ||
                    url.contains("doubleclick") ||
                    url.contains("adservice")) {

                System.out.println("[RESPONSE] " + url);
            }
        });

        driver.manage().window().maximize();

        testData = new ExcelTestData();

        testData.loadTestData(
                "src/test/resources/massa/Massa/automationExercise.xlsx",
                "automationExercise", ct);

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