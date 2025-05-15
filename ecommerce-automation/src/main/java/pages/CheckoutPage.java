package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "first-name")
    private WebElement firstNameField;
    
    @FindBy(id = "last-name")
    private WebElement lastNameField;
    
    @FindBy(id = "postal-code")
    private WebElement zipCodeField;
    
    @FindBy(id = "continue")
    private WebElement continueButton;
    
    @FindBy(id = "finish")
    private WebElement finishButton;
    
    @FindBy(className = "complete-header")
    private WebElement confirmationMessage;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void enterShippingInfo(String firstName, String lastName, String zipCode) {
        // Wait for page to be fully loaded
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
            .executeScript("return document.readyState").equals("complete"));
        
        // Wait for the checkout form to be present and visible
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("first-name")));
        wait.until(ExpectedConditions.visibilityOf(firstNameField));
        
        // Try to scroll the element into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstNameField);
        
        // Small delay to ensure the page is stable
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Clear and send keys
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
        zipCodeField.clear();
        zipCodeField.sendKeys(zipCode);
    }

    public void continueToOverview() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();
        
        // Take a screenshot for debugging
        captureScreenshot("after_continue.png");
        
        // Wait for the overview page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));
    }

    private void captureScreenshot(String fileName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void completePurchase() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        finishButton.click();
    }

    public String getConfirmationMessage() {
        wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
        return confirmationMessage.getText();
    }
}