package com.longtester.HRM.pages;

import com.longtester.keywords.WebUI;
import org.openqa.selenium.By;

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
    public int getTotalProjectsNotStarted(){
        String total_text = WebUI.getElementText(totalProjectsNotStarted);
        return Integer.parseInt(total_text);
    }
    public void addNewProject(String title, String client, String startDate, String endDate, String summary) {
        clickAddNewProject();
        WebUI.setText(inputTitle, title);
        WebUI.clickElement(selectClient);
        WebUI.setText(inputSearchClient, client);
        By optionClient = By.xpath("//li[normalize-space()='" + client + "']");
        WebUI.clickElement(optionClient);
        WebUI.clickElement(inputStartDate);
        //WebUI.clickElement(buttonSave);

    }

}
