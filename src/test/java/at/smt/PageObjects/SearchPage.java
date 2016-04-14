package at.smt.PageObjects;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import at.smt.Utilities.DriverSettings;

public class SearchPage {
	private final WebDriver driver;

	@FindBy(how = How.CSS, using = "div[data-key='make']")
	public WebElement searchBrandSelect;
	@FindBy(how = How.CSS, using = "div[data-key='model']")
	public WebElement searchModelSelect;
	@FindBy(how = How.CSS, using = "div[data-key='price']")
	public WebElement searchPrice;
	@FindBy(how = How.CSS, using = "div[data-key='year']")
	public WebElement searchYear;
	@FindBy(how = How.CSS, using = "div[data-key='mileage']")
	public WebElement searchMileage;
	@FindBy(how = How.CSS, using = "div[data-key='fuel_type']")
	public WebElement searchFuelType;
	@FindBy(how = How.CSS, using = "button[class='om-button search-area__button-submit']")
	public WebElement searchButton;

	public SearchPage(WebDriver driver) {
		this.driver = driver;
	}

	public static SearchPage navigateTo(WebDriver driver) {
		driver.get("http://otomoto.pl");
		return PageFactory.initElements(driver, SearchPage.class);
	}

	public void selectValueInSpecifiedList(String name, WebElement list) {
		// could delete display:none from the original select but that
		// doesn't replicate user actions so we're using the rendered one
		DriverSettings.wait.until(ExpectedConditions.elementToBeClickable(
				list.findElement(By.className("select2-selection__arrow"))));
		list.findElement(By.className("select2-selection__arrow")).click();
		DriverSettings.wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector("ul[class='select2-results__options']")));
		List<WebElement> elementList = driver.findElements(By.className("select2-results__option"));
		for (WebElement element : elementList) {
			if (element.getText().equals(name)) {
				element.click();
				//  in case the element is not visible we need to scroll back up after the click
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("window.scrollTo(0, 0)");
				break;
			}
		}
		//  elements unique labels look dynamic thats why i'm using the top div in the first place
		String titleId = list.findElement(By.cssSelector("span[role=combobox]")).getAttribute("aria-labelledby");
		assertEquals(name, list.findElement(By.id(titleId)).getAttribute("title"));
	}

	public void setValueForElement(String value, boolean bottom, WebElement list) {
		List<WebElement> price = list.findElements(By.cssSelector("span[class=select2-selection__rendered]"));
		if (bottom) {
			price.get(0).click();
		} else {
			price.get(1).click();
		}
		WebElement input = driver.findElement(By.cssSelector("input[type='search']"));
		input.sendKeys(value);
		input.sendKeys(Keys.RETURN);
		List<WebElement> titles = list.findElements(By.cssSelector("span[role=combobox]"));
		String titleId = "";
		if (bottom) {
			titleId = titles.get(0).getAttribute("aria-labelledby");
		} else {
			titleId = titles.get(1).getAttribute("aria-labelledby");
		}
		if (!list.equals(searchYear)) {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(' ');
			DecimalFormat formatter = new DecimalFormat("#,###", symbols);
			//  could parametrize the currency from config if localized
			value = formatter.format(Integer.parseInt(value));
			if(list.equals(searchPrice)) {
				 value += " PLN"; 
			} else if (list.equals(searchMileage)) {
				value += " km";
			}
		}
		assertEquals(value, list.findElement(By.id(titleId)).getAttribute("title"));
	}
	
	public void fuelTypeIsEither() {
		assertEquals(searchFuelType.findElements(By.cssSelector
				("span[class='fuel_type_box search-area__fuel-type-option active']")).size() > 0, true);
	}
	
	public SearchResultPage executeSearch() {
		searchButton.click();
		return PageFactory.initElements(driver, SearchResultPage.class);
	}
	
}
