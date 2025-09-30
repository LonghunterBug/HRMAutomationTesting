package com.longtester.listeners;

import com.longtester.helpers.CaptureHelper;
import com.longtester.helpers.PropertiesHelper;
import com.longtester.mail.EmailSender;
import com.longtester.reports.AllureManager;
import com.longtester.utils.LogUtils;
import org.testng.*;

import java.io.IOException;

public class TestListener implements ISuiteListener,ITestListener {
    public static int count_totalTCs;
    static int count_passedTCs;
    static int count_skippedTCs;
    static int count_failedTCs;

    @Override
    public void onStart(ISuite arg0) {
        LogUtils.info("********** RUN STARTED **********");
    }

    @Override
    public void onFinish(ISuite arg0) {
        LogUtils.info("********** RUN FINISHED **********");
        LogUtils.info("Test Summary:");
        LogUtils.info("📊 Total TCs: " + count_totalTCs);
        LogUtils.info("✅ Passed TCs: " + count_passedTCs);
        LogUtils.info("❌ Failed TCs: " + count_failedTCs);
        LogUtils.info("⚠ Skipped TCs: " + count_skippedTCs);
        if (PropertiesHelper.getValue("SEND_EMAIL_TO_USERS").equalsIgnoreCase("yes")) {
            ProcessBuilder pb = new ProcessBuilder(
                    "D:\\allure-2.35.1\\bin\\allure.bat", "generate",
                    "--single-file",
                    "target/allure-results",
                    "--clean",
                    "-o", "target/allure-report"
            );
            try {
                Process process = pb.start();// chạy câu lệnh generate report index.html
                int check = process.waitFor();
                if(check == 0){
                    LogUtils.info("Allure report generated successfully!");
                }else{
                    LogUtils.info("Failed to generate Allure report!");
                }
            } catch (IOException | InterruptedException e) {
                LogUtils.error("❌ Error while generating Allure report: " + e);
            }
            EmailSender.sendMail(System.getProperty("os.name"),PropertiesHelper.getValue("BROWSER"),
                    count_totalTCs,count_passedTCs,count_failedTCs,count_skippedTCs);
            LogUtils.info("📧 Sending result email to users successful");
        }
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        LogUtils.info("Test case: " + iTestResult.getMethod().getMethodName() + " is starting...");
        count_totalTCs++;
        if(PropertiesHelper.getValue("RECORD_VIDEO").equalsIgnoreCase("yes")){
            CaptureHelper.startRecord(iTestResult.getMethod().getMethodName());
        }
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LogUtils.info("✅ Test case: " + iTestResult.getMethod().getMethodName() + " is passed.");
        count_passedTCs++;
        if(PropertiesHelper.getValue("RECORD_VIDEO").equalsIgnoreCase("yes")){
            CaptureHelper.stopRecord();
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        LogUtils.error("❌ Test case: " + iTestResult.getMethod().getMethodName() + " is failed.");
        LogUtils.error("📄 Reason: " + iTestResult.getThrowable());
        count_failedTCs++;
        if(PropertiesHelper.getValue("RECORD_VIDEO").equalsIgnoreCase("yes")){
            CaptureHelper.stopRecord();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LogUtils.warn("⚠ Test case: " + iTestResult.getMethod().getMethodName() + " is skipped.");
        LogUtils.warn("📄 Reason: " + iTestResult.getThrowable());
        count_skippedTCs++;
        if(PropertiesHelper.getValue("RECORD_VIDEO").equalsIgnoreCase("yes")){
            CaptureHelper.stopRecord();
        }
    }
}

