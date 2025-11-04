package com.longtester.HRM.pages;

import com.longtester.helpers.PropertiesHelper;
import com.longtester.keywords.WebUI;
import org.openqa.selenium.By;

public class LoginPage {
    // Login form locator
    By inputUsername = By.xpath("//input[@id='iusername']");
    By inputPassword = By.xpath("//input[@id='ipassword']");
    By buttonLogin = By.xpath("//button[normalize-space()='Login']");
    // Alert locator
    By alertErrorInputRequiredField = By.xpath("//div[@class='toast toast-error']");
    By alertLoginSuccess = By.xpath("//h2[@id='swal2-title']");

    public void loginHRM(String username, String password){
        WebUI.openURL(PropertiesHelper.getValue("URL"));
        WebUI.setText(inputUsername,username);
        WebUI.setText(inputPassword,password);
        WebUI.clickElement(buttonLogin);
    }
    public void verifyLoginSuccess(){
        String actual_text= WebUI.getElementText(alertLoginSuccess);
        WebUI.softVerifyEqual(actual_text,"Logged In Successfully.",actual_text + " not match with expected");
    }
    public void verifyShowAlertErrorInputUsernameRequired(){
        String actual_text= WebUI.getElementText(alertErrorInputRequiredField);
        WebUI.verifyEqual(actual_text,"tThe username field is required.",actual_text + " not match with expected");
    }
    public void verifyShowAlertErrorInputPasswordRequired(){
        String actual_text= WebUI.getElementText(alertErrorInputRequiredField);
        WebUI.verifyEqual(actual_text,"The password field is required.",actual_text + " not match with expected");
    }

}