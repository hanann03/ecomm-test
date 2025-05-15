package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;
    
    @FindBy(xpath = "//button[contains(@id,'remove')]")
    private List<WebElement> removeButtons;
    
    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;
    
    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public void removeItem(int index) {
        removeButtons.get(index).click();
    }

    public void continueShopping() {
        continueShoppingButton.click();
    }

    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        checkoutButton.click();
    }
}