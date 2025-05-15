package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;

public class ScreenshotUtils {
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String dest = System.getProperty("user.dir") + "/test-output/screenshots/" 
                        + screenshotName + "_" + System.currentTimeMillis() + ".png";
            File destination = new File(dest);
            FileUtils.copyFile(source, destination);
            return dest;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to capture screenshot: " + e.getMessage();
        }
    }
}