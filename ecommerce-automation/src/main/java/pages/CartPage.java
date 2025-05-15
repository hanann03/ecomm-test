package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class CartPage {
    private WebDriver driver;

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
        checkoutButton.click();
    }
}