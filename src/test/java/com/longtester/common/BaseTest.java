package com.longtester.common;

import com.longtester.driver.DriverManager;
import com.longtester.helpers.PropertiesHelper;
import com.longtester.helpers.SoftAssertHelper;
import com.longtester.keywords.WebUI;
import com.longtester.listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseTest {
    @BeforeSuite
    public void runConfig(){
        PropertiesHelper.loadAllFiles();
    }
    @BeforeMethod
    public void openBrowser(){
        WebDriver driver;// khao báo driver cục bộ
        String browser = PropertiesHelper.getValue("BROWSER").trim().toLowerCase();
        ChromeOptions options = new ChromeOptions();
        boolean isHeadless = PropertiesHelper.getValue("HEADLESS").equalsIgnoreCase("true");

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--guest");
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }
                chromeOptions.addArguments("--guest");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if(isHeadless){
                    firefoxOptions.addArguments("--guest");
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--window-size=1920,1080");
                }
                firefoxOptions.addArguments("--guest");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) {
                    edgeOptions.addArguments("--guest");
                    edgeOptions.addArguments("--headless");
                    edgeOptions.addArguments("--window-size=1920,1080");
                }
                edgeOptions.addArguments("--guest");
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
                driver = new ChromeDriver(); // mặc định Chrome
        }
        DriverManager.setDriver(driver);
        DriverManager.getDriver().manage().window().maximize();
        // Reset SoftAssert trước mỗi test
        SoftAssertHelper.resetSoftAssert();
    }
    @AfterMethod
    public void tearDown(){
        if (DriverManager.getDriver() != null) {
                DriverManager.getDriver().quit();  // luôn đóng driver
            }
        }
}
