package com.longtester.HRM.testcases;

import com.longtester.HRM.pages.BasePage;
import com.longtester.HRM.pages.LoginPage;
import com.longtester.HRM.pages.ProjectPage;
import com.longtester.common.BaseTest;
import com.longtester.dataprovider.DataProviderFactory;
import com.longtester.helpers.PropertiesHelper;
import org.testng.annotations.Test;

public class ProjectTest extends BaseTest {
    @Test(dataProvider = "data_AddNewProject", dataProviderClass = DataProviderFactory.class)
    public void testAddNewProject(String title, String client, String startDate, String endDate, String summary) {
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        ProjectPage projectPage = new ProjectPage();
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuProject();
        projectPage.addNewProject(title, client, startDate, endDate, summary);
        projectPage.verifyAddProjectSuccess(title, client, startDate, endDate, summary);
    }

    @Test(dataProvider = "data_editProject", dataProviderClass = DataProviderFactory.class)
    public void testEditProject(String title, String client, String startDate, String endDate, String summary, String update_endDate) {
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        ProjectPage projectPage = new ProjectPage();
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuProject();
        projectPage.addNewProject(title, client, startDate, endDate, summary);
        projectPage.editProject(title, update_endDate);
        projectPage.verifyDetailProjectAfterUpdate(update_endDate);
    }

    @Test(dataProvider = "data_deleteProject", dataProviderClass = DataProviderFactory.class)
    public void testDeleteProject(String title, String client, String startDate, String endDate, String summary) {
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        ProjectPage projectPage = new ProjectPage();
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuProject();
        projectPage.addNewProject(title, client, startDate, endDate, summary);
        projectPage.deleteProject(title);
        projectPage.verifyProjectNotDisplayedAfterDelete(title);
    }

    @Test(dataProvider = "data_editStatusProject", dataProviderClass = DataProviderFactory.class)
    public void testEditStatusProject(String title, String client, String startDate, String endDate, String summary, String status, String priority,String progress) {
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        ProjectPage projectPage = new ProjectPage();
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuProject();
        projectPage.addNewProject(title, client, startDate, endDate, summary);
        projectPage.editStatus(title, progress, status, priority);
        projectPage.verifyStatusProjectAfterUpdate(status, priority,progress);
    }
}
