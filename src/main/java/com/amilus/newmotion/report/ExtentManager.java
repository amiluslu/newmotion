package com.amilus.newmotion.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * ExtentReports instance created by calling createExtentReports() method from ExtentManager.
 * */
public class ExtentManager
{
    public static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports createExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("./reports/TestAutomation-Results-Report.html");
        reporter.config().setReportName("Newmotion API Test Report");
        extentReports.attachReporter(reporter);
        return extentReports;
    }
}
