package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductPage;
import utils.ConfigReader;

public class CartTests extends BaseTest {
    
    @Test(description = "Verify add to cart functionality")
    public void testAddToCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
            ConfigReader.getProperty("valid_username"),
            ConfigReader.getProperty("valid_password")
        );
        
        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart(0);
        productPage.navigateToCart();
        
        CartPage cartPage = new CartPage(driver);
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Item not added to cart");
        test.pass("Item added to cart successfully");
    }
    
    @Test(description = "Verify remove from cart functionality")
    public void testRemoveFromCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
            ConfigReader.getProperty("valid_username"),
            ConfigReader.getProperty("valid_password")
        );
        
        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart(0);
        productPage.navigateToCart();
        
        CartPage cartPage = new CartPage(driver);
        cartPage.removeItem(0);
        Assert.assertEquals(cartPage.getCartItemCount(), 0, "Item not removed from cart");
        test.pass("Item removed from cart successfully");
    }
}