package at.smt.Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import at.smt.PageObjects.OfferPage;
import at.smt.PageObjects.SearchPage;
import at.smt.PageObjects.SearchResultPage;
import at.smt.Utilities.DriverSettings;

public class SearchTest {

	private static WebDriver driver;

	@Before
	public void setup() {
		driver = new FirefoxDriver();
		DriverSettings.wait = new WebDriverWait(driver, 30);
		driver.manage().window().maximize();
	}

	@After
	public void close() {
		driver.close();
	}

	@Test
	public void searchTest() {
		SearchPage mainPage = SearchPage.navigateTo(driver);
		mainPage.selectValueInSpecifiedList("BMW", mainPage.searchBrandSelect);
		mainPage.selectValueInSpecifiedList("Seria 3", mainPage.searchModelSelect);
		mainPage.setValueForElement("35000", true, mainPage.searchPrice);
		mainPage.setValueForElement("80000", false, mainPage.searchPrice);
		mainPage.setValueForElement("2007", true, mainPage.searchYear);
		mainPage.setValueForElement("50000", true, mainPage.searchMileage);
		mainPage.fuelTypeIsEither();
		SearchResultPage searchResultPage = mainPage.executeSearch();
		searchResultPage.allResultHigherThan(500);
		OfferPage offerPage = searchResultPage.enterResult(0);
		offerPage.saveScreenshot();
	}
}
