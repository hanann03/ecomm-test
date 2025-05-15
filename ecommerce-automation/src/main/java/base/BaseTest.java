package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {
    protected static ExtentReports extent;
    protected WebDriver driver;
    protected ExtentTest test;
    
    @BeforeSuite
    public void setupReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }
    
    @BeforeMethod
    public void setup(Method method) {
        // Initialize browser based on configuration (chrome by default)
        String browser = ConfigReader.getProperty("browser", "chrome");
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
        
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("implicit_wait"))));
        
        test = extent.createTest(method.getName());
        driver.get(ConfigReader.getProperty("url"));
    }
    
    private String captureScreenshot(String testName) {
        try {
            File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            String dest = "test-output/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
            FileUtils.copyFile(source, new File(dest));
            return dest;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = captureScreenshot(result.getName());
            test.fail(result.getThrowable(), 
                     MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        } else {
            test.skip("Test skipped");
        }
        
        if (driver != null) {
            driver.quit();
        }
    }
    
    @AfterSuite
    public void flushReport() {
        extent.flush();
    }
}