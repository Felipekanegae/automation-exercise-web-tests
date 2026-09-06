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

    @FindBy(xpath = "//a[contains(@href, '/Close/')]")
    private WebElement dismissButton;

    @FindBy(xpath = "//a[contains(@href, '/product_details/')]")
    private List<WebElement> viewProductButtons;


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

    public void viewProductsDetails() {
        closeAdIfPresent();
        wait.until(ExpectedConditions.visibilityOfAllElements(viewProductButtons));
        WebElement product = viewProductButtons.get(0);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", product);

        wait.until(ExpectedConditions.elementToBeClickable(product));
        product.click();
    }

    //===FORM FILLING===


    //===MESSAGES===


}