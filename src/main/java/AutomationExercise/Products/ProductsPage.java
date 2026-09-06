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

    @FindBy(xpath = "//*[contains(normalize-space(.), 'Add to cart')]")
    private WebElement addCartButton;

    @FindBy(className = "product-information")
    private WebElement productInformation;

    @FindBy(id = "quantity")
    private WebElement quantityField;

    @FindBy(id = "review")
    private WebElement reviewField;

    @FindBy(xpath = "//*[normalize-space(text())='Quantity:']")
    private WebElement quantityLabel;

    @FindBy(xpath = "//*[normalize-space(text())='Availability:']")
    private WebElement availabilityLabel;

    @FindBy(xpath = "//*[normalize-space(text())='Condition:']")
    private WebElement conditionLabel;

    @FindBy(xpath = "//*[normalize-space(text())='Brand:']")
    private WebElement brandLabel;

    //===ACTIONS===

    public void openProductsPage() {
        wait.until(ExpectedConditions.visibilityOf(productsButton));
        productsButton.click();

    }

    public void closeAdIfPresent() {

        try {
            List<WebElement> adFrames = driver.findElements(By.cssSelector("iframe[title='Advertisement']"));

            for (WebElement frame : adFrames) {

                driver.switchTo().defaultContent();
                driver.switchTo().frame(frame);

                List<WebElement> closeButtons = driver.findElements(By.id("dismiss-button"));

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

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", product);

        wait.until(ExpectedConditions.elementToBeClickable(product));
        product.click();
    }

    //===VALIDATION===

    public void verifyProductDetails() {
        wait.until(ExpectedConditions.visibilityOf(reviewField));
        String quantity = quantityField.getAttribute("value");

        Assert.assertTrue(productInformation.isDisplayed());
        Assert.assertTrue(quantityLabel.isDisplayed());
        Assert.assertTrue(quantityField.isDisplayed());
        Assert.assertTrue(addCartButton.isDisplayed());
        Assert.assertTrue(availabilityLabel.isDisplayed());
        Assert.assertTrue(conditionLabel.isDisplayed());
        Assert.assertTrue(brandLabel.isDisplayed());

        System.out.println("[TEST] QUANTITY: " +  quantity);

    }

}