package pages;

import actions.BrowserActions;
import actions.UI_Actions;
import org.apache.hc.core5.util.Asserts;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SelectDocumentPage {

  private final String documentNameSelector =  "//div[text()='%s']";
  private final String documentTitleSelector = "//div[@class='title']";

  /* to get the whole text of the span */
  private String freePriceSelector = "//div[text()='%s']/..//div[@class='product-price']/span";

  UI_Actions uiActions = new UI_Actions() ;

  public void selectDocument(String documentName) throws Exception {
    uiActions.clickOn(String.format(documentNameSelector,documentName), UI_Actions.Locators.XPATH);
    String actualDocumentTitle = new WebDriverWait(BrowserActions.driver,10).
        until(ExpectedConditions.visibilityOfElementLocated(
            uiActions.locateElement(documentTitleSelector, UI_Actions.Locators.XPATH))).getText() ;
        Assert.assertEquals(actualDocumentTitle,documentName,"The document name does not found!");
  }

  public boolean checkFreePrice(String documentName) throws Exception {
    return uiActions.getText(String.format(freePriceSelector,documentName), UI_Actions.Locators.XPATH).contains("Free") ;
  }

  public String getDocumentPrice(String documentName) throws Exception {
    return  uiActions.getText(String.format(freePriceSelector,documentName), UI_Actions.Locators.XPATH);


  }



}
