package shopizer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PagePayment extends BasePage {
	
	public PagePayment (WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver);
		
	}

		@FindBy(xpath = "//div[@class='entry-header']/h1[contains(.,'Paiement')]")
		public static WebElement titrePagePaiement;

		
		public static boolean verifyPaiementisDisplayed() {
			wait.until(ExpectedConditions.visibilityOf(titrePagePaiement));
			return titrePagePaiement.isDisplayed();
		}
}
