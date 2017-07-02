package by.epam.atmentoring.framework.bo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Letter {

	private static final String ADDRESSEE = "iv.selenium.test2@yopmail.com";
	private static final String SUBJECT = "Test subject";
	private static final String LETTER_TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n\nKind regards,\nJohn Doe";
	private static String sendingTime = "";
	/**
	 * get email address of addressee
	 * @return email address of addressee
	 */
	public static String getAddressee() {
		return ADDRESSEE;
	}
	/**
	 * get email subject
	 * @return email subject
	 */
	public static String getSubject() {
		return SUBJECT;
	}
	/**
	 * get email text
	 * @return email text
	 */
	public static String getLetterText() {
		return LETTER_TEXT;
	}
	/**
	 * set time when the last email was sent
	 */
	public static void setSendingTime() {
		sendingTime = (new SimpleDateFormat("hh:mm a").format(new Date()).toLowerCase()).replaceFirst("^0+(?!$)", "");
	}
	/**
	 * get time when the last email was sent
	 * @return time when the last email was sent
	 */
	public static String getSendingTime() {
		return sendingTime;
	}
}