package com.longtester.HRM.pages;

import com.longtester.driver.DriverManager;
import com.longtester.helpers.DateHelper;
import com.longtester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.util.List;

public class TaskPage {
    //Add Task form
    By buttonAddNewTask = By.xpath("//a[normalize-space()='Add New']");
    By tabTask = By.xpath("//a[normalize-space()='Tasks' and @id='pills-tasks-tab']");
    By inputTitleTask = By.xpath("//input[@placeholder='Title' and @name='task_name']");
    By inputStartDateTask = By.xpath("//input[@placeholder='Start Date']");
    By inputEndDateTask = By.xpath("//input[@placeholder='End Date']");
    By selectProject = By.xpath("//label[@for='project_ajax']/following::span[contains(@id,'project')]");
    By inputSearchProject = By.xpath("//input[@type='search' and @role='searchbox' and contains(@aria-activedescendant,'project')]");
    By inputSummary = By.xpath("//textarea[@id='summary']");
    By buttonSave = By.xpath("//span[normalize-space()='Save']/parent::button");

    //Date picker form
    By currentMonth = By.xpath("//div[@class='dtp animated fadeIn']//div[contains(@class,'actual-month')]");
    By iconForwardMonth = By.xpath("//div[@class='dtp animated fadeIn']//a[@class='dtp-select-month-after']");
    By iconBackMonth = By.xpath("//div[@class='dtp animated fadeIn']//a[@class='dtp-select-month-before']");
    By currentYear = By.xpath("//div[@class='dtp animated fadeIn']//div[contains(@class,'actual-year')]");
    By iconForwardYear = By.xpath("//div[@class='dtp animated fadeIn']//a[@class='dtp-select-year-after']");
    By iconBackYear = By.xpath("//div[@class='dtp animated fadeIn']//a[@class='dtp-select-year-before']");
    By currentDate = By.xpath("//div[@class='dtp animated fadeIn']//table[contains(@class,'picker-days')]//td");
    By buttonOK = By.xpath("//div[@class='dtp animated fadeIn']//div[@class='dtp-buttons']/button[text()='OK']");

    // Status task
    By totalTasksNotStarted = By.xpath("//span[normalize-space()='Not Started']/ancestor::div/h2");
    //Common
    By alertAddNewTaskSuccess = By.xpath("//div[contains(@class,'toast-success')]");
    By inputSearch = By.xpath("//input[@type='search' and @aria-controls='xin_table']");
    By buttonViewDetail = By.xpath("//button/parent::a[contains(@href,'task-detail')]");
    By buttonDeleteTask = By.xpath("//button/parent::span[@data-original-title='Delete']");
    By buttonConfirmDelete = By.xpath("//div[@class='modal-content']//span[normalize-space()='Confirm']");

    //Overview task tab
    By textTitle = By.xpath("//div[@id='pills-overview']//tr[1]/td[2]");
    By textStartDate = By.xpath("//div[@id='pills-overview']//tr[2]/td[2]");
    By textEndDate = By.xpath("//div[@id='pills-overview']//tr[3]/td[2]");
    By textProject = By.xpath("//div[@id='pills-overview']//tr[5]/td[2]");
    By textSummary = By.xpath("//div[@id='pills-overview']//div[3]");

    private int TotalTaskNotStartedBefore;

    private int getTotalTasksNotStarted() {
        String total_text = WebUI.getElementText(totalTasksNotStarted);
        return Integer.parseInt(total_text);
    }

    private void clickAddNewTask() {
        WebUI.waitForElementVisible(buttonAddNewTask);
        WebUI.clickElement(buttonAddNewTask);
    }

    private void searchTitle(String title) {
        WebUI.waitForElementVisible(inputSearch);
        WebUI.setText(inputSearch, title);
        WebUI.sleep(1);
    }

    private void clickViewDetail() {
        WebUI.hoverMouse(By.xpath("//tbody/tr[@role='row']"));
        WebUI.waitForElementVisible(buttonViewDetail);
        WebUI.clickElement(buttonViewDetail);
    }
    private void clickDeleteTask() {
        WebUI.hoverMouse(By.xpath("//tbody/tr[@role='row']"));
        WebUI.waitForElementVisible(buttonDeleteTask);
        WebUI.clickElement(buttonDeleteTask);
    }

