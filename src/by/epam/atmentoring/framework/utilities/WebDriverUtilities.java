package by.epam.atmentoring.framework.utilities;

import org.openqa.selenium.WebDriver;

public class WebDriverUtilities {
	
	public static void switchToNewWindow(WebDriver driver) {
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[driver.getWindowHandles().size() - 1]);
    }
	
	public static void switchToMainWindow(WebDriver driver) {
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[0]);
    }
	
	public static void closeCurrentWindow(WebDriver driver) {
		driver.close();
    }
}