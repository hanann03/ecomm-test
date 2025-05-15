package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductPage {
    private WebDriver driver;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;
    
    @FindBy(className = "inventory_item")
    private List<WebElement> productItems;
    
    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;
    
    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;
    
    @FindBy(xpath = "//button[contains(@id,'add-to-cart')]")
    private List<WebElement> addToCartButtons;
    
    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void sortProducts(String sortOption) {
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(sortOption);
    }

    public void addProductToCart(int index) {
        addToCartButtons.get(index).click();
    }

    public String getProductName(int index) {
        return productNames.get(index).getText();
    }

    public String getProductPrice(int index) {
        return productPrices.get(index).getText();
    }

    public int getProductCount() {
        return productItems.size();
    }

    public void navigateToCart() {
        cartIcon.click();
    }
}