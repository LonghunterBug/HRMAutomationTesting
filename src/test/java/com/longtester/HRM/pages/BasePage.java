package com.longtester.HRM.pages;

import com.longtester.keywords.WebUI;
import org.openqa.selenium.By;

public class BasePage {
    By menuProject = By.xpath("//li/a[contains(@href,'projects')]");
    By menuClient = By.xpath("//li/a[contains(@href,'clients')]");
    By buttonLogout = By.xpath("//div[normalize-space()='Logout']/a");
    By menuHome = By.xpath("//li/a[contains(@href,'desk')]");

    public void clickMenuProject(){
        WebUI.waitForElementVisible(menuProject);
        WebUI.clickElement(menuProject);
    }
    public void clickMenuClient(){
        WebUI.waitForElementVisible(menuClient);
        WebUI.scrollToElementAtTop(menuClient);
        WebUI.clickElement(menuClient);
    }
    public void verifyUserNavigateToHome(){
        WebUI.verifyDisplay(menuHome,WebUI.isElementDisplayed(menuHome),"Menu Home not display");
        String actual_url = WebUI.getCurrentURL();
        WebUI.verifyEqual(actual_url,"https://hrm.anhtester.com/erp/desk","User not navigate to Home");
    }
}
