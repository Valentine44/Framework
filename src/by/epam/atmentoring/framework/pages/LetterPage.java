package by.epam.atmentoring.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import by.epam.atmentoring.framework.utilities.WebDriverUtilities;
/**
 * Letter page class
 * @author Valiantsin_Ivashynka
 *
 */
public class LetterPage extends GmailPage {
	private static final By IN_NEW_WINDOW_BUTTON = By.xpath("//img[@alt='In new window']");
	/**
	 * LetterPage constructor
	 * @param driver
	 */
	public LetterPage(WebDriver driver) {
		super(driver);
	}
	/**
	 * open letter in new window
	 * @return letter window instance
	 */
	public LetterWindow inNewWindow() {
		waitElementVisible(IN_NEW_WINDOW_BUTTON);
		driver.findElement(IN_NEW_WINDOW_BUTTON).click();
		WebDriverUtilities.switchToNewWindow(driver);
		return new LetterWindow(driver);
	}
}