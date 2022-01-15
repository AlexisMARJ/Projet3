package shopizer;

import java.util.List;

// import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TablesPage extends BasePage {

	public TablesPage(WebDriver driver) {
		super(driver);

	}

	// Locators

	// @FindBy(css = "[href=\"javascript:filterCategory('BRAND','50')\"]")
	@FindBy(css = "[class=\"nav nav-list\"] :nth-child(3) > a")
	WebElement asianWoodFilter;

	@FindBy(css = "[class=\"nav nav-list\"] :nth-child(3) > a")
	WebElement asianWoodFilterText;

	@FindBy(css = "[id=\"productsContainer\"] > [class$=\"product\"] > [class=\"product-content text-center\"] > a > h3")
	List<WebElement> products;

	@FindBy(css = "[id=\"productsContainer\"] > [class$=\"product\"] > [class=\"product-content text-center\"] > a > h3")
	WebElement coffeTablePageLink;

	@FindBy(css = "h3[itemprop=\"name\"]")
	WebElement coffeeTableName;

	@FindBy(css = "h4 [itemprop=\"price\"]")
	WebElement coffeeTablePrice;

	// Methods

	public void clickAsianWoodFilter() {
		wait.until(ExpectedConditions.visibilityOf(asianWoodFilter));
		// Disclaimer : method .click() throws error "element is not clickable at point
		// because another element oscures it"
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", asianWoodFilter);
	}

	public String asianWoodFilter() {
		String asianWoodFilterExtractText = asianWoodFilterText.getText();
		return asianWoodFilterExtractText;
	}

	public CoffeeTablePage openCoffeTablePage() {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", coffeTablePageLink);
		return new CoffeeTablePage(this.driver);
	}

	public String coffeeTableName() {
		String coffeeTableExtractName = coffeeTableName.getText();
		return coffeeTableExtractName;
	}

	public String coffeeTablePrice() {
		String coffeeTableExtractPrice = coffeeTablePrice.getText();
		return coffeeTableExtractPrice;
	}

}
