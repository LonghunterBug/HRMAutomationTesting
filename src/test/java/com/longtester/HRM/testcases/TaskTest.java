package com.longtester.HRM.testcases;

import com.longtester.common.BaseTest;
import com.longtester.dataprovider.DataProviderFactory;
import com.longtester.helpers.PropertiesHelper;
import com.longtester.keywords.WebUI;
import io.qameta.allure.*;
import org.testng.annotations.Test;

public class TaskTest extends BaseTest {
    @Owner("Minh Long")
    @Epic("Task Management")
    @Feature("Add new task")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test add new task function")
    @Test(dataProvider = "data_addNewTask", dataProviderClass = DataProviderFactory.class)
    public void testAddNewTask(String titleTask, String startDate, String endDate,String project, String summary) {
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuTask();
        taskPage.addNewTask(titleTask, startDate, endDate, project, summary);
        taskPage.verifyAddTaskSuccess(titleTask, startDate, endDate, project, summary);
        WebUI.assertAll();
    }
    @Owner("Minh Long")
    @Epic("Task Management")
    @Feature("Delete task")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test delete task function")
    @Test(dataProvider = "data_deleteTask", dataProviderClass = DataProviderFactory.class)
    public void testDeleteTask(String titleTask, String startDate, String endDate,String project, String summary) {
        loginPage.loginHRM(PropertiesHelper.getValue("ADMIN_USERNAME"), PropertiesHelper.getValue("ADMIN_PASSWORD"));
        basePage.clickMenuTask();
        taskPage.addNewTask(titleTask, startDate, endDate, project, summary);
        taskPage.deleteTask(titleTask);
        taskPage.verifyTaskNotDisplayedAfterDelete(titleTask);
        WebUI.assertAll();
    }

}
