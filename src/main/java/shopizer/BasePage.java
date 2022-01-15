/**
 * 
 */
package shopizer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author formation
 *
 */
public class BasePage {
	
	public WebDriver driver;
	public static WebDriverWait wait;
	// constructor
	public BasePage (WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 15);
		PageFactory.initElements(driver, this);

}
	
	@FindBy(xpath="//a[@class=\"header-logo\"]")
	private WebElement accueil;
	
	

}



