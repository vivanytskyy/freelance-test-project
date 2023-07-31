package com.gmail.ivanytskyy.vitaliy.listeners;

import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;

/**
 * @author Vitaliy Ivanytskyy
 * @version 1.00
 * @date 31/07/2023
 */
public class UISelenideExtentReportsListener extends UIExtentReportsListener {
    static {
        System.setProperty("extent.reporter.html.start", "true");
        System.setProperty("extent.reporter.html.out", "target/extentReport/UISelenideExtentHtml.html");
    }

    protected File getScreenshot(ITestResult iTestResult){
        File fileForCopy = new File(iTestResult.getName() + ".jpg");
        File screenshotFile = ((TakesScreenshot) WebDriverRunner.getWebDriver())
                .getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, fileForCopy);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileForCopy;
    }
}