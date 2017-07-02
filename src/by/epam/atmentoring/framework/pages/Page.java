package by.epam.atmentoring.framework.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
/**
 * Page class (all other pages' superclass)
 * @author Valiantsin_Ivashynka
 *
 */
public class Page {
	WebDriver driver;
	public static final int WAIT_FOR_ELEMENT_TIME = 100;
	protected static final String URL = "https://accounts.google.com/signin/v2/identifier";
	protected static final String EXPECTED_LETTER_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
	protected static final String SENDER = "<iv.selenium.test@gmail.com>";
	protected static final String SEARCH_QUERY = "subject";
	/**
	 * Page class constructor
	 * @param driver
	 */
	public Page(WebDriver driver) {
		this.driver = driver;
	}
	/**
	 * wait until web-element is visible on the page
	 * @param element
	 */
	public void waitElementVisible(By locator) {
		new WebDriverWait (driver, WAIT_FOR_ELEMENT_TIME).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	/**
	 * wait until web-element is present on the page
	 * @param element
	 */
	public void waitElementPresent(By locator) {
		new WebDriverWait (driver, WAIT_FOR_ELEMENT_TIME).until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	/**
	 * refresh page
	 */
	public Page jsRefreshPage(){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("history.go(0)");
		return this;
	}
	/**
	 * get title of the current page
	 */
	public String jsGetTitle(){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		return js.executeScript("return document.title;").toString();
	}
	/**
	 * get query for sent email search 
	 * @return query for sent email search 
	 */
	public static String getSearchQuery() {
		return SEARCH_QUERY;
	}
	/**
	 * get own email in specific format to assert email sender
	 * @return own email in specific format (enclosed in angle brackets)
	 */
	public static String getSender() {
		return SENDER;
	}
}