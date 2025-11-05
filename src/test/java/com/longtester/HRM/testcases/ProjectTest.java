package com.longtester.HRM.testcases;

import com.longtester.HRM.pages.BasePage;
import com.longtester.HRM.pages.LoginPage;
import com.longtester.HRM.pages.ProjectPage;
import com.longtester.common.BaseTest;
import com.longtester.dataprovider.DataProviderFactory;
import com.longtester.helpers.PropertiesHelper;
import com.longtester.keywords.WebUI;
import io.qameta.allure.*;
import org.testng.annotations.Test;

public class ProjectTest extends BaseTest {
    @Owner("Minh Long")
    @Epic("Project Management")
    @Feature("Add new project")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test add new project function")
    @Test(dataProvider = "data_AddNewProject", dataProviderClass = DataProviderFactory.class,priority = 2)
    public void testAddNewProject(String title, String client, String startDate, String endDate, String summary) {
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        ProjectPage projectPage = new ProjectPage();
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuProject();
        projectPage.addNewProject(title, client, startDate, endDate, summary);
        projectPage.verifyAddProjectSuccess(title, client, startDate, endDate, summary);
        WebUI.assertAll();
    }
    @Owner("Minh Long")
    @Epic("Project Management")
    @Feature("Edit project")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test edit project function")
    @Test(dataProvider = "data_editProject", dataProviderClass = DataProviderFactory.class,priority = 3)
    public void testEditProject(String title, String client, String startDate, String endDate, String summary, String update_endDate) {
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        ProjectPage projectPage = new ProjectPage();
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuProject();
        projectPage.addNewProject(title, client, startDate, endDate, summary);
        projectPage.editProject(title, update_endDate);
        projectPage.verifyDetailProjectAfterUpdate(update_endDate);
        WebUI.assertAll();
    }
    @Owner("Minh Long")
    @Epic("Project Management")
    @Feature("Delete project")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test delete project function")
    @Test(dataProvider = "data_deleteProject", dataProviderClass = DataProviderFactory.class,priority = 4)
    public void testDeleteProject(String title, String client, String startDate, String endDate, String summary) {
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        ProjectPage projectPage = new ProjectPage();
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuProject();
        projectPage.addNewProject(title, client, startDate, endDate, summary);
        projectPage.deleteProject(title);
        projectPage.verifyProjectNotDisplayedAfterDelete(title);
        WebUI.assertAll();
    }
    @Owner("Minh Long")
    @Epic("Project Management")
    @Feature("Edit status project")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test edit status project function")
    @Test(dataProvider = "data_editStatusProject", dataProviderClass = DataProviderFactory.class,priority = 1)
    public void testEditStatusProject(String title, String client, String startDate, String endDate, String summary, String status, String priority, String progress) {
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        ProjectPage projectPage = new ProjectPage();
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuProject();
        projectPage.addNewProject(title, client, startDate, endDate, summary);
        projectPage.editStatus(title, progress, status, priority);
        projectPage.verifyStatusProjectAfterUpdate(status, priority, progress);
        WebUI.assertAll();
    }
    @Owner("Minh Long")
    @Epic("Project Management")
    @Feature("Add attach file for project")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test add attach file function")
    @Test(dataProvider = "data_addAttachFileProject", dataProviderClass = DataProviderFactory.class)
    public void testAddAttachFileProject(String title, String client, String startDate, String endDate, String summary, String filename, String filepath) {
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        ProjectPage projectPage = new ProjectPage();
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuProject();
        projectPage.addNewProject(title,client,startDate,endDate,summary);
        projectPage.addAttachFile(title, filename, filepath);
        projectPage.verifyUploadAttachSuccess(filename);
    }

}
