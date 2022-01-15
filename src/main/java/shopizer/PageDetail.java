package shopizer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PageDetail extends BasePage {

		public PageDetail (WebDriver driver) {

			super(driver);
		}


@FindBy(xpath = "")
public WebElement imageItemDetail;

@FindBy(xpath = "")
public WebElement priceInitItemDetail;

@FindBy(xpath = "")
public WebElement pricePromoItemDetail;

@FindBy(xpath = "")
public WebElement deviseItemDetail;

@FindBy(xpath = "")
public WebElement nameItemDetail;

@FindBy(xpath = "")
public WebElement numberStarItemDetail;

@FindBy(xpath = "//button[@productid='400']")
public WebElement buttonAjouterAuPanier;
}