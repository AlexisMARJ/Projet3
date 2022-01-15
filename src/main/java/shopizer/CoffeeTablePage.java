package shopizer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CoffeeTablePage extends BasePage {

	public CoffeeTablePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = "[id=\"productPrice\"]")
	public WebElement coffeeTablePrice;

	@FindBy(css = "[class=\"sinple-c-title\"] > h3")
	public WebElement coffeeTableName;

	@FindBy(css = "[id=\"productRating\"]")
	public WebElement coffeeTableStars;

	@FindBy(css = "[class=\"btn addToCart addToCartButton btn-buy\"]")
	public WebElement coffeeTableAddToCartBtn;

	public String exractDeviseCoffeeTable() {
		wait.until(ExpectedConditions.visibilityOf(coffeeTablePrice));
		String price = coffeeTablePrice.getText();
		String devise2 = price.substring(0, 3);
		return devise2;
	}
	
	public String coffeeTableName() {
		String coffeeTableNameProductPage = coffeeTableName.getText();
		return coffeeTableNameProductPage;
	}

	public String coffeeTablePrice() {
		String coffeeTablePriceProductPage = coffeeTablePrice.getText();
		return coffeeTablePriceProductPage;
	}

}