    private void selectDate(String date) {
        LocalDate getdate = DateHelper.parseDate(date);
        String expect_day = String.format("%02d", getdate.getDayOfMonth());
        String expect_month = String.valueOf(getdate.getMonth().toString().substring(0, 3).toUpperCase());
        String expect_year = String.valueOf(getdate.getYear());
        // Handle month
        while (true) {
            String actual_month = DriverManager.getDriver().findElement(currentMonth).getText();
            if (actual_month.equalsIgnoreCase(expect_month)) {
                break;
            }
            if (DateHelper.convertMonthToNumber(expect_month) < DateHelper.convertMonthToNumber(actual_month))//So sánh nếu expect month < actual month
            {
                DriverManager.getDriver().findElement(iconBackMonth).click();
            } else {
                DriverManager.getDriver().findElement(iconForwardMonth).click();
            }
        }
        WebUI.logConsole("Select month: " + expect_month);
        // Handle year
        while (true) {
            String actual_year = DriverManager.getDriver().findElement(currentYear).getText();
            if (actual_year.equalsIgnoreCase(expect_year)) {
                break;
            }
            if (Integer.parseInt(expect_year) < Integer.parseInt(actual_year))//So sánh nếu expect year < actual year
            {
                DriverManager.getDriver().findElement(iconBackYear).click();
            } else {
                DriverManager.getDriver().findElement(iconForwardYear).click();
            }
        }
        WebUI.logConsole("Select year: " + expect_year);
        List<WebElement> allValidDate = WebUI.getWebElements(currentDate);
        for (WebElement e : allValidDate) {
            if (e.getText().equalsIgnoreCase(expect_day)) {
                e.click();
                break;
            }
        }
        WebUI.logConsole("Select date: " + expect_day);
    }

    public void addNewTask(String titleTask, String startDateTask, String endDateTask, String titleProject, String summary) {
        clickAddNewTask();
        WebUI.waitForElementVisible(inputTitleTask);
        WebUI.setText(inputTitleTask, titleTask);
        WebUI.clickElement(inputStartDateTask);
        selectDate(startDateTask);
        WebUI.clickElement(buttonOK);
        WebUI.clickElement(inputEndDateTask);
        selectDate(endDateTask);
        WebUI.clickElement(buttonOK);
        WebUI.clickElement(selectProject);
        WebUI.setText(inputSearchProject, titleProject);
        By optionProject = By.xpath("//li[normalize-space()='" + titleProject + "']");
        WebUI.clickElement(optionProject);
        WebUI.setText(inputSummary, summary);
        WebUI.clickElement(buttonSave);
    }
    public void deleteTask(String titleTask) {
        WebUI.refreshPage();
        TotalTaskNotStartedBefore = getTotalTasksNotStarted();// Lấy con số trước khi delete project
        searchTitle(titleTask);
        clickDeleteTask();
        WebUI.clickElement(buttonConfirmDelete);
    }

    public void verifyAddTaskSuccess(String titleTask, String startDateTask, String endDateTask, String project, String summary) {
        TotalTaskNotStartedBefore = getTotalTasksNotStarted();
        WebUI.refreshPage();
        int expect_TotalTaskNotStarted = TotalTaskNotStartedBefore + 1;
        WebUI.softVerifyEqual(getTotalTasksNotStarted(),expect_TotalTaskNotStarted,"Total task notstarted not match with expected");
        searchTitle(titleTask);
        clickViewDetail();
        WebUI.softVerifyEqual(WebUI.getElementText(textTitle), titleTask, "Title task not match with expected");
        WebUI.softVerifyEqual(WebUI.getElementText(textStartDate).trim(), startDateTask, "Start date task not match with expected");
        WebUI.softVerifyEqual(WebUI.getElementText(textEndDate).trim(), endDateTask, "End date task not match with expected");
        WebUI.softVerifyEqual(WebUI.getElementText(textProject).trim(), project, "Project task not match with expected");
        WebUI.softVerifyEqual(WebUI.getElementText(textSummary).replace("Summary","").trim(), summary, "Summary task not match with expected");
        WebUI.assertAll();
    }
    public void verifyTaskNotDisplayedAfterDelete(String titleTask) {
        WebUI.refreshPage();
        int actual_TotalTasksNotStarted = getTotalTasksNotStarted();
        int expect_TotalTasksNotStarted = TotalTaskNotStartedBefore - 1;
        WebUI.softVerifyEqual(actual_TotalTasksNotStarted, expect_TotalTasksNotStarted, "Total tasks notstarted not match with expected");
        searchTitle(titleTask);
        List<WebElement> list_task = WebUI.getWebElements(By.xpath("//table//td[1][normalize-space()='" + titleTask + "']"));
        WebUI.verifyNotDisplay(list_task, titleTask, titleTask + " still display in table after delete");
        WebUI.assertAll();
    }


}
