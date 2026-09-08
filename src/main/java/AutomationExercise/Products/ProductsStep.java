package AutomationExercise.Products;

import AutomationExercise.Login.LoginPage;
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


public class ProductsStep {

    private ProductsPage product;
    private WebDriver driver;
    private ExcelTestData testData;
    private LoginPage login;

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
        login = new LoginPage(driver, testData);

    }

    @Given("I am on the products page")
    public void i_am_on_the_products_page() {
        product.openProductsPage();
        product.closeAdIfPresent();
    }

    @When("I select a product")
    public void i_select_a_product() {
        product.viewProductDetails();

    }

    @Then("the product details should be displayed")
    public void the_product_details_should_be_displayed() {
        product.verifyProductDetails();

    }

    @When("I enter a product name")
    public void i_enter_a_product_name() {
        product.searchProduct(testData.getStringOf("PRODUCT_1"));

    }

    @Then("the product should be displayed")
    public void the_product_should_be_displayed() {
        product.verifySearchedProduct();

    }


    @Then("the products should be displayed in the cart")
    public void the_products_should_be_displayed_in_the_cart() {
        product.verifyProductsInCart();

    }

    @Given("I am logged in")
    public void i_am_logged_in() {
        login.openLoginPage();
        login.login();
        product.openProductsPage();
        product.closeAdIfPresent();
    }

    @When("I add the products to the cart")
    public void i_add_the_products_to_the_cart() {
        product.addProductsToCart();

    }

    @Given("I have products in the cart")
    public void i_have_products_in_the_cart() {
        product.openProductsPage();
        product.closeAdIfPresent();
        product.addProductsToCart();
    }

    @When("I remove a product from the cart")
    public void i_remove_a_product_from_the_cart() {
        product.removeProductFromCart();

    }

    @Then("the product should no longer be displayed in the cart")
    public void the_product_should_no_longer_be_displayed_in_the_cart() {
        product.verifyProductRemovedFromCart();

    }


    @After
    public void finalizar() {
        if (driver != null) {
            driver.quit();

        }
    }

}