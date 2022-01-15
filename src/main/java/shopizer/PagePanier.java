package shopizer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PagePanier extends BasePage {

	public PagePanier(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@class='entry-header']/h1[contains(.,'Revoir votre commande')]")
	public WebElement titrePagePanier;

	@FindBy(xpath = "//table[@id='mainCartTable']")

	public WebElement tableauRecap;

	@FindBy(xpath = "//div[@class='row-cart']/img")
	public WebElement imageProduct;

	@FindBy(xpath = "//div[@class='row-cart']//span[@class='nomargin']")
	public WebElement nameProduct;

	@FindBy(xpath = "//input[@class='input-small quantity text-center']")
	public WebElement quantityProduct;

	@FindBy(xpath = "//td[@data-th='Prix']/strong")
	public WebElement priceProduct;

	@FindBy(xpath = "//td[@data-th='Total']/strong")
	public WebElement priceTotalArticle;

	@FindBy(xpath = "//th[text()='Total']/following-sibling::td/span[@class='amount']")
	public WebElement priceTotal;

	@FindBy(xpath = "//a[text()='Recalculer']")
	public WebElement buttonRecalculate;

	@FindBy(css = "[class='wc-proceed-to-checkout']>a")
	public WebElement buttonPayment;

	public boolean verifyBasketisDisplayed() {
		wait.until(ExpectedConditions.visibilityOf(titrePagePanier));
		return titrePagePanier.isDisplayed();
	}

	public boolean verifyTableisDisplayed() {
		wait.until(ExpectedConditions.visibilityOf(tableauRecap));
		return tableauRecap.isDisplayed();
	}

	public void verifyImage() {
		if ((imageProduct) != null) {
			System.out.println("Image is Present");
		} else {
			System.out.println("Image is Absent");
		}
	}

	public boolean verifyNameisDisplayed() {
		return nameProduct.isDisplayed();
	}

	public void verifyQuantity() {
		quantityProduct.getAttribute("value").equals("1");
	}

	public boolean verifyPriceProductisDisplayed() {
		return priceProduct.isDisplayed();
	}

	public boolean verifypricePriceTotalArticleisDisplayed() {
		return priceTotalArticle.isDisplayed();
	}

	public boolean verifypricePriceTotaisDisplayed() {
		return priceTotal.isDisplayed();
	}

	public void changeQuantity(String numberProductString) {
		quantityProduct.click();
		quantityProduct.clear();
		quantityProduct.sendKeys(numberProductString);
	}

	public PagePanier recalculate() {
		buttonRecalculate.click();
		return new PagePanier(this.driver);
	}

	public PagePayment effectuerPayment() {

		wait.until(ExpectedConditions.elementToBeClickable(buttonPayment));
		buttonPayment.click();

		return new PagePayment(this.driver);
	}
}