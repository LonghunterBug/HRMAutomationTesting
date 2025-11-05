package com.longtester.common;


import com.longtester.driver.DriverManager;
import com.longtester.helpers.PropertiesHelper;
import com.longtester.helpers.SoftAssertHelper;
import com.longtester.listeners.TestListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.Dimension;
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
    public void runConfig() {
        PropertiesHelper.loadAllFiles();
    }

    @BeforeMethod
    public void openBrowser() {
        WebDriver driver;// khao báo driver cục bộ
        String browser = PropertiesHelper.getValue("BROWSER").trim().toLowerCase();
        boolean isHeadless = PropertiesHelper.getValue("HEADLESS").equalsIgnoreCase("true");
        Allure.step("Open " + browser + " browser");
        switch (browser) {
            case "chrome":
                //WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--guest");
                if (isHeadless) {
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                //WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if(isHeadless){
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                if (isHeadless) {
                    driver.manage().window().setSize(new Dimension(1920, 1080));// Firefox bỏ qua --window-size
                }
                break;
            case "edge":
                //WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) {
                    edgeOptions.addArguments("--headless");
                    edgeOptions.addArguments("--window-size=1920,1080");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
                ChromeOptions defaultChromeOptions = new ChromeOptions();
                defaultChromeOptions.addArguments("--guest");
                if (isHeadless) {
                    defaultChromeOptions.addArguments("--headless");
                    defaultChromeOptions.addArguments("--window-size=1920,1080");
                }
                driver = new ChromeDriver(defaultChromeOptions); // mặc định Chrome
        }
        DriverManager.setDriver(driver);
        if (!isHeadless) {
            DriverManager.getDriver().manage().window().maximize();
        }
        // Reset SoftAssert trước mỗi test
        SoftAssertHelper.resetSoftAssert();
    }

    @AfterMethod
    public void tearDown() {
        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();  // luôn đóng driver
            Allure.step("Close " + PropertiesHelper.getValue("BROWSER").trim().toLowerCase() + " browser");
        }
    }
}
