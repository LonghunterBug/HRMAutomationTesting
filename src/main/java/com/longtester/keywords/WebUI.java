package com.longtester.keywords;


import com.longtester.driver.DriverManager;
import com.longtester.helpers.PropertiesHelper;
import com.longtester.helpers.SoftAssertHelper;
import com.longtester.reports.AllureManager;
import com.longtester.utils.LogUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;

public class WebUI {
    private static WebDriver driver;

    private static int TIMEOUT = Integer.parseInt(PropertiesHelper.getValue("WAIT_EXPLICIT"));
    private static double STEP_TIME = Double.parseDouble(PropertiesHelper.getValue("SLEEP_TIME"));
    private static int PAGE_LOAD_TIMEOUT = 30;


    public static void sleep(double second) {
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void logConsole(Object message) {
        LogUtils.info(message);
    }

    @Step("Refresh page")
    public static void refreshPage() {
        DriverManager.getDriver().navigate().refresh();
        DriverManager.getDriver().navigate().refresh();
        logConsole("Refresh page: " + DriverManager.getDriver().getTitle());
        AllureManager.saveTextLog("Page: " + DriverManager.getDriver().getTitle());
    }

    @Step("Back to previous page")
    public static void backToPreviousPage() {
        DriverManager.getDriver().navigate().back();
        logConsole("Back to previous page: " + DriverManager.getDriver().getTitle());
        AllureManager.saveTextLog("Page: " + DriverManager.getDriver().getTitle());
    }

    public static String getCurrentURL() {
        String currentURL = DriverManager.getDriver().getCurrentUrl();
        logConsole("Current URL: " + currentURL);
        AllureManager.saveTextLog("URL: " + currentURL);
        return currentURL;
    }

    @Step("Open URL: {0}")
    public static void openURL(String url) {
        DriverManager.getDriver().get(url);
        sleep(STEP_TIME);
        logConsole("\uD83C\uDF10 Open URL: " + url);
    }

    @Step("Click on element: {0}")
    public static void clickElement(By by) {
        waitForElementClickable(by);
        sleep(STEP_TIME);
        getWebElement(by).click();
        logConsole("Click on element " + by);
    }

    public static void clearTextWithKey(By by) {
        waitForElementVisible(by);
        sleep(STEP_TIME);
        WebElement element = getWebElement(by);
        element.click();
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
        logConsole("Clear text on element " + by);
    }

    @Step("Set text {1} on element {0}")
    public static void setText(By by, String value) {
        waitForElementVisible(by);
        sleep(STEP_TIME);
        getWebElement(by).sendKeys(value);
        logConsole("Set text " + value + " on element " + by);
    }

    @Step("Upload file path {1} on element {0}")
    public static void uploadFile(By by, String filePath) {
        sleep(STEP_TIME);
        getWebElement(by).sendKeys(filePath);
        logConsole("Upload file: " + filePath + " on element " + by);
    }

    @Step("Scroll to element {0}")
    public static void scrollToElementAtTop(By by) {
        sleep(STEP_TIME);
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", getWebElement(by));
    }

    public static void scrollToElementAtTop(WebElement element) {
        sleep(STEP_TIME);
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToElementAtTop(List<WebElement> elements, int index) {
        sleep(STEP_TIME);
        WebElement element = elements.get(index);
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void highlightElement(By by) {
        // Highlight the element using JavaScript
        String script = "arguments[0].style.border='3px solid red';";

        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript(script, DriverManager.getDriver().findElement(by));
    }

    public static void highlightElement(WebElement element) {
        // Highlight the element using JavaScript
        String script = "arguments[0].style.border='3px solid red';";

        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript(script, element);
    }

    public static void highlightElement(List<WebElement> elements, int index) {
        // Highlight the element using JavaScript
        String script = "arguments[0].style.border='3px solid red';";
        WebElement element = elements.get(index);
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript(script, element);
    }

    @Step("Get text on element {0}")
    public static String getElementText(By by) {
        waitForElementVisible(by);
        logConsole("Get text of element " + by);
        String text = getWebElement(by).getText();
        logConsole("==> TEXT: " + text);
        AllureManager.saveTextLog("==> TEXT: " + text);
        return text; //Trả về một giá trị kiểu String
    }

    @Step("Get attribute value {1} on element {0} ")
    public static String getElementAttribute(By by, String attributeName) {
        waitForElementVisible(by);
        logConsole("Get attribute of element " + by);
        String value = getWebElement(by).getAttribute(attributeName);
        logConsole("==> Attribute value: " + value);
        AllureManager.saveTextLog("==> Value: " + value);
        return value;
    }

    public static void hoverMouse(List<WebElement> elements, int index) {
        try {
            sleep(STEP_TIME);
            Actions action = new Actions(DriverManager.getDriver());
            WebElement element = elements.get(index);
            action.moveToElement(element).perform();
            logConsole("Hover mouse on element " + element.getText());
        } catch (Exception e) {
            logConsole("Error hovering mouse on element " + e + ": " + e.getMessage());
            Assert.fail("Error hovering mouse on element " + e + ": " + e.getMessage());
        }
    }

    public static void hoverMouse(WebElement element) {
        try {
            sleep(STEP_TIME);
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(element).perform();
            logConsole("Hover mouse on element " + element.getText());
        } catch (Exception e) {
            logConsole("Error hovering mouse on element " + e + ": " + e.getMessage());
            Assert.fail("Error hovering mouse on element " + e + ": " + e.getMessage());
        }
    }

    @Step("Hover mouse on element {0}")
    public static void hoverMouse(By by) {
        try {
            waitForElementVisible(by);
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            logConsole("Hover mouse on element " + by);
        } catch (Exception e) {
            logConsole("Error hovering mouse on element " + by + ": " + e.getMessage());
            Assert.fail("Error hovering mouse on element " + by + ": " + e.getMessage());
        }
    }

    @Step("Set slider to {1} on element {0}")
    public static void setValueToSlider(WebElement element, String value) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].value='" + Integer.parseInt(value) + "';", element);
        logConsole("Set slider to value: " + value);
    }

    public static boolean isElementDisplayed(By by) {
        try {
            waitForElementVisible(by);
            WebElement element = getWebElement(by);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementSelected(By by) {
        try {
            WebElement element = getWebElement(by);
            return element.isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isElementEnabled(By by) {
        try {
            waitForElementVisible(by);
            WebElement element = getWebElement(by);
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public static WebElement getWebElement(By by) {
        return DriverManager.getDriver().findElement(by);
    }

    public static List<WebElement> getWebElements(By by) {
        return DriverManager.getDriver().findElements(by);
    }

    //Wait for Element
    public static void waitForElementVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }

    public static void waitForAllElementsVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element Visible. " + by.toString());
            //Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }

    public static void waitForElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            logConsole("Element not exist. " + by.toString());
            Assert.fail("Element not exist. " + by.toString());
        }
    }

    public static void waitForAllElementsPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        } catch (Throwable error) {
            logConsole("Element not exist. " + by.toString());
            Assert.fail("Element not exist. " + by.toString());
        }
    }

    public static void waitForElementClickable(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element ready to click. " + by.toString());
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
        }
    }

    public static void verifySelect(By by, boolean check, String message) {
        String text = getWebElement(by).getText();
        Assert.assertTrue(check, message);
        logConsole("Verify " + text + " is checked");
    }

    public static void softVerifySelect(By by, boolean check, String message) {
        String text = getWebElement(by).getText();
        SoftAssertHelper.getSoftAssert().assertTrue(check, message);
        logConsole("Verify " + text + " is checked");
    }

    public static void verifyEqual(Object actual, Object expected, String message) {
        boolean isEqual = String.valueOf(actual).equals(String.valueOf(expected));
        String stepName;
        if (isEqual) {
            stepName = "✅ PASS: Verify equals | expected=[" + expected + "] and actual=[" + actual + "]";
        } else {
            stepName = "❌ FAIL: Verify equals | " + message +
                    " | expected=[" + expected + "] but found=[" + actual + "]";
        }
        Allure.step(stepName);
        Assert.assertEquals(actual, expected, message);
        logConsole(stepName);
    }

    public static void softVerifyEqual(Object actual, Object expected, String message) {
        boolean isEqual = String.valueOf(actual).equals(String.valueOf(expected));
        String stepName;
        if (isEqual) {
            stepName = "✅ PASS: Verify equals | expected=[" + expected + "] and actual=[" + actual + "]";
        } else {
            stepName = "❌ FAIL: Verify equals | " + message +
                    " | expected=[" + expected + "] but found=[" + actual + "]";
        }
        Allure.step(stepName);
        SoftAssertHelper.getSoftAssert().assertEquals(actual, expected, message);
        logConsole(stepName);
    }

    public static void verifyDisplay(By by, boolean check, String message) {
        String text = getWebElement(by).getText();
        String stepName;
        if (check) {
            stepName = "✅ PASS: Verify element [" + text + "] is displayed";
        } else {
            stepName = "❌ FAIL: Verify element [" + text + "] is not displayed | " + message;
        }
        Allure.step(stepName);
        Assert.assertTrue(check, message);
        logConsole(stepName);
    }

    public static void verifyDisplay(WebElement element, boolean check, String message) {
        String text = element.getText();
        Assert.assertTrue(check, message);
        logConsole("Verify " + text + " is displayed");
    }

    // Trường hợp page có inputSearch, dùng hàm này
    public static void verifyNotDisplay(List<WebElement> elements, String elementName, String message) {
        boolean isNotDisplayed = elements.isEmpty();
        String stepName;
        if (isNotDisplayed) {
            stepName = "✅ PASS: Verify [" + elementName + "] is not displayed";
        } else {
            stepName = "❌ FAIL: Verify [" + elementName + "] is still displayed | " + message;
        }
        Allure.step(stepName);
        Assert.assertTrue(isNotDisplayed, message);
        logConsole(stepName);
    }


    public static void assertAll() {
        SoftAssertHelper.getSoftAssert().assertAll();
        SoftAssertHelper.resetSoftAssert();
    }


}
