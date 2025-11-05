package com.longtester.HRM.testcases;

import com.longtester.HRM.pages.*;
import com.longtester.common.BaseTest;
import com.longtester.dataprovider.DataProviderFactory;
import com.longtester.keywords.WebUI;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class E2E extends BaseTest {
    @Owner("Minh Long")
    @Epic("E2E Flow")
    @Feature("Admin views task created by client")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test admin can view task which is created by client ")
    @Test(dataProvider = "data_E2E", dataProviderClass = DataProviderFactory.class)
    public void testAdminCanViewClientTask(String username_admin, String password_admin, String firstname,
                                           String lastname, String password_client, String contactnumber,
                                           String gender, String mail, String username_client, String filepath,
                                           String titleProject, String startdateProject, String endateProject,
                                           String client, String summaryProject, String titleTask, String startdateTask,
                                           String endateTask,String summaryTask) {
        LoginPage loginPage = new LoginPage();
        BasePage basePage = new BasePage();
        ClientPage clientPage = new ClientPage();
        TaskPage taskPage = new TaskPage();
        ProjectPage projectPage = new ProjectPage();
        loginPage.loginHRM(username_admin,password_admin);
        basePage.clickMenuClient();
        clientPage.addNewClient(firstname,lastname,password_client,contactnumber,gender,mail,username_client,filepath);
        clientPage.verifyNewClientDisplayedInTable(username_client);
        basePage.clickMenuProject();
        projectPage.addNewProject(titleProject,client,startdateProject,endateProject,summaryProject);
        projectPage.verifyNewProjectDisplayedInTable(titleProject);
        basePage.clickLogOut();
        loginPage.loginHRM(username_client,password_client);
        loginPage.verifyLoginSuccess();
        basePage.verifyUserNavigateToHome();
        basePage.clickMenuTask();
        basePage.verifyUserNavigateToTask();
        taskPage.addNewTask(titleTask,startdateTask,endateTask,titleProject,summaryTask);
        taskPage.verifyNewTaskDisplayedInTable(titleTask);
        basePage.clickLogOut();
        loginPage.loginHRM(username_admin,password_admin);
        basePage.clickMenuClient();
        clientPage.verifyAdminCanViewTaskCreatedByClient(client,titleTask);
        WebUI.assertAll();
    }
}
/*
1. Login admin account
2. Create a new client account => verify create success
3. Create a new project => verify create success
4. Logout admin account
5. Login client account => verify login success
6. Create a new task => verify create success
7. Logout client account
8. Login admin account
9. Navigate to client detail and verify admin should view task which is created by client
Notes: If client want create a task, client should be assigned to any project
 */
