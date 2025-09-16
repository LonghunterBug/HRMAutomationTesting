package com.longtester.HRM.pages;

import com.longtester.driver.DriverManager;
import com.longtester.helpers.DateHelper;
import com.longtester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProjectPage {
    // Add project form
    By buttonAddNewProject = By.xpath("//a[normalize-space()='Add New']");
    By inputTitle = By.xpath("//input[@placeholder='Title']");
    By selectClient = By.xpath("//label[@for='client_id']/following::span[contains(@id,'client')]");
    By inputSearchClient = By.xpath("//input[@type='search' and @role='searchbox' and contains(@aria-activedescendant,'client')]");
    By inputStartDate = By.xpath("//input[@placeholder='Start Date']");
    By inputEndDate = By.xpath("//input[@placeholder='End Date']");
    By inputSummary = By.xpath("//textarea[@id='summary']");
    By buttonSave = By.xpath("//span[normalize-space()='Save']/parent::button");
    // Date picker form
    By currentMonth = By.xpath("//div[@class='dtp animated fadeIn']//div[contains(@class,'actual-month')]");
    By iconForwardMonth = By.xpath("//div[@class='dtp animated fadeIn']//a[@class='dtp-select-month-after']");
    By iconBackMonth = By.xpath("//div[@class='dtp animated fadeIn']//a[@class='dtp-select-month-before']");
    By currentYear = By.xpath("//div[@class='dtp animated fadeIn']//div[contains(@class,'actual-year')]");
    By iconForwardYear = By.xpath("//div[@class='dtp animated fadeIn']//a[@class='dtp-select-year-after']");
    By iconBackYear = By.xpath("//div[@class='dtp animated fadeIn']//a[@class='dtp-select-year-before']");
    By currentDate = By.xpath("//div[@class='dtp animated fadeIn']//table[contains(@class,'picker-days')]//td");
    By buttonOK = By.xpath("//div[@class='dtp animated fadeIn']//div[@class='dtp-buttons']/button[text()='OK']");

    By totalProjectsNotStarted = By.xpath("//span[normalize-space()='Not Started']/ancestor::div/h2");
    By alertAddNewProjectSuccess = By.xpath("//div[contains(@class,'toast-success')]");
    By inputSearch = By.xpath("//input[@type='search' and  contains(@class,'form-control')]");
    By buttonViewDetail = By.xpath("//button/parent::a[contains(@href,'project-detail')]");
    By buttonDeleteClient = By.xpath("//button/parent::span[@data-original-title='Delete']");
    By buttonConfirmDelete = By.xpath("//div[@class='modal-content']//span[normalize-space()='Confirm']");

    private void clickAddNewProject() {
        WebUI.waitForElementVisible(buttonAddNewProject);
        WebUI.clickElement(buttonAddNewProject);
    }
    private void searchTitle(String title) {
        WebUI.waitForElementVisible(inputSearch);
        WebUI.setText(inputSearch, title);
        WebUI.sleep(2);
    }

    public int getTotalProjectsNotStarted() {
        String total_text = WebUI.getElementText(totalProjectsNotStarted);
        return Integer.parseInt(total_text);
    }

    private void selectDate(String date) {
        LocalDate getdate = DateHelper.parseDate(date);
        String expect_day = String.format("%02d", getdate.getDayOfMonth());
        String expect_month = String.valueOf(getdate.getMonth().toString().substring(0,3).toUpperCase());
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
            }
            else {
                DriverManager.getDriver().findElement(iconForwardMonth).click();
            }
        }
        WebUI.logConsole("Select month: "+expect_month);
        // Handle year
        while (true) {
            String actual_year = DriverManager.getDriver().findElement(currentYear).getText();
            if (actual_year.equalsIgnoreCase(expect_year)) {
                break;
            }
            if (Integer.parseInt(expect_year) < Integer.parseInt(actual_year))//So sánh nếu expect year < actual year
            {
                DriverManager.getDriver().findElement(iconBackYear).click();
            }
            else {
                DriverManager.getDriver().findElement(iconForwardYear).click();
            }
        }
        WebUI.logConsole("Select year: "+expect_year);
        List<WebElement> allValidDate = WebUI.getWebElements(currentDate);
        for(WebElement e:allValidDate){
            if(e.getText().equalsIgnoreCase(expect_day)){
                e.click();
                break;
            }
        }
        WebUI.logConsole("Select date: "+expect_day);
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
        WebUI.setText(inputSummary,summary);
        WebUI.clickElement(buttonSave);
    }
    public void verifyAddProjectSuccess(){
        int actual_TotalProjectsNotStarted = getTotalProjectsNotStarted();//105
        WebUI.refreshPage();
        int expect_TotalProjectsNotStarted = actual_TotalProjectsNotStarted + 1;//106
        WebUI.softVerifyEqual(getTotalProjectsNotStarted(),expect_TotalProjectsNotStarted,"Total project notstarted not match with expected");
    }
}
