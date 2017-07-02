package by.epam.atmentoring.framework.bo;

public class Account {

	private static final String EMAIL = "iv.selenium.test@gmail.com";
	private static final String PASSWORD = "$T123456";
	private static final String INCORRECT_PASSWORD = "wrong!";

	public static String getEmail() {
		return EMAIL;
	}

	public static String getPassword() {
		return PASSWORD;
	}
	
	public static String getIncorrectPassword() {
		return INCORRECT_PASSWORD;
	}
}