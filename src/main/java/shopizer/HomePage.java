package shopizer;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// Locators

	@FindBy(css = "[productid=\"50\"]")
	public WebElement firstBtnAddBasket;

	@FindBy(css = ".mainmenu > nav:nth-child(1) > ul:nth-child(1) > li:nth-child(4) > a:nth-child(1)")
	public WebElement bedroomBtn;

	@FindBy(css = ".mainmenu > nav:nth-child(1) > ul:nth-child(1) > li:nth-child(4) > ul:nth-child(2) > li:nth-child(1) > a:nth-child(1)")
	public WebElement nightTableBtn;

	@FindBy(css = "[item-name=\"Compact night table\"]  > div >[class=\"listing-product-name\"]")
	public WebElement compactNightTable;

	@FindBy(css = "[class=\"mainmenu hidden-xs\"] > nav > ul > li > a[href^=\"\\/shop\\/category\\/tables\\.html\"]")
	public WebElement tablesBtn;

	// Methods

	public NightTablesPage openNightTablesPage() {
		wait.until(ExpectedConditions.visibilityOf(bedroomBtn));
		Actions action = new Actions(driver);
		action.moveToElement(bedroomBtn).perform();
		wait.until(ExpectedConditions.visibilityOf(nightTableBtn));
		nightTableBtn.click();

		wait.until(ExpectedConditions.visibilityOf(compactNightTable));
		// marche pas :
		// Actions action2 = new Actions(driver);
		// action2.moveToElement(compactNightTable).perform();
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", compactNightTable);
		return new NightTablesPage(this.driver);
	}

	public TablesPage openTablesPage() {
		wait.until(ExpectedConditions.elementToBeClickable(tablesBtn));
		tablesBtn.click();
		return new TablesPage(this.driver);
	}

}
