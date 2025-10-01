package com.longtester.HRM.pages;

import com.longtester.helpers.PropertiesHelper;
import com.longtester.helpers.SystemHelper;
import com.longtester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.List;

public class ClientPage {
    // Add client form
    By buttonAddNewClient = By.xpath("//a[normalize-space()='Add New']");
    By inputFirstName = By.xpath("//input[@placeholder='First Name']");
    By inputLastName = By.xpath("//input[@placeholder='Last Name']");
    By inputPassword = By.xpath("//input[@placeholder='Password']");
    By inputContactNumber = By.xpath("//input[@placeholder='Contact Number']");
    By selectGender = By.xpath("//label[normalize-space()='Gender']/following::span[contains(@id,'gender')]");
    By inputEmail = By.xpath("//input[@placeholder='Email']");
    By inputUsername = By.xpath("//input[@placeholder='Username']");
    By uploadAttachment = By.xpath("//input[@name='file']");
    By buttonSave = By.xpath("//span[normalize-space()='Save']/parent::button");
    By avatarClient = By.xpath("//div[@class='certificated-badge']/preceding-sibling::img");
    // Personal Information form
    By selectCountry = By.xpath("//label[@for='country']/following::span[contains(@id,'country')]");
    By inputSearchCountry = By.xpath("//input[@type='search' and @role='searchbox']");
    By buttonSubmit = By.xpath("//div[@class='card-body']//button[@type='submit']");

    By alertSuccess = By.xpath("//div[contains(@class,'toast-success')]");
    By inputSearch = By.xpath("//input[@type='search']");
    By buttonViewDetail = By.xpath("//button/parent::a[contains(@href,'view-client')]");
    By buttonDeleteClient = By.xpath("//button/parent::span[@data-original-title='Delete']");
    By buttonConfirmDelete = By.xpath("//div[@class='modal-content']//span[normalize-space()='Confirm']");

    private void clickAddNewClient() {
        WebUI.waitForElementVisible(buttonAddNewClient);
        WebUI.clickElement(buttonAddNewClient);
    }

    public void addNewClient(String firstName, String lastName, String password, String contactNumber,
                             String gender, String email, String username, String filepath) {
        clickAddNewClient();
        WebUI.setText(inputFirstName, firstName);
        WebUI.setText(inputLastName, lastName);
        WebUI.setText(inputPassword, password);
        WebUI.setText(inputContactNumber, contactNumber);
        WebUI.clickElement(selectGender);
        By optionGender = By.xpath("//li[normalize-space()='" + gender + "']");
        WebUI.clickElement(optionGender);
        WebUI.setText(inputEmail, email);
        WebUI.setText(inputUsername, username);
        WebUI.uploadFile(uploadAttachment, SystemHelper.getCurrentDir() + filepath);
        WebUI.clickElement(buttonSave);
    }

    public void editClient(String search_username, String country) {
        searchClient(search_username);
        clickViewDetail();
        WebUI.clickElement(selectCountry);
        WebUI.setText(inputSearchCountry, country);
        By countryName = By.xpath("//ul[normalize-space()='" + country + "']");
        WebUI.waitForElementVisible(countryName);
        WebUI.clickElement(countryName);
        WebUI.clickElement(buttonSubmit);
    }

    public void deleteClient(String username) {
        searchClient(username);
        clickDeleteClient();
        WebUI.waitForElementVisible(buttonConfirmDelete);
        WebUI.clickElement(buttonConfirmDelete);
    }

    private void searchClient(String username) {
        WebUI.waitForElementVisible(inputSearch);
        WebUI.setText(inputSearch, username);
        WebUI.sleep(2);
    }

    private void clickViewDetail() {
        WebUI.hoverMouse(By.xpath("//tbody/tr[@role='row']"));
        WebUI.waitForElementVisible(buttonViewDetail);
        WebUI.clickElement(buttonViewDetail);
    }

    private void clickDeleteClient() {
        WebUI.hoverMouse(By.xpath("//tbody/tr[@role='row']"));
        WebUI.waitForElementVisible(buttonDeleteClient);
        WebUI.clickElement(buttonDeleteClient);
    }

    public void verifyAddNewClientSuccess(String username) {
        WebUI.waitForElementVisible(alertSuccess);
        String actual_text = WebUI.getElementText(alertSuccess);
        WebUI.softVerifyEqual(actual_text, "Client added.", actual_text + " not match with expected");
        searchClient(username);
        String actual_textUsername = WebUI.getElementText(By.xpath("//table//td[2]"));
        WebUI.verifyEqual(actual_textUsername, username, username + "Username in table not match with expected");
        WebUI.assertAll();
    }

    public void verifyDetailClientAfterAddNew(String firstName, String lastName, String contactNumber,
                                              String gender, String email, String username) {
        clickViewDetail();
        String actual_firtname = WebUI.getElementAttribute(inputFirstName, "value");
        WebUI.softVerifyEqual(actual_firtname, firstName, "First name not match with expected");
        String actual_lastname = WebUI.getElementAttribute(inputLastName, "value");
        WebUI.softVerifyEqual(actual_lastname, lastName, "Last name not match with expected");
        String actual_contactnumber = WebUI.getElementAttribute(inputContactNumber, "value");
        WebUI.softVerifyEqual(actual_contactnumber, contactNumber, "Contact number not match with expected");
        String actual_email = WebUI.getElementAttribute(inputEmail, "value");
        WebUI.softVerifyEqual(actual_email, email, "Email not match with expected");
        String actual_username = WebUI.getElementAttribute(inputUsername, "value");
        WebUI.softVerifyEqual(actual_username, username, "Username not match with expected");
        String actual_gender = WebUI.getElementAttribute(selectGender, "title");
        WebUI.softVerifyEqual(actual_gender.trim(), gender, "Gender not match with expected");
        WebUI.verifyImageUpLoaded(avatarClient,"Avatar client not loaded");
        WebUI.assertAll();
    }

    public void verifyDetailClientAfterUpdate(String country) {
        WebUI.refreshPage();
        String actual_country = WebUI.getElementAttribute(selectCountry, "title");
        WebUI.softVerifyEqual(actual_country.trim(), country, "Country not match with expected");
        WebUI.assertAll();
    }

    public void verifyClientNotDisplayedAfterDelete(String username) {
        searchClient(username);
        WebUI.refreshPage();
        searchClient(username);
        List<WebElement> list_client = WebUI.getWebElements(By.xpath("//table//td[2][normalize-space()='" + username + "']"));
        WebUI.verifyNotDisplay(list_client, username, username + " still display in table after delete");
    }

}
