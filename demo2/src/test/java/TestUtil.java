import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtil {

    public WebDriver driver;
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest logger;
    public Logger LOG = LoggerFactory.getLogger(TestUtil.class);


    public void extentReportInitialize(String extentReportPath, String reportTitle, String reportName,
                                       String hostname, String environment,
                                       String testSuite) {

        htmlReporter = new ExtentHtmlReporter(extentReportPath.concat("/extentReport.html"));
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", hostname);
        extent.setSystemInfo("Environment", environment);
        extent.setSystemInfo("Test Suite", testSuite);
        htmlReporter.config().setDocumentTitle(reportTitle);
        htmlReporter.config().setReportName(reportName);
        htmlReporter.config().setTheme(Theme.STANDARD);
    }

    public ExtentHtmlReporter getHtmlReporter() {
        return this.htmlReporter;
    }

    public ExtentTest getExtentLogger() {
        return this.logger;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public String takescreenshots(String scenario) {
        //Copy the file to a location and use try catch block to handle exception
        String screenshotPath = "";
        try {

            String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // "C:\\Users\\njoya\\Downloads\\Screenshots
            String destination = "C:\\Users\\njoya\\Downloads\\Screenshots" + scenario + dateName + ".png";
            FileUtils.copyFile(source, new File(destination));
            screenshotPath = destination;
            return screenshotPath;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return scenario;

    }

    public void extentTestInitialize(String testName) {
        logger = extent.createTest(testName);

    }

    public void closeExtentReport() {
        extent.flush();
    }

    public void addtoReport(String status, String stepName, String directoryName) {
        try {
            String image = takescreenshots(directoryName);
            if (status.equalsIgnoreCase("Pass")) {
                //logger is extentTest object
                logger.log(Status.PASS, stepName, MediaEntityBuilder.createScreenCaptureFromBase64String(image).build());

            } else if (status.equalsIgnoreCase("Fail")) {
                logger.log(Status.FAIL, stepName, MediaEntityBuilder.createScreenCaptureFromBase64String(image).build());

            }

        } catch (Exception e) {

            LOG.info(e.getMessage());
        }
    }

}


