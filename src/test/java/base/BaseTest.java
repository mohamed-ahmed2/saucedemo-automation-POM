package base;

//import io.github.bonigarcia.wdm.WebDriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.commons.io.FileUtils;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import pages.LoginPage;
import utils.ConfigReader;
import utils.Reporter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;
    public LoginPage loginPage;
    @BeforeSuite
    public void suiteSetup() {
        // بننادي التقرير مرة واحدة بس قبل ما كل التيستات تبدأ
        extent = Reporter.getInstance();
    }
        @BeforeMethod
        public  void setUp(Method method) {
            test = extent.createTest(method.getName());
            String browser = ConfigReader.getProperty("browser").toLowerCase();

            switch (browser) {
                case "chrome":
                    ChromeOptions chromeOptions = getChromeOptions();
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "firefox":
                    //  FirefoxOptions
                    FirefoxOptions ffOptions = getFirefoxOptions();
                    driver = new FirefoxDriver(ffOptions);
                    break;

                case "edge":
                    //  EdgeOptions
                    EdgeOptions edgeOptions = getEdgeOptions();
                    driver = new EdgeDriver(edgeOptions);
                    break;

                default:
                    throw new RuntimeException("Browser isn't supported" + browser);
            }

            driver.manage().window().maximize();



            driver.get(ConfigReader.getProperty("url"));
            loginPage = new LoginPage(driver);


    }

    private static @NonNull ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-features=PasswordLeakDetection");
        chromeOptions.addArguments("--incognito");
        Map<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        return chromeOptions;
    }

    private static @NonNull EdgeOptions getEdgeOptions(){
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("-inprivate");
        return edgeOptions;
    }


    private static @NonNull FirefoxOptions getFirefoxOptions(){
        FirefoxOptions ffOptions = new FirefoxOptions();
        ffOptions.addArguments("-private"); //  incognito
            return ffOptions;
    }

    @AfterMethod
        public void tearDown(ITestResult result) {
            if (result.getStatus() == ITestResult.FAILURE) {
                    // Take a screenshot on failure
                String path = captureScreenshot(result.getName());
                test.fail("Test Failed", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
                test.fail(result.getThrowable()); // Add Error message
            }
            else if (result.getStatus() == ITestResult.SUCCESS) {
                if (result.getMethod().getDataProviderMethod() == null) {
                    test.pass("Test Passed Successfully!");}
                    }
            if (driver != null) {
                driver.quit();
            }
        }

    @AfterSuite
    public void suiteTearDown() {

        extent.flush();
    }

    public String captureScreenshot(String testName) {
       // file name
        String fileName = testName + "_" + System.currentTimeMillis() + ".png";
        // file path
        String fullPath = "reports/screenshots/" + fileName;

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            // Full path
            FileUtils.copyFile(srcFile, new File(fullPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "screenshots/" + fileName;
    }
    }

