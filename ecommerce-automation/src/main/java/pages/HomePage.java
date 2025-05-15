package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;
    
    @FindBy(className = "title")
    private WebElement pageTitle;
    
    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;
    
    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;
    
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public String getPageTitle() {
        return pageTitle.getText();
    }
    
    public void logout() {
        menuButton.click();
        logoutLink.click();
    }
}