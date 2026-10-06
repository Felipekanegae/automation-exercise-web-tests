package AutomationExercise.Products;

import org.openqa.selenium.*;
import testData.ExcelTestData;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

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

    @FindBy(css = "a.check_out")
    private WebElement checkoutButton;

    @FindBy(css = ".cart_quantity_delete")
    private List<WebElement> deleteProductButtons;

    @FindBy(id = "button-review")
    private WebElement submitReviewButton;

    @FindBy(xpath = "//*[contains(normalize-space(.), 'Continue Shopping')]")
    private WebElement continueShoppingButton;

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

    @FindBy(id = "address_delivery")
    private WebElement deliveryAddressBox;

    @FindBy(id = "address_invoice")
    private WebElement billingAddressBox;

    //===ACTIONS===

    public void openProductsPage() {

        wait.until(ExpectedConditions.visibilityOf(productsButton));
        productsButton.click();
    }

    public void closeAdIfPresent() {

        driver.switchTo().defaultContent();

        List<WebElement> ads = driver.findElements(
                By.xpath("//iframe[contains(@id, 'aswift_') " +
                        "or contains(@name, 'aswift_') " +
                        "or @title='3rd party ad content' " +
                        "or @title='Advertisement']"));

        for (WebElement ad : ads) {

            try {
                driver.switchTo().defaultContent();
                driver.switchTo().frame(ad);

                List<WebElement> closeButtons =
                        driver.findElements(By.id("dismiss-button-element"));

                if (!closeButtons.isEmpty()) {

                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", closeButtons.get(0));

                    driver.switchTo().defaultContent();
                    return;
                }

            } catch (Exception e) {
                driver.switchTo().defaultContent();
            }
        }

        driver.switchTo().defaultContent();
    }

    public void viewProductDetails() {

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

        try {
            searchButton.click();
        } catch (ElementClickInterceptedException e) {
            inspectBlockingModal();
            throw e;
        }
    }

    private void addProductToCart() {

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

    public void writeReview() {
        wait.until(ExpectedConditions.visibilityOf(reviewField));
        fillReview();
        submitReviewButton.click();
    }

    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        cartButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        checkoutButton.click();
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
        reviewField.sendKeys(testData.getStringOf("MESSAGE"));
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

        Assert.assertTrue(productName.contains(testData.getStringOf("PRODUCT_1")));
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

    public void validateThankYouForYourReviewMessage() {
        wait.until(ExpectedConditions.visibilityOf(thankYouForYourReviewMessage));

        String message = thankYouForYourReviewMessage.getText();
        Assert.assertTrue(message.contains("Thank you for your review."));

        System.out.println("[TEST] " + message);
    }

    public void validateDeliveryAddress() {
        validateAddress(deliveryAddressBox);

    }

    public void validateBillingAddress() {
        validateAddress(billingAddressBox);

    }

    private void validateAddress(WebElement addressBox) {
        wait.until(ExpectedConditions.visibilityOf(addressBox));

        String address = addressBox.getText();

        String expectedFirstName = testData.getStringOf("FIRST_NAME");
        String expectedLastName = testData.getStringOf("LAST_NAME");
        String expectedCompany = testData.getStringOf("COMPANY");
        String expectedAddress = testData.getStringOf("ADDRESS");
        String expectedCity = testData.getStringOf("CITY");
        String expectedPostalCode = testData.getStringOf("ZIPCODE");
        String expectedCountry = testData.getStringOf("COUNTRY");
        String expectedPhone = testData.getStringOf("MOBILE_NUMBER");

        Assert.assertTrue(address.contains(expectedFirstName),
                "First name not found: " + expectedFirstName);

        Assert.assertTrue(address.contains(expectedLastName),
                "Last name not found: " + expectedLastName);

        Assert.assertTrue(address.contains(expectedCompany),
                "Company not found: " + expectedCompany);

        Assert.assertTrue(address.contains(expectedAddress),
                "Address not found: " + expectedAddress);

        Assert.assertTrue(address.contains(expectedCity),
                "City not found: " + expectedCity);

        Assert.assertTrue(address.contains(expectedPostalCode),
                "Zip Code not found: " + expectedPostalCode);

        Assert.assertTrue(address.contains(expectedCountry),
                "Country not found: " + expectedCountry);

        Assert.assertTrue(address.contains(expectedPhone),
                "Phone number not found: " + expectedPhone);

    }

    private void inspectBlockingModal() {

        List<WebElement> modals = driver.findElements(By.cssSelector(".modal-content"));

        System.out.println("[MODAL] Quantidade de .modal-content: " + modals.size());

        for (WebElement modal : modals) {

            if (modal.isDisplayed()) {

                JavascriptExecutor js = (JavascriptExecutor) driver;

                String info = (String) js.executeScript(
                        "let el = arguments[0];" +
                                "let result = '';" +
                                "let level = 0;" +
                                "while (el && level < 4) {" +
                                "   result += '\\n[NIVEL ' + level + '] ' + el.outerHTML;" +
                                "   el = el.parentElement;" +
                                "   level++;" +
                                "}" +
                                "return result;",
                        modal
                );

                System.out.println("[MODAL] VISÍVEL");
                System.out.println(info);
            }
        }
    }

}

