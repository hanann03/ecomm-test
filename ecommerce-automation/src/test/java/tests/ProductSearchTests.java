package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductPage;
import utils.ConfigReader;

public class ProductSearchTests extends BaseTest {
    
    @Test(description = "Verify product sorting functionality")
    public void testProductSorting() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
            ConfigReader.getProperty("valid_username"),
            ConfigReader.getProperty("valid_password")
        );
        
        ProductPage productPage = new ProductPage(driver);
        productPage.sortProducts("Price (low to high)");
        
        String firstPrice = productPage.getProductPrice(0);
        Assert.assertTrue(firstPrice.contains("7.99"), "Products not sorted correctly");
        test.pass("Products sorted by price low to high successfully");
    }
    
    @Test(description = "Verify product listing")
    public void testProductListing() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
            ConfigReader.getProperty("valid_username"),
            ConfigReader.getProperty("valid_password")
        );
        
        ProductPage productPage = new ProductPage(driver);
        Assert.assertTrue(productPage.getProductCount() > 0, "No products displayed");
        test.pass("Product listing verified successfully");
    }
}