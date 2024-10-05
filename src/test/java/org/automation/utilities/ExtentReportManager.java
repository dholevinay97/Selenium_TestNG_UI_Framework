package org.automation.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ExtentReportManager implements ITestListener {
    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private ExtentTest test;
    private ScenarioContext scenarioContext;
    private EnvConfigFileReader envConfigFileReader;
    private String repName = "";

    /**
     * Initializes the Extent Reports and the Scenario Context at the start of the test.
     */
    public void onStart(ITestContext testContext) {
        scenarioContext = ScenarioContext.getInstance();
        envConfigFileReader = new EnvConfigFileReader();
        String reportingType = envConfigFileReader.getReportingType();
        scenarioContext.setContext("reportingType", reportingType);

        String date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        createDirectoryIfNotExists(".\\reports\\" + date);
        createDirectoryIfNotExists(".\\screenshots\\" + date);

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        scenarioContext.setContext("timeStamp", timeStamp);

        repName += "Test_Report_" + scenarioContext.getContext("timeStamp") + ".html";

        String env = envConfigFileReader.getEnv();
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + date + "\\" + repName);
        sparkReporter.config().setDocumentTitle("My_UI_Automation");
        sparkReporter.config().setReportName("Test Case Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Demo Suite");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", env);
    }

    /**
     * Logs the successful test execution to the Extent Report.
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        logTestResult(result, Status.PASS, "Test Passed");
    }

    /**
     * Logs the failed test execution and captures a screenshot.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        logTestResult(result, Status.FAIL, "Test Failed");
        captureScreenshot(result);
        String str1 = result.getMethod().getGroups()[0] + "_";
        String str = scenarioContext.getContext("timeStamp").toString();
        String date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        File file = new File(".\\reports\\" + str1 + result.getName() + "_" + str + ".png");
        file.renameTo(new File(".\\reports\\" + date + "\\" + str1 + result.getName() + "_" + str + ".png"));
        file.delete();
    }

    /**
     * Logs the skipped test execution to the Extent Report.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        logTestResult(result, Status.SKIP, "Test Skipped");
    }

    /**
     * Finalizes the Extent Report and sends test results via webhook notifications.
     */
    @Override
    public void onFinish(ITestContext testContext) {
        extent.flush();
        String date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        File f = new File(".\\reports\\" + date + "\\" + repName);
        File f1 = new File(".\\reports\\Test_Report.html");
        try {
            File backup = getNonExistingTempFile(f1);
            FileUtils.copyFile(f1, backup);
            FileUtils.copyFile(f, f1);
            if (!backup.delete()) {
                throw new IOException("Failed to delete " + backup.getName());
            }
        } catch (Exception e) {
            System.err.println("Error moving file: " + e.getMessage());
        }
//        String env = envConfigFileReader.getEnv();
//        scenarioContext = ScenarioContext.getInstance();
//
//        String str1 = sparkReporter.getFile().toString();
//        str1 = str1.substring(1);
//        String str2 = System.getProperty("user.dir") + str1;
//        File file = new File(str2);
//        try {
//            Desktop.getDesktop().browse(file.toURI());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public static File getNonExistingTempFile(File inputFile) {
        File tempFile = new File(inputFile.getParentFile(), inputFile.getName() + "_temp");
        if (tempFile.exists()) {
            return getNonExistingTempFile(tempFile);
        } else {
            return tempFile;
        }
    }

    private void createDirectoryIfNotExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private void logTestResult(ITestResult result, Status status, String message) {
        test = extent.createTest(result.getMethod().getDescription());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());

        List<String> reporterMessages = Reporter.getOutput(result);
        for (String reporterMessage : reporterMessages) {
            test.info(reporterMessage);
        }
        test.log(status, message);
        if (status == Status.FAIL) {
            test.log(status, result.getThrowable().getMessage());
        }
    }

    private void captureScreenshot(ITestResult result) {
        String timeStamp = scenarioContext.getContext("timeStamp").toString();
        String date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        String group = result.getMethod().getGroups()[0] + "_";

        WebDriver driver = (WebDriver) scenarioContext.getContext(Thread.currentThread().getName() + "_webdriver");
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(screenshotFile, new File(".\\reports\\" + group + result.getName() + "_" + timeStamp + ".png"));
            FileHandler.copy(screenshotFile, new File(".\\screenshots\\" + date + "\\" + group + result.getName() + "_" + timeStamp + ".png"));
            test.addScreenCaptureFromPath(group + result.getName() + "_" + timeStamp + ".png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
