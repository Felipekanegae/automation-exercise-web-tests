package AutomationExercise.Products;

import org.openqa.selenium.*;
import testData.ExcelTestData;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class ProductsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private ExcelTestData testData;

    public ProductsPage(WebDriver driver, ExcelTestData testData) {

        this.driver = driver;
        this.testData = testData;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);
    }

    //===ELEMENTS===

    @FindBy(xpath = "//*[normalize-space(text())='Products']")
    private WebElement productsButton;

    @FindBy(xpath = "//a[contains(@href, '/product_details/')]")
    private List<WebElement> viewProductButtons;

    @FindBy(css = ".cart_description h4 a")
    private List<WebElement> cartProductNames;

    @FindBy(css = "a[href='/view_cart']")
    private WebElement cartButton;

    // Add to cart buttons from product cards
    @FindBy(css = ".productinfo .add-to-cart")
    private List<WebElement> addToCartButtons;

    // Add to cart button from product details page
    @FindBy(xpath = "//*[contains(normalize-space(.), 'Add to cart')]")
    private WebElement productDetailsAddToCartButton;

    @FindBy(id = "submit_search")
    private WebElement searchButton;

    @FindBy(xpath = "//*[contains(normalize-space(.), 'Proceed To Checkout')]")
    private WebElement checkoutButton;

    @FindBy(css = ".cart_quantity_delete")
    private List<WebElement> deleteProductButtons;

    @FindBy(id = "button-review")
    private WebElement submitReviewButton;

    @FindBy(className = "product-information")
    private WebElement productInformation;

    @FindBy(id = "quantity")
    private WebElement quantityField;

    @FindBy(id = "review")
    private WebElement reviewField;

    @FindBy(id = "search_product")
    private WebElement searchProductField;

    @FindBy(id = "name")
    private WebElement nameReviewField;

    @FindBy(id = "email")
    private WebElement emailReviewField;

    @FindBy(id = "review")
    private WebElement textReviewField;

    @FindBy(xpath = "//*[normalize-space(text())='Quantity:']")
    private WebElement quantityLabel;

    @FindBy(xpath = "//*[normalize-space(text())='Availability:']")
    private WebElement availabilityLabel;

    @FindBy(xpath = "//*[normalize-space(text())='Condition:']")
    private WebElement conditionLabel;

    @FindBy(xpath = "//*[normalize-space(text())='Brand:']")
    private WebElement brandLabel;

    @FindBy(css = ".productinfo.text-center p")
    private WebElement searchedProductName;

    @FindBy(xpath = "//*[text()=\"Thank you for your review.\"]")
    private WebElement thankYouForYourReviewMessage;

    //===ACTIONS===

    public void openProductsPage() {

        wait.until(ExpectedConditions.visibilityOf(productsButton));
        productsButton.click();
    }

    public void closeAdIfPresent() {
        try {
            List<WebElement> adFrames =
                    driver.findElements(By.cssSelector("iframe[title='Advertisement']"));

            for (WebElement frame : adFrames) {

                driver.switchTo().defaultContent();
                driver.switchTo().frame(frame);

                List<WebElement> closeButtons =
                        driver.findElements(By.id("dismiss-button"));

                if (!closeButtons.isEmpty()) {
                    closeButtons.get(0).click();
                    return;
                }
            }

        } finally {
            driver.switchTo().defaultContent();

        }
    }

    public void viewProductDetails() {
        closeAdIfPresent();
        wait.until(ExpectedConditions.visibilityOfAllElements(viewProductButtons));

        WebElement product = viewProductButtons.get(0);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                product);

        wait.until(ExpectedConditions.elementToBeClickable(product));

        product.click();
    }

    public void searchProduct(String productName) {

        fillProductName(productName);

        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    public void addProductToCart() {
        closeAdIfPresent();
        wait.until(ExpectedConditions.visibilityOfAllElements(addToCartButtons));

        WebElement addToCartButton = addToCartButtons.get(0);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", addToCartButton);

        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addToCartButton.click();
    }

    public void addProductsToCart() {
        searchProduct(testData.getStringOf("PRODUCT_1"));
        addProductToCart();
        searchProduct(testData.getStringOf("PRODUCT_2"));
        addProductToCart();
        searchProduct(testData.getStringOf("PRODUCT_3"));
        addProductToCart();

    }

    public void removeProductFromCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        cartButton.click();

        wait.until(ExpectedConditions.visibilityOfAllElements(cartProductNames));
        wait.until(ExpectedConditions.visibilityOfAllElements(deleteProductButtons));

        String removedProductName = cartProductNames.get(0).getText();

        WebElement removeProductButton = deleteProductButtons.get(0);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                removeProductButton);

        wait.until(ExpectedConditions.elementToBeClickable(removeProductButton));
        removeProductButton.click();

        wait.until(driver ->
                cartProductNames.stream().noneMatch(product ->
                                product.getText().contains(removedProductName)));

    }

    public void writeReview(){
        wait.until(ExpectedConditions.visibilityOf(reviewField));
        fillReview();
        submitReviewButton.click();
    }

    //===FORM FILLING===

    private void fillProductName(String productName) {

        wait.until(ExpectedConditions.visibilityOf(searchProductField));

        searchProductField.clear();
        searchProductField.sendKeys(productName);

    }

    private void fillReview() {
        nameReviewField.sendKeys(testData.getStringOf("NAME"));
        emailReviewField.sendKeys(testData.getStringOf("EMAIL"));
        textReviewField.sendKeys(testData.getStringOf("MESSAGE"));

    }

    //===VALIDATIONS===

    public void verifyProductDetails() {

        wait.until(ExpectedConditions.visibilityOf(reviewField));
        wait.until(ExpectedConditions.visibilityOf(productDetailsAddToCartButton));

        Assert.assertTrue(productInformation.isDisplayed());
        Assert.assertTrue(quantityLabel.isDisplayed());
        Assert.assertTrue(quantityField.isDisplayed());
        Assert.assertTrue(productDetailsAddToCartButton.isDisplayed());
        Assert.assertTrue(availabilityLabel.isDisplayed());
        Assert.assertTrue(conditionLabel.isDisplayed());
        Assert.assertTrue(brandLabel.isDisplayed());
    }

    public void verifySearchedProduct() {
        wait.until(ExpectedConditions.visibilityOf(searchedProductName));

        String productName = searchedProductName.getText();

        Assert.assertTrue(
                productName.contains(testData.getStringOf("PRODUCT_1")));

    }

    public void verifyProductsInCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        cartButton.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(cartProductNames));

        String expectedProduct1 = testData.getStringOf("PRODUCT_1");
        String expectedProduct2 = testData.getStringOf("PRODUCT_2");
        String expectedProduct3 = testData.getStringOf("PRODUCT_3");

        boolean product1Found = false;
        boolean product2Found = false;
        boolean product3Found = false;

        for (WebElement product : cartProductNames) {

            String productName = product.getText();

            if (productName.contains(expectedProduct1)) {
                product1Found = true;
            }

            if (productName.contains(expectedProduct2)) {
                product2Found = true;
            }

            if (productName.contains(expectedProduct3)) {
                product3Found = true;
            }
        }

        Assert.assertTrue(product1Found, "Product not found: " + expectedProduct1);
        Assert.assertTrue(product2Found, "Product not found: " + expectedProduct2);
        Assert.assertTrue(product3Found, "Product not found: " + expectedProduct3);
    }

    public void verifyProductRemovedFromCart() {
        wait.until(ExpectedConditions.visibilityOfAllElements(cartProductNames));

        String expectedProduct1 = testData.getStringOf("PRODUCT_1");
        String expectedProduct2 = testData.getStringOf("PRODUCT_2");
        String expectedProduct3 = testData.getStringOf("PRODUCT_3");

        boolean product1Found = false;
        boolean product2Found = false;
        boolean product3Found = false;

        for (WebElement product : cartProductNames) {

            String productName = product.getText();

            if (productName.contains(expectedProduct1)) {
                product1Found = true;
            }

            if (productName.contains(expectedProduct2)) {
                product2Found = true;
            }

            if (productName.contains(expectedProduct3)) {
                product3Found = true;
            }
        }

        Assert.assertFalse(product1Found, "Product should have been removed: " +
                        expectedProduct1);
        Assert.assertTrue(product2Found, "Product not found: " +
                expectedProduct2);
        Assert.assertTrue(product3Found, "Product not found: " +
                expectedProduct3);

    }

    public void validateThankYouForYourReviewMessage(){
        wait.until(ExpectedConditions.visibilityOf(thankYouForYourReviewMessage));

        String message =  thankYouForYourReviewMessage.getText();
        Assert.assertTrue(message.contains("Thank you for your review."));

        System.out.println("[TEST] " +  message);
    }

}

