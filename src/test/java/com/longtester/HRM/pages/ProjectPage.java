package com.longtester.HRM.pages;

import com.longtester.driver.DriverManager;
import com.longtester.helpers.DateHelper;
import com.longtester.helpers.SystemHelper;
import com.longtester.keywords.WebUI;
import io.qameta.allure.Allure;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProjectPage {
    //Add project form
    By buttonAddNewProject = By.xpath("//a[normalize-space()='Add New']");
    By inputTitle = By.xpath("//input[@placeholder='Title']");
    By selectClient = By.xpath("//label[@for='client_id']/following::span[contains(@id,'client')]");
    By inputSearchClient = By.xpath("//input[@type='search' and @role='searchbox' and contains(@aria-activedescendant,'client')]");
    By inputStartDate = By.xpath("//input[@placeholder='Start Date']");
    By inputEndDate = By.xpath("//input[@placeholder='End Date']");
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
    //Status project
    By totalProjectsCompleted = By.xpath("//span[normalize-space()='Completed']/ancestor::div/h2");
    By totalProjectsInProgress = By.xpath("//span[normalize-space()='In Progress']/ancestor::div/h2");
    By totalProjectsNotStarted = By.xpath("//span[normalize-space()='Not Started']/ancestor::div/h2");
    By totalProjectsOnHold = By.xpath("//span[normalize-space()='On Hold']/ancestor::div/h2");
    //Common
    By alertSuccess = By.xpath("//div[contains(@class,'toast-success')]");
    By inputSearch = By.xpath("//input[@type='search' and @aria-controls='xin_table']");
    By buttonViewDetail = By.xpath("//button/parent::a[contains(@href,'project-detail')]");
    By buttonDeleteProject = By.xpath("//button/parent::span[@data-original-title='Delete']");
    By buttonConfirmDelete = By.xpath("//div[@class='modal-content']//span[normalize-space()='Confirm']");

    //Overview project tab
    By buttonUpdateProject = By.xpath("//span[normalize-space()='Update Project']/parent::button");
    By textTitle = By.xpath("//div[@id='pills-overview']//tr[1]/td[2]");
    By textClient = By.xpath("//div[@id='pills-overview']//tr[2]/td[2]");
    By textStartDate = By.xpath("//div[@id='pills-overview']//tr[5]/td[2]");
    By textEndDate = By.xpath("//div[@id='pills-overview']//tr[6]/td[2]");
    By textSummary = By.xpath("//div[@id='pills-overview']//div[3]");

    //Edit project tab
    By tabEdit = By.xpath("//a[normalize-space()='Edit']");

    //Edit project status
    By sliderValue = By.xpath("//span[@class='irs-single']");
    By selectPriority = By.xpath("//select[@name='priority']/following-sibling::span");
    By buttonUpdateStatus = By.xpath("//span[normalize-space()='Update Status']/parent::button");

    //Attach tab
    By tabAttachFile = By.xpath("//a[normalize-space()='Attach files']");
    By inputFileName = By.xpath("//input[@name='file_name']");
    By buttonChooseFile = By.xpath("//input[@id='attachment_file']");
    By buttonAddFile = By.xpath("//span[normalize-space()='Add File']/parent::button");


    private int TotalProjectsCompletedBefore;
    private int TotalProjectsInProgressBefore;
    private int TotalProjectsNotStartedBefore;
    private int TotalProjectsOnHoldBefore;

    private int getTotalProjectsCompleted() {
        String total_text = WebUI.getElementText(totalProjectsCompleted);
        return Integer.parseInt(total_text);
    }

    private int getTotalProjectsInProgress() {
        String total_text = WebUI.getElementText(totalProjectsInProgress);
        return Integer.parseInt(total_text);
    }

    private int getTotalProjectsNotStarted() {
        String total_text = WebUI.getElementText(totalProjectsNotStarted);
        return Integer.parseInt(total_text);
    }

    private int getTotalProjectsOnHold() {
        String total_text = WebUI.getElementText(totalProjectsOnHold);
        return Integer.parseInt(total_text);
    }

    private void clickAddNewProject() {
        WebUI.waitForElementVisible(buttonAddNewProject);
        WebUI.clickElement(buttonAddNewProject);
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

    private void clickDeleteProject() {
        WebUI.hoverMouse(By.xpath("//tbody/tr[@role='row']"));
        WebUI.waitForElementVisible(buttonDeleteProject);
        WebUI.clickElement(buttonDeleteProject);
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
        Allure.step("Select month: " + expect_month);
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
        Allure.step("Select year: " + expect_year);
        List<WebElement> allValidDate = WebUI.getWebElements(currentDate);
        for (WebElement e : allValidDate) {
            if (e.getText().equalsIgnoreCase(expect_day)) {
                e.click();
                break;
            }
        }
        WebUI.logConsole("Select date: " + expect_day);
        Allure.step("Select date: " + expect_day);
    }

    public void addNewProject(String title, String client, String startDate, String endDate, String summary) {
        clickAddNewProject();
        WebUI.setText(inputTitle, title);
        WebUI.clickElement(selectClient);
        WebUI.setText(inputSearchClient, client);
        By optionClient = By.xpath("//li[normalize-space()='" + client + "']");
        WebUI.clickElement(optionClient);
        WebUI.clickElement(inputStartDate);
        selectDate(startDate);
        WebUI.clickElement(buttonOK);
        WebUI.clickElement(inputEndDate);
        selectDate(endDate);
        WebUI.clickElement(buttonOK);
        WebUI.setText(inputSummary, summary);
        WebUI.clickElement(buttonSave);
    }


    public void editProject(String title, String update_endDate) {
        searchTitle(title);
        clickViewDetail();
        WebUI.clickElement(tabEdit);
        WebUI.clickElement(inputEndDate);
        selectDate(update_endDate);
        WebUI.clickElement(buttonOK);
        WebUI.clickElement(buttonUpdateProject);
    }

    public void editStatus(String title, String progress, String status, String priority) {
        WebUI.refreshPage();
        TotalProjectsCompletedBefore = getTotalProjectsCompleted();
        TotalProjectsInProgressBefore = getTotalProjectsInProgress();
        TotalProjectsOnHoldBefore = getTotalProjectsOnHold();
        TotalProjectsNotStartedBefore = getTotalProjectsNotStarted();
        searchTitle(title);
        clickViewDetail();
        WebUI.waitForElementVisible(buttonUpdateStatus);
        WebElement sliderline = DriverManager.getDriver().findElement(By.xpath("//input[@id='progres_val']"));
        WebUI.setValueToSlider(sliderline, progress);
        WebUI.clickElement(By.xpath("//a[normalize-space(@data-rating-text)='" + status + "']"));
        WebUI.clickElement(selectPriority);
        WebUI.clickElement(By.xpath("//ul/li[normalize-space()='" + priority + "']"));
        WebUI.clickElement(buttonUpdateStatus);
    }

    public void deleteProject(String title) {
        WebUI.refreshPage();
        TotalProjectsNotStartedBefore = getTotalProjectsNotStarted();// Lấy con số trước khi delete project
        searchTitle(title);
        clickDeleteProject();
        WebUI.clickElement(buttonConfirmDelete);
    }

    public void addAttachFile(String title, String fileName, String filepath) {
        searchTitle(title);
        clickViewDetail();
        WebUI.waitForElementVisible(tabAttachFile);
        WebUI.clickElement(tabAttachFile);
        WebUI.setText(inputFileName, fileName);
        WebUI.uploadFile(buttonChooseFile, SystemHelper.getCurrentDir() + filepath);
        WebUI.clickElement(buttonAddFile);
    }

    public void verifyAddProjectSuccess(String titleProject, String client, String startDate, String endDate, String summary) {
        int actual_TotalProjectsNotStarted = getTotalProjectsNotStarted();
        WebUI.refreshPage();
        int expect_TotalProjectsNotStarted = actual_TotalProjectsNotStarted + 1;
        WebUI.softVerifyEqual(getTotalProjectsNotStarted(), expect_TotalProjectsNotStarted, "Total project notstarted not match with expected");
        searchTitle(titleProject);
        WebUI.verifyDisplay(By.xpath("//table[@id='xin_table']//td[1][normalize-space()='" + titleProject + "']"),WebUI.isElementDisplayed(By.xpath("//table[@id='xin_table']//td[1][normalize-space()='" + titleProject + "']")),"Project not found in table");
        clickViewDetail();
        WebUI.softVerifyEqual(WebUI.getElementText(textTitle), titleProject, "Title not match with expected");
        WebUI.softVerifyEqual(WebUI.getElementText(textClient), client, "Client not match with expected");
        WebUI.softVerifyEqual(WebUI.getElementText(textStartDate).trim(), startDate, "Start date not match with expected");
        WebUI.softVerifyEqual(WebUI.getElementText(textEndDate).trim(), endDate, "End date not match with expected");
        WebUI.softVerifyEqual(WebUI.getElementText(textSummary).replace("Summary", "").trim(), summary, "Summary not match with expected");
        WebUI.assertAll();
    }
    public void verifyNewProjectDisplayedInTable(String titleProject){
        WebUI.waitForElementVisible(alertSuccess);
        String actual_text = WebUI.getElementText(alertSuccess);
        WebUI.softVerifyEqual(actual_text, "Project added.", actual_text + " not match with expected");
        searchTitle(titleProject);
        WebUI.verifyDisplay(By.xpath("//table[@id='xin_table']//td[1][normalize-space()='" + titleProject + "']"),WebUI.isElementDisplayed(By.xpath("//table[@id='xin_table']//td[1][normalize-space()='" + titleProject + "']")),"Project not found in table");
    }

    public void verifyUploadAttachSuccess(String fileName) {
        WebUI.sleep(4);// Lí do: do page đợi ẩn toast message và refresh page
        WebUI.clickElement(tabAttachFile);
        By hyperlink = By.xpath("//h6[@class='mb-0' and normalize-space(text()[1])='" + fileName + "']/parent::a/following-sibling::div//a[contains(@href,'download')]");
        WebUI.verifyFileUpLoaded(hyperlink, "File not uploaded");
    }

    public void verifyDetailProjectAfterUpdate(String update_endDate) {
        WebUI.waitForElementVisible(textEndDate);
        WebUI.softVerifyEqual(WebUI.getElementText(textEndDate).trim(), update_endDate, "End date not match with expected");
        WebUI.assertAll();
    }

    private void handleCompleted() {
        int actual_TotalProjectsCompleted = getTotalProjectsCompleted();
        int expect_TotalProjectsCompleted = TotalProjectsCompletedBefore + 1;
        WebUI.softVerifyEqual(actual_TotalProjectsCompleted, expect_TotalProjectsCompleted, "Total project completed not match with expected");
        int actual_TotalProjectsNotStarted = getTotalProjectsNotStarted();
        int expect_TotalProjectsNotStarted = TotalProjectsNotStartedBefore - 1;
        WebUI.softVerifyEqual(actual_TotalProjectsNotStarted, expect_TotalProjectsNotStarted, "Total project notstarted not match with expected");
    }

    private void handleInProgress() {
        int actual_TotalProjectsInProgress = getTotalProjectsInProgress();
        int expect_TotalProjectsInProgress = TotalProjectsInProgressBefore + 1;
        WebUI.softVerifyEqual(actual_TotalProjectsInProgress, expect_TotalProjectsInProgress, "Total project inprogress not match with expected");
        int actual_TotalProjectsNotStarted = getTotalProjectsNotStarted();
        int expect_TotalProjectsNotStarted = TotalProjectsNotStartedBefore - 1;
        WebUI.softVerifyEqual(actual_TotalProjectsNotStarted, expect_TotalProjectsNotStarted, "Total project notstarted not match with expected");
    }

    private void handleOnHold() {
        int actual_TotalProjectsOnHold = getTotalProjectsOnHold();
        int expect_TotalProjectsOnHold = TotalProjectsOnHoldBefore + 1;
        WebUI.softVerifyEqual(actual_TotalProjectsOnHold, expect_TotalProjectsOnHold, "Total project onhold not match with expected");
        int actual_TotalProjectsNotStarted = getTotalProjectsNotStarted();
        int expect_TotalProjectsNotStarted = TotalProjectsNotStartedBefore - 1;
        WebUI.softVerifyEqual(actual_TotalProjectsNotStarted, expect_TotalProjectsNotStarted, "Total project notstarted not match with expected");
    }

    private void handleCancled() {
        int actual_TotalProjectsNotStarted = getTotalProjectsNotStarted();
        int expect_TotalProjectsNotStarted = TotalProjectsNotStartedBefore + 1;
        WebUI.softVerifyEqual(actual_TotalProjectsNotStarted, expect_TotalProjectsNotStarted, "Total project notstarted not match with expected");
    }

    public void verifyStatusProjectAfterUpdate(String status, String priority, String progress) {
        WebUI.refreshPage();
        String actual_status = WebUI.getElementText(By.xpath("//div[@class='br-current-rating' and normalize-space()='" + status + "']"));
        WebUI.softVerifyEqual(actual_status, status, "Status not match with expected");
        String actual_priority = WebUI.getElementText(selectPriority);
        WebUI.softVerifyEqual(actual_priority, priority, "Priority not match with expected");
        String actual_progress = WebUI.getElementText(sliderValue);
        WebUI.softVerifyEqual(actual_progress, progress, "Progress not match with expected");
        WebUI.backToPreviousPage();
        switch (status) {
            case "Completed":
                handleCompleted();
                break;
            case "In Progress":
                handleInProgress();
                break;
            case "On Hold":
                handleOnHold();
                break;
            case "Cancelled":
                handleCancled();
                break;
            default:
                WebUI.logConsole(status + " is not exist");
                break;
        }
        WebUI.assertAll();
    }

    public void verifyProjectNotDisplayedAfterDelete(String title) {
        WebUI.refreshPage();
        int actual_TotalProjectsNotStarted = getTotalProjectsNotStarted();
        int expect_TotalProjectsNotStarted = TotalProjectsNotStartedBefore - 1;
        WebUI.softVerifyEqual(actual_TotalProjectsNotStarted, expect_TotalProjectsNotStarted, "Total project notstarted not match with expected");
        searchTitle(title);
        List<WebElement> list_project = WebUI.getWebElements(By.xpath("//table//td[1][normalize-space()='" + title + "']"));
        WebUI.verifyNotDisplay(list_project, title, title + " still display in table after delete");
        WebUI.assertAll();
    }
}
