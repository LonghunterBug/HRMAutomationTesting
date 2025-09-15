package com.longtester.dataprovider;

import com.longtester.helpers.ExcelHelper;
import org.testng.annotations.DataProvider;

public class DataProviderFactory {
    String data_path = "src/test/resources/testdata/HRM.xlsx";

    @DataProvider(name = "data_LoginSuccess_registered_account")
    public Object[][] dataLoginSuccess() {
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] logindata = excelHelper.getExcelData(data_path, "Client", "TC01");
        Object[][] result = new Object[logindata.length][2];
        for (int i = 0; i < logindata.length; i++) {
            result[i][0] = logindata[i][6]; //username
            result[i][1] = logindata[i][2]; //password
        }
        return result;
    }

    @DataProvider(name = "data_LoginFail_without_username")
    public Object[][] dataLoginFail1() {
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] logindata = excelHelper.getExcelData(data_path, "Login", "TC03");
        return logindata;
    }

    @DataProvider(name = "data_LoginFail_without_password")
    public Object[][] dataLoginFail2() {
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] logindata = excelHelper.getExcelData(data_path, "Login", "TC02");
        return logindata;
    }
    @DataProvider(name = "data_AddNewClient")
    public Object[][] dataAddNewClient() {
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] clientdata = excelHelper.getExcelData(data_path, "Client", "TC01");
        Object[][] result = new Object[clientdata.length][8];
        for (int i = 0; i < clientdata.length; i++) {
            result[i][0] = clientdata[i][0]; //firstName
            result[i][1] = clientdata[i][1]; //lastName
            result[i][2] = clientdata[i][2]; //password
            result[i][3] = clientdata[i][3]; //contactNumber
            result[i][4] = clientdata[i][4]; //gender
            result[i][5] = clientdata[i][5]; //email
            result[i][6] = clientdata[i][6]; //username
            result[i][7] = clientdata[i][7]; //filepath
        }
        return result;
    }
    @DataProvider(name = "data_editClient")
    public Object[][] dataEditClient() {
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] clientdata = excelHelper.getExcelData(data_path, "Client", "TC02");
        return clientdata;
    }
    @DataProvider(name = "data_DeleteClient")
    public Object[][] dataDeleteClient() {
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] clientdata = excelHelper.getExcelData(data_path, "Client", "TC03");
        Object[][] result = new Object[clientdata.length][8];
        for (int i = 0; i < clientdata.length; i++) {
            result[i][0] = clientdata[i][0]; //firstName
            result[i][1] = clientdata[i][1]; //lastName
            result[i][2] = clientdata[i][2]; //password
            result[i][3] = clientdata[i][3]; //contactNumber
            result[i][4] = clientdata[i][4]; //gender
            result[i][5] = clientdata[i][5]; //email
            result[i][6] = clientdata[i][6]; //username
            result[i][7] = clientdata[i][7]; //filepath
        }
        return result;
    }
    @DataProvider(name = "data_AddNewProject")
    public Object[][] dataAddNewProject() {
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] projectdata = excelHelper.getExcelData(data_path, "Project", "TC01");
        return projectdata;
    }

}
