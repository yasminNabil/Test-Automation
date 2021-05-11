package actions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import pages.HomePage;

import java.io.File;
import java.io.IOException;

public class BrowserActions {
    public static WebDriver driver;
    private HomePage homePage;

    public void initializeWebDriver(BrowserType browserType) throws Exception {

        switch (browserType) {
            case CHROME -> {/* Initialize Tests */
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            case FIREFOX -> {

                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            case IE -> {

                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
            }

            case EDGE -> {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }

            case HEADLESS -> {
               /* WebDriverManager.phantomjs().setup(); */
                DesiredCapabilities dCaps = new DesiredCapabilities() ;
                dCaps.setJavascriptEnabled(true);
                dCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"resources/phantomjs.exe");
                String []phantomJSArgs = {"--web-security=no", "--ignore-ssl-errors=yes"} ;
                dCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,phantomJSArgs);
                driver = new PhantomJSDriver(dCaps) ;
            }

            default -> {
                throw new Exception("Browser type mismatch !");
            }
        }

        homePage = new HomePage();
        homePage.navigateToHome();
    }

    public void maximizeScreen() {
        driver.manage().window().maximize();
    }

    public void closeDriver() {
        driver.close();
    }

    public static BrowserType geBrowserType(String browser) throws Exception {
        if (browser.equalsIgnoreCase("chrome") || browser.equalsIgnoreCase("chrome-headless")) {
            return BrowserType.CHROME;
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            return BrowserType.FIREFOX;
        }
        else if (browser.equalsIgnoreCase("ie")) {
            return BrowserType.IE;
        }
        else if (browser.equalsIgnoreCase("edge")) {
            return BrowserType.EDGE;
        }
        else if (browser.equalsIgnoreCase("headless")) {
             return
                     BrowserType.HEADLESS ;
        } else
            throw new Exception("Not Valid Browser Type !!");

    }

    public static void recordFailure(ITestResult testResult, WebDriver driver) {
        try {
            if (ITestResult.FAILURE == testResult.getStatus()) {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.moveFile(scrFile, new File("screenshots/" + testResult.getName() + ".png"));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void goBack(WebDriver t_driver) {
        t_driver.navigate().back();
    }

    public static void goForward(WebDriver t_driver) {
        t_driver.navigate().forward();
    }

    public static void refresh(WebDriver t_driver) {
        t_driver.navigate().refresh();
    }

    public enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE,
        IE,
        HEADLESS
    }


}
