package AutomationExercise.Products;

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


public class ProductsStep {

    private static final Logger log = LoggerFactory.getLogger(ProductsStep.class);
    private ProductsPage product;
    private WebDriver driver;
    private ExcelTestData testData;
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

        testData = new ExcelTestData();

        testData.carregarMassa(
                "src/test/resources/massa/Massa/automationExercise.xlsx",
                "automationExercise",
                ct
        );

        driver.get("https://automationexercise.com");

        product = new ProductsPage(driver, testData);
    }

    @Given("I am on the products page")
    public void i_am_on_the_products_page() {
        product.openProductsPage();

    }

    @When("I select a product")
    public void i_select_a_product() {
        product.closeAdIfPresent();
        product.viewProductDetails();

    }

    @Then("the product details should be displayed")
    public void the_product_details_should_be_displayed() {
        product.verifyProductDetails();

    }


    @After
    public void finalizar() {
        if (driver != null) {
            driver.quit();

        }
    }

}