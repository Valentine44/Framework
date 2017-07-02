package by.epam.atmentoring.framework.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import by.epam.atmentoring.framework.bo.Account;
import by.epam.atmentoring.framework.bo.Letter;
import by.epam.atmentoring.framework.pages.*;

/**
 * Gmail critical path test scenario implemented using Page Object
 * @author Valiantsin_Ivashynka
 *
 */
public class PageObjectGmailTest{
	
	WebDriver driver;
	/**
	 * launch browser, maximize window, set timeout
	 */
	@BeforeMethod
	private WebDriver setUp() {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome(); 
		try{
			driver = new RemoteWebDriver(new URL("http://10.6.207.10:4444/wd/hub"),capabilities);
		} catch (MalformedURLException e){
			e.printStackTrace();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	/**
	 * send letter with Ctrl+Enter, delete letter by drag&drop to Trash
	 */
	@Test 
	public void mouseKeyboardActions() {
		LogIn_EmailPage emailPage = new LogIn_EmailPage(driver).open();
		LogIn_PasswordPage passwordPage = emailPage.inputEmail().submitEmail();
		GoogleAccountPage googleAccount = passwordPage.inputPassword(Account.getPassword()).submitPassword();
		InboxPage inbox = googleAccount.selectMail();
		inbox.clickCompose().inputAddressee(Letter.getAddressee()).inputSubject().inputLetterText().sendViaCtrlEnter();
		SentMailPage sentMail = inbox.toSentMailPage();
		sentMail.expandLeftNavBar().letterToTrash(sentMail.getLastSentPage());
		Assert.assertTrue(sentMail.getMovedToTrashMessage().isDisplayed());
		sentMail.logOut();
	}
	/**
	 * refresh page, get page title with JavaScript
	 */
	@Test (dependsOnMethods = { "mouseKeyboardActions" })
	public void jsInjection() {
		LogIn_EmailPage emailPage = new LogIn_EmailPage(driver).open();
		LogIn_PasswordPage passwordPage = emailPage.inputEmail().submitEmail();
		GoogleAccountPage googleAccount = passwordPage.inputPassword(Account.getPassword()).submitPassword();
		InboxPage inbox = googleAccount.selectMail();
		Assert.assertTrue(inbox.jsGetTitle().contains("Inbox"));
		inbox.jsRefreshPage();
		Assert.assertTrue(inbox.jsGetTitle().contains("Inbox"));
	}
	/**
	 * log in to Gmail, create letter, save as draft, send the letter
	 */
	@Test (dependsOnMethods = { "jsInjection" })
	public void Scenario1() {
		LogIn_EmailPage emailPage = new LogIn_EmailPage(driver).open();
		LogIn_PasswordPage passwordPage = emailPage.inputEmail().submitEmail();
		GoogleAccountPage googleAccount = passwordPage.inputPassword(Account.getPassword()).submitPassword();
		InboxPage inbox = googleAccount.selectMail();
		inbox.clickCompose().inputAddressee(Letter.getAddressee()).inputSubject().inputLetterText().closeAndSave();
		DraftsPage drafts = inbox.toDraftsPage();
		drafts.openDraft().sendDraft();
		SentMailPage sentMail = drafts.toSentMailPage();
		Assert.assertEquals(sentMail.getEmailTime(), Letter.getSendingTime());
		sentMail.logOut();
	}
	/**
	 * submit incorrect password, then submit correct password
	 */
	@Test (dependsOnMethods = { "Scenario1" })
	public void Scenario2() {
		LogIn_EmailPage emailPage = new LogIn_EmailPage(driver).open();
		LogIn_PasswordPage passwordPage = emailPage.inputEmail().submitEmail();
		passwordPage = passwordPage.inputPassword(Account.getIncorrectPassword()).attemptSubmitPassword();
		Assert.assertTrue(driver.findElement(LogIn_PasswordPage.getWrongPasswordMessage()).isDisplayed());
		passwordPage.inputPassword(Account.getPassword());
		GoogleAccountPage googleAccount = passwordPage.submitPassword();
		googleAccount.selectMail();
		Assert.assertTrue(driver.getTitle().contains("Inbox"));
	}
	/**
	 * log in to Gmail, create letter, send to myself, open in neew window, remove
	 */
	@Test (dependsOnMethods = { "Scenario2" })
	public void Scenario3() {
		LogIn_EmailPage emailPage = new LogIn_EmailPage(driver).open();
		LogIn_PasswordPage passwordPage = emailPage.inputEmail().submitEmail();
		GoogleAccountPage googleAccount = passwordPage.inputPassword(Account.getPassword()).submitPassword();
		GmailPage gmail = googleAccount.selectMail();
		gmail.clickCompose().inputAddressee(Account.getEmail()).inputSubject().inputLetterText().sendDraft();
		SentMailPage sentMail = gmail.toSentMailPage();
		sentMail.inputSearchQuery(Page.getSearchQuery()).clickSearch();
		LetterPage letter = sentMail.openSearchResult();
		LetterWindow letterInNewWindow = letter.inNewWindow();
		Assert.assertEquals(letterInNewWindow.getActualSubject(), Letter.getSubject());
		Assert.assertEquals(letterInNewWindow.getActualSender(), Page.getSender());
		Assert.assertTrue(letterInNewWindow.getActualLetterText().equals(Letter.getLetterText()));
		//letterInNewWindow.removeLetter();
		letterInNewWindow.closeLetterWindow();
		//Assert.assertFalse(sentMail.getEmailTime().equals(GmailPage.getSendTime()));
	}
	/**
	 * close browser
	 */
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}