package levelset_tests;

import actions.BrowserActions;
import actions.UI_Actions;
import files.ExcelFileManager;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.HomePage;
import pages.SelectDocumentPage;

import java.io.File;
import java.io.IOException;

@Test
public class LevelSetTests {


    private BrowserActions browserActions;
    SelectDocumentPage selectDocumentPage;
    private HomePage homePage;


    @BeforeClass
    @Parameters({"browser"})
    public void setUp(@Optional("Chrome") String browserName) {
        try {
            browserActions = new BrowserActions();
            browserActions.initializeWebDriver(BrowserActions.geBrowserType(browserName));
            browserActions.maximizeScreen();
            homePage = new HomePage();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            Assert.fail();
        }
    }

    @BeforeMethod
    public void goHomePage() {
        try {
            homePage.navigateToHome();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            Assert.fail();
        }

    }

    @Test(dataProvider = "readExcelSheet")
    public void testFreeDocument(String documentName, String documentPrice) throws Exception {
        homePage.navigateToHome();
        selectDocumentPage = homePage.pressCreateDocument();
        selectDocumentPage.selectDocument(documentName);
        Assert.assertTrue(documentPrice.contains("Free") && selectDocumentPage.checkFreePrice(documentName));
    }

    @AfterMethod

    public void getFailureScreenShot(ITestResult result){
        BrowserActions.recordFailure(result , BrowserActions.driver);
    }


    @AfterClass
    public void tearDown() {
        browserActions.closeDriver();
    }

    @DataProvider
    public Object[][] readExcelSheet() throws IOException, InvalidFormatException {

        return ExcelFileManager.getExcelData("test_data/Book1.xlsx");
    }
}
