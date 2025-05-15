package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;

public class CheckoutTests extends BaseTest {
    
    @Test(description = "Verify complete checkout flow")
    public void testCompleteCheckout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
            ConfigReader.getProperty("valid_username"),
            ConfigReader.getProperty("valid_password")
        );
        
        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart(0);
        productPage.navigateToCart();
        
        CartPage cartPage = new CartPage(driver);
        cartPage.proceedToCheckout();
        
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterShippingInfo("John", "Doe", "12345");
        checkoutPage.continueToOverview();
        checkoutPage.completePurchase();
        
        Assert.assertEquals(checkoutPage.getConfirmationMessage(), 
                          "Thank you for your order!",
                          "Order confirmation not displayed");
        test.pass("Checkout completed successfully");
    }
}