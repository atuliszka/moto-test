package at.smt.PageObjects;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

public class OfferPage {
	private final WebDriver driver;

	public OfferPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void saveScreenshot() {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File("c:\\screenshots\\screenshot.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
