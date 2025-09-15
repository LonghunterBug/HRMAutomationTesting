package com.longtester.HRM.testcases;

import com.longtester.HRM.pages.BasePage;
import com.longtester.HRM.pages.ClientPage;
import com.longtester.HRM.pages.LoginPage;
import com.longtester.common.BaseTest;
import com.longtester.dataprovider.DataProviderFactory;
import com.longtester.helpers.PropertiesHelper;
import org.testng.annotations.Test;

public class ClientTest extends BaseTest {
    @Test(dataProvider = "data_AddNewClient", dataProviderClass = DataProviderFactory.class)
    public void testAddNewClient(String firstName, String lastName, String password, String contactNumber,
                                 String gender, String email, String username, String filepath) {
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        ClientPage clientPage = new ClientPage();
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuClient();
        clientPage.addNewClient(firstName, lastName, password, contactNumber, gender, email, username, filepath);
        clientPage.verifyAddNewClientSuccess(username);
        clientPage.verifyDetailClientAfterAddNew(firstName, lastName, contactNumber, gender, email, username);
    }
    @Test(dataProvider = "data_editClient", dataProviderClass = DataProviderFactory.class)
    public void testEditClient(String firstName, String lastName, String password, String contactNumber,
                                 String gender, String email, String username, String filepath, String country) {
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        ClientPage clientPage = new ClientPage();
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuClient();
        clientPage.addNewClient(firstName, lastName, password, contactNumber, gender, email, username, filepath);
        clientPage.editClient(username,country);// Edit country information
        clientPage.verifyDetailClientAfterUpdate(country);
    }
    @Test(dataProvider = "data_DeleteClient", dataProviderClass = DataProviderFactory.class)
    public void testDeleteClient(String firstName, String lastName, String password, String contactNumber,
                                 String gender, String email, String username, String filepath){
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        ClientPage clientPage = new ClientPage();
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuClient();
        clientPage.addNewClient(firstName, lastName, password, contactNumber, gender, email, username, filepath);
        clientPage.deleteClient(username); // Delete the client just added
        clientPage.verifyClientNotDisplayedAfterDelete(username);
    }
}
