package com.amilus.newmotion.base;


import com.amilus.newmotion.report.ExtentManager;
import com.amilus.newmotion.report.ExtentTestManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Testbase class for JUnit framework methods.
 * Before and After methods for initializing & generating a well described HTML report.
 * BeforeAll --> Creates HTML Report file
 * BeforeTestExecuton --> Creates Test informations into HTML file.
 * AfterTestExecution --> Puts Test Results into HTML file.
 * AfterAll --> Finalize and generates HTML Report file.
 * */
public class TestBase implements BeforeAllCallback, BeforeTestExecutionCallback, AfterAllCallback, AfterTestExecutionCallback
{
    static ExtentReports reports;
    static ExtentTest test;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        reports = ExtentManager.createExtentReports();
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        if(context.getDisplayName().contains(context.getTestMethod().get().getName())){
            test = ExtentTestManager.startTest(reports, context.getDisplayName());
            test.info("Test Name: "+context.getDisplayName() + " is started");
        }
        else {
            test = ExtentTestManager.startTest(reports,context.getTestMethod().get().getName()+"-"+context.getDisplayName());
            test.info("Test Name: "+context.getTestMethod().get().getName()+"-"+context.getDisplayName() + " is started");
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (!context.getExecutionException().isPresent()) {
            test.pass("Test passed...");
        } else {
            test.fail(context.getExecutionException().get().getLocalizedMessage());
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        reports.flush();
    }
}