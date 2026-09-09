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

    @Given("I am on the product details page")
    public void i_am_on_the_product_details_page() {
        product.openProductsPage();
        product.viewProductDetails();

    }

    @When("I submit a product review")
    public void i_submit_a_product_review() {
        product.writeReview();

    }

    @Then("a review confirmation message should be displayed")
    public void a_review_confirmation_message_should_be_displayed() {
        product.validateThankYouForYourReviewMessage();

    }

    @When("I proceed to checkout")
    public void i_proceed_to_checkout() {
        product.proceedToCheckout();

    }

    @Then("the delivery address should be displayed correctly")
    public void the_delivery_address_should_be_displayed_correctly() {
        product.validateDeliveryAddress();

    }

    @Then("the billing address should be displayed correctly")
    public void the_billing_address_should_be_displayed_correctly() {
        product.validateBillingAddress();

    }

    @After
    public void afterScenario() {
        if (driver != null) {
            driver.quit();

        }
    }

}