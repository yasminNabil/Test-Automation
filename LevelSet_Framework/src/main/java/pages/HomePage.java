package pages;

import actions.UI_Actions;

public class HomePage {
  String payment_Selector = "//h2[text()='Payment Help is Here']" ;
  private String createDocumentsSelector = "Create a Document";
  UI_Actions uiActions = new UI_Actions();

  public void navigateToHome() throws Exception {
    uiActions.navigateToPage("https://www.levelset.com/",payment_Selector , UI_Actions.Locators.XPATH );
  }
  public SelectDocumentPage pressCreateDocument () throws Exception {
    uiActions.clickOn( createDocumentsSelector, UI_Actions.Locators.LINK_TEXT);
    return new SelectDocumentPage() ;
  }

}
