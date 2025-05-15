package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginTests extends BaseTest {
    
    @Test(description = "Verify successful login with valid credentials")
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        test.info("Attempting login with valid credentials");
        
        loginPage.login(
            ConfigReader.getProperty("valid_username"),
            ConfigReader.getProperty("valid_password")
        );
        
        HomePage homePage = new HomePage(driver);
        Assert.assertEquals(homePage.getPageTitle(), "Products");
        test.pass("Login successful with valid credentials");
    }
    
    @Test(description = "Verify login fails with invalid credentials")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        test.info("Attempting login with invalid credentials");
        
        loginPage.login(
            ConfigReader.getProperty("invalid_username"),
            ConfigReader.getProperty("invalid_password")
        );
        
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        test.pass("Login failed as expected with invalid credentials");
    }
}