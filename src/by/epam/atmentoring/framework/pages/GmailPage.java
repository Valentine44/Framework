package by.epam.atmentoring.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import by.epam.atmentoring.framework.bo.Letter;
/**
 * Gmail page class (superclass of Inbox, Drafts, Sent mail classes)
 * @author Valiantsin_Ivashynka
 *
 */
public class GmailPage extends Page{
	private static final By INBOX_LINK = By.partialLinkText("Inbox");
	private static final By DRAFTS_LINK = By.partialLinkText("Drafts");
	private static final By SENT_MAIL_LINK = By.partialLinkText("Sent Mail");
	private static final By LOG_OUT = By.linkText("Sign out");
	private static final By SEND_BUTTON = By.xpath("//div[@role='button'][@aria-label='Send ‪(Ctrl-Enter)‬']");
	private static final By COMPOSE_BUTTON = By.xpath("//div[contains(text(), 'COMPOSE')]");
	private static final By ADDRESSEE_TEXT_FIELD = By.xpath("//textarea[@aria-label='To']");
	private static final By SUBJECT_TEXT_FIELD = By.xpath("//input[@placeholder='Subject']");
	private static final By LETTER_BODY_TEXT_FIELD = By.xpath("//div[@contenteditable='true']");
	private static final By SAVE_AND_CLOSE_BUTTON = By.xpath("//img[@data-tooltip='Save & Close']");
	private static final By SEARCH_FIELD = By.xpath("//input[@dir='ltr']");
	private static final By SEARCH_BUTTON = By.xpath("//button[@aria-label='Search Gmail']");
	private static final By INBOX_PAGE = By.xpath("//title[contains(text(), 'Inbox')]");
	private static final By DRAFTS_PAGE = By.xpath("//title[contains(text(), 'Drafts')]");
	private static final By SENT_MAIL_PAGE = By.xpath("//title[contains(text(), 'Sent Mail')]");
	private static final By SEARCH_RESULT = By.xpath("//div[@role='main']//tr[@aria-labelledby][1]");
	private final WebElement MORE = driver.findElement(By.xpath("//span[contains(text(), 'More')]"));
	private final WebElement TRASH = driver.findElement(By.xpath("//a[@title='Trash']"));
	/**
	 * GmailPage class constructor
	 * @param driver
	 */
	public GmailPage(WebDriver driver) {
		super(driver);
	}
	/**
	 * open Inbox page
	 * @return Inbox page instance
	 */
	public InboxPage toInboxPage() {
		driver.findElement(INBOX_LINK).click();
		waitElementPresent(INBOX_PAGE);
		return new InboxPage(driver);
	}
	/**
	 * open Drafts page
	 * @return Drafts page instance
	 */
	public DraftsPage toDraftsPage() {
		driver.findElement(DRAFTS_LINK).click();
		waitElementPresent(DRAFTS_PAGE);
		return new DraftsPage(driver);
	}
	/**
	 * open Sent mail page
	 * @return Sent mail page instance
	 */
	public SentMailPage toSentMailPage() {
		driver.findElement(SENT_MAIL_LINK).click();
		waitElementPresent(SENT_MAIL_PAGE);
		return new SentMailPage(driver);
	}
	/**
	 * input query into search field
	 * @return current page instance
	 */
	public GmailPage inputSearchQuery(String searchQuery) {
		driver.findElement(SEARCH_FIELD).sendKeys(searchQuery);
		return this;
	}
	/**
	 * click 'Search' button
	 * @return current page instance
	 */
	public GmailPage clickSearch() {
		driver.findElement(SEARCH_BUTTON).click();
		return this;
	}
	/**
	 * click 'Compose' button to start a letter
	 * @return current page instance
	 */
	public GmailPage clickCompose() {
		waitElementVisible(COMPOSE_BUTTON);
		driver.findElement(COMPOSE_BUTTON).click();
		return this;
	}
	/**
	 * input receiver's address into new letter form
	 * @return current page instance
	 */
	public GmailPage inputAddressee(String addressee) {
		waitElementVisible(ADDRESSEE_TEXT_FIELD);
		driver.findElement(ADDRESSEE_TEXT_FIELD).sendKeys(addressee);
		driver.findElement(ADDRESSEE_TEXT_FIELD).sendKeys(" ");
		return this;
	}
	/**
	 * input subject into new letter form
	 * @return current page instance
	 */
	public GmailPage inputSubject() {
		waitElementVisible(SUBJECT_TEXT_FIELD);
		driver.findElement(SUBJECT_TEXT_FIELD).sendKeys(Letter.getSubject());
		return this; 
	}
	/**
	 * input text into new letter form
	 * @return current page instance
	 */
	public GmailPage inputLetterText() {
		waitElementVisible(LETTER_BODY_TEXT_FIELD);
		driver.findElement(LETTER_BODY_TEXT_FIELD).sendKeys(Letter.getLetterText());
		return this;
	}
	/**
	 * close the letter without sending (it is saved to drafts automatically)
	 * @return current page instance
	 */
	public GmailPage closeAndSave() {
		waitElementVisible(SAVE_AND_CLOSE_BUTTON);
		driver.findElement(SAVE_AND_CLOSE_BUTTON).click();
		return this;
	}
	/**
	 * send open letter
	 * @return current page instance
	 */
	public GmailPage sendDraft() {
		waitElementVisible(SEND_BUTTON);
		Letter.setSendingTime();
		driver.findElement(SEND_BUTTON).click();
		return this;
	}
	/**
	 * open 1st search result set item
	 * @return letter page instance
	 */
	public LetterPage openSearchResult() {
		waitElementPresent(SEARCH_RESULT);
		driver.findElement(SEARCH_RESULT).click();
		return new LetterPage(driver);
	}
	/**
	 * send letter by emulating pressin "Ctrl+Enter"
	 * @return current page instance
	 */
	public GmailPage sendViaCtrlEnter() {
		new Actions(driver).sendKeys(Keys.CONTROL).sendKeys(Keys.RETURN).build().perform();
		return this;
	}
	/**
	 * click "More" in left-side navigation bar
	 * @return current page instance
	 */
	public GmailPage expandLeftNavBar(){
		new Actions(driver).click(MORE).build().perform();
		return this;
	}
	/**
	 * drag letter to trash
	 * @param locator
	 * @return current page instance
	 */
	public GmailPage letterToTrash(By locator){
		WebElement letter = driver.findElement(locator);
		new Actions(driver).dragAndDrop(letter,TRASH).build().perform();
		return this;
	}
	/**
	 * log out from Gmail
	 */
	public void logOut() {
		driver.findElement(By.cssSelector("a[title^='Google Account: ']")).click();
		waitElementVisible(LOG_OUT);
		driver.findElement(LOG_OUT).click();
	}
}