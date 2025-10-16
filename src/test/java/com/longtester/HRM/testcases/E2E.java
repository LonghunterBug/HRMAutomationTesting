package com.longtester.HRM.testcases;

import com.longtester.HRM.pages.*;
import com.longtester.common.BaseTest;
import com.longtester.dataprovider.DataProviderFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class E2E extends BaseTest {

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
    }
}
