package actions;

import org.apache.hc.core5.util.Asserts;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UI_Actions {

    /**
     * locateElement: locates an element according the parameter locator
     *
     * @param Selector the selector of the element which will be used to locate this element
     * @param locator  the locator type by which the the element will be located(xpath, id ,...)
     * @return the element By object
     */
    public By locateElement(String Selector, Locators locator) throws Exception {
        switch (locator) {
            case ID -> {
                return By.id(Selector);
            }

            case TAG_NAME -> {
                return By.tagName(Selector);
            }

            case CSS_SlECTOR -> {
                return By.cssSelector(Selector);
            }

            case NAME -> {
                return By.name(Selector);
            }

            case LINK_TEXT -> {
                return By.linkText(Selector);
            }

            case PARTIAL_LINK_TEXT -> {
                return By.partialLinkText(Selector);
            }

            case XPATH -> {
                return By.xpath(Selector);
            }

            default -> throw new Exception("This element can't be found!");
        }
    }

    public void clickOn(String selector, Locators locator) throws Exception {
       new WebDriverWait(BrowserActions.driver , 100).
               until(ExpectedConditions.elementToBeClickable(locateElement(selector, locator))).click();
    }

    public String getText(String selector, Locators locator) throws Exception {
        return
                new WebDriverWait(BrowserActions.driver, 100).
                        until(ExpectedConditions.
                                visibilityOf(BrowserActions.driver.findElement(locateElement(selector, locator)))).
                        getText();
    }

    /**
     * Locator types -->xpath , id , name ...
     */
    public enum Locators {
        XPATH,
        LINK_TEXT,
        PARTIAL_LINK_TEXT,
        TAG_NAME,
        CSS_SlECTOR,
        ID,
        NAME
    }

    public void navigateToPage(String url, String selector, Locators locator) throws Exception {
        BrowserActions.driver.get(url);
        /*Assert that the specified parameter element has been existed (located)*/

        WebDriverWait wait = new WebDriverWait(BrowserActions.driver, 100);

        new WebDriverWait(BrowserActions.driver, 100).until(
                webDriver -> ((JavascriptExecutor) webDriver).
                        executeScript("return document.readyState").equals("complete"));

        WebElement webElement = wait.until(ExpectedConditions.presenceOfElementLocated(locateElement(selector, locator)));
        Asserts.notNull(webElement, "Navigation Failed to this Website " + url);

    }

}
