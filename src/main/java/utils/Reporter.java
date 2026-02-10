package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporter {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/TestReport.html");

            spark.config().setTheme(Theme.STANDARD);
            spark.config().setReportName("SauceDemo Automation Results");
            spark.config().setDocumentTitle("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Tester", "Mohamed");
            extent.setSystemInfo("Environment", "Beta");
            extent.setSystemInfo("Browser", "Chrome");
        }
        return extent;
    }
}