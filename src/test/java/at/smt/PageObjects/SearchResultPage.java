package at.smt.PageObjects;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SearchResultPage {
	private final WebDriver driver;

	@FindBy(how = How.CSS, using = "span[class='fleft tab selected']")
	public WebElement allResults;
	@FindBy(tagName = "article")
	List<WebElement> articles;

	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
	}

	public void allResultHigherThan(Integer value) {
		String result = allResults.findElement(By.cssSelector("span[class='counter']")).getText();
		result = result.replaceAll("[() ]", "");
		assertTrue("Expected " + value + " was lower than result: " + result, Integer.parseInt(result) > value);
	}

	public OfferPage enterResult(Integer index) {
		articles.get(index).findElement(By.cssSelector("div[class='item-col photo-box']")).click();
		return PageFactory.initElements(driver, OfferPage.class);
	}
}
