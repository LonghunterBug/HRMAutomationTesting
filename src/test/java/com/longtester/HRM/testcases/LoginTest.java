package com.longtester.HRM.testcases;

import com.longtester.HRM.pages.BasePage;
import com.longtester.HRM.pages.LoginPage;
import com.longtester.common.BaseTest;
import com.longtester.dataprovider.DataProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "data_LoginSuccess_registered_account",dataProviderClass = DataProviderFactory.class)
    public void testLoginSuccess(String username, String password){
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        loginPage.loginHRM(username,password);
        loginPage.verifyShowAlertLoginSuccess();
        basePage.verifyUserNavigateToHome();
    }
    @Test(dataProvider = "data_LoginFail_without_username",dataProviderClass = DataProviderFactory.class)
    public void testLoginFailureWithInvalidUsername(String username, String password){
        LoginPage loginPage = new LoginPage();
        loginPage.loginHRM(username,password);
        loginPage.verifyShowAlertErrorInputUsernameRequired();
    }
    @Test(dataProvider = "data_LoginFail_without_password",dataProviderClass = DataProviderFactory.class)
    public void testLoginFailureWithInvalidPassword(String username, String password){
        LoginPage loginPage = new LoginPage();
        loginPage.loginHRM(username,password);
        loginPage.verifyShowAlertErrorInputPasswordRequired();
    }

}
