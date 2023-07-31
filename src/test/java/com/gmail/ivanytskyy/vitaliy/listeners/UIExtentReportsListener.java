package com.gmail.ivanytskyy.vitaliy.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.service.ExtentTestManager;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 31/07/2023
 */
public abstract class UIExtentReportsListener extends ExtentITestListenerClassAdapter {
    @Override
    public synchronized void onTestFailure(ITestResult result) {
        super.onTestFailure(result);
        ExtentTest test = ExtentTestManager.getTest(result);
        File file = getScreenshot(result);
        try {
            test.addScreenCaptureFromBase64String(Base64
                    .getEncoder()
                    .encodeToString(FileUtils.readFileToByteArray(file)), "Failed test");
            file.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        super.onTestSkipped(result);
        ExtentTest test = ExtentTestManager.getTest(result);
        File file = getScreenshot(result);
        try {
            test.addScreenCaptureFromBase64String(Base64
                    .getEncoder()
                    .encodeToString(FileUtils.readFileToByteArray(file)), "Skipped test");
            file.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected abstract File getScreenshot(ITestResult iTestResult);
}