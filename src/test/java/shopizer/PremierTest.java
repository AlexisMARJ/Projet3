package shopizer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.edge.EdgeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PremierTest {
	WebDriver driver;
	Logger LOGGER = LoggerFactory.getLogger(PremierTest.class);
	String numberProductString = "3";
	int numberProductInt;
	WebDriverWait wait;

	@Before
	/*public void setUp() {
	
	 System.setProperty("webdriver.chrome.driver",
	 "src/main/resources/driver/chromedriver.exe"); driver = new ChromeDriver();
	 driver.manage().window().maximize(); }*/
	
		/*System.setProperty("webdriver.ie.driver",
	 "src/main/resources/driver/IEDriverServer.exe"); driver = new InternetExplorerDriver();
	 driver.manage().window().maximize(); }*/
	

	public void setup() throws Exception {
		String browser = System.getProperty("Navigateur"); // String browser = "";
		if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "src/main/resources/driver/geckodriver.exe");
			driver = new FirefoxDriver();
		}

		else if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
			driver = new ChromeDriver();
		}

		else if(browser.equalsIgnoreCase("edge")){
					System.setProperty("webdriver.edge.driver","src/main/resources/driver/msedgedriver.exe");
					driver = new EdgeDriver();
				
		} else {
			System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
			driver = new ChromeDriver();
		}

		driver.manage().window().maximize();
	}
	

	@Test
	public void run() throws InterruptedException {

		wait = new WebDriverWait(driver, 15);

		PageAccueil accueilPage = new PageAccueil(driver);
		PagePanier pagePanier = new PagePanier(driver);

		LOGGER.info("******* ETAPE 1 : Overture de l'application ******");
		driver.get("http://alexismarjollet.ddns.net:8080/shop");

		LOGGER.info("******* ETAPE 2 : Ajout de produit ******");
		accueilPage.ajouterAuPanier();

		LOGGER.info("******* ETAPE 3 : Incrementation du contenu du panier ******");
		wait.until(ExpectedConditions.visibilityOf(accueilPage.contentPanier));
		assertTrue("La quantitee n'a pas ete changee", accueilPage.contentPanier.getText().equals("(1)"));

		LOGGER.info("******* ETAPE 4 : Affichage du contenu du panier ******");
		accueilPage.moveMouse();

		LOGGER.info("******* ETAPE 5 : Ouverture du panier ******");
		accueilPage.ouvrirPanier();

		LOGGER.info("******* ETAPE 6 : Verification de l'affichage du panier ******");
		assertTrue("La page Panier n'est pas affichee", pagePanier.verifyBasketisDisplayed());

		LOGGER.info("******* ETAPE 7 : Verification de la presence d'un tableau ******");
		assertTrue(pagePanier.verifyTableisDisplayed());

		LOGGER.info("******* ETAPE 8 : Verification de la presence d'une image ******");
		pagePanier.verifyImage();

		LOGGER.info("******* ETAPE 9 : Verification de la presence d'un nom ******");
		assertTrue(pagePanier.verifyNameisDisplayed());

		LOGGER.info("******* ETAPE 10 : Verification de la presence de la quantite ******");
		pagePanier.verifyQuantity();

		LOGGER.info("******* ETAPE 11 : Verification de la presence du prix par article ******");
		assertTrue(pagePanier.verifyPriceProductisDisplayed());

		LOGGER.info("******* ETAPE 12 : Verification de la presence du total par article ******");
		assertTrue(pagePanier.verifypricePriceTotalArticleisDisplayed());

		LOGGER.info("******* ETAPE 13 : Recuperation du total de la section en String ******");
		String priceTotalArticle_un = pagePanier.priceTotalArticle.getText().substring(3);

		LOGGER.info("******* ETAPE 14 : Verification de la presence du total ******");
		assertTrue(pagePanier.verifypricePriceTotaisDisplayed());

		LOGGER.info("******* ETAPE 15 : Recuperation du total  ******");
		String priceTotal_un = pagePanier.priceTotal.getText().substring(3);

		LOGGER.info("******* ETAPE 16 : Doublement de la quantite  ******");
		pagePanier.changeQuantity(numberProductString);

		LOGGER.info("******* ETAPE 17 : Verification du doublement de la quantite ******");
		assertTrue("La quantitee n'a pas ete doublee",
				pagePanier.quantityProduct.getAttribute("value").equals(numberProductString));

		LOGGER.info("******* ETAPE 18 : Verification que les prix n'ont pas ete modifies******");
		assertTrue("Le prix de l'article a change",
				pagePanier.priceTotalArticle.getText().substring(3).equals(priceTotalArticle_un));
		assertTrue("Le total de section a change", pagePanier.priceTotal.getText().substring(3).equals(priceTotal_un));

		LOGGER.info("******* ETAPE 17 : Recalculation de la quantite ******");

		PagePanier pageRecalculate = pagePanier.recalculate();

		Thread.sleep(1000);

		LOGGER.info("******* ETAPE 18 : Verification du doublement de la quantite ******");
		numberProductInt = Integer.parseInt(numberProductString);

		String total_section_deux = pageRecalculate.priceTotalArticle.getText().substring(3);
		double prix_article_double = Double.parseDouble(priceTotalArticle_un);
		double total_section_double = Double.parseDouble(total_section_deux);
		assertEquals("Le total de section n'a pas ete multiplie par " + numberProductInt,
				prix_article_double * numberProductInt, total_section_double, 0);
		String total_commande_deux = pageRecalculate.priceTotal.getText().substring(3);
		double total_commande_double = Double.parseDouble(total_commande_deux);
		assertTrue("Le total de la commande n'a pas ete multiplie par " + numberProductInt,
				total_commande_double == prix_article_double * numberProductInt);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		LOGGER.info("******* ETAPE 19 : Payment ******");
		pageRecalculate.effectuerPayment();

		LOGGER.info("******* ETAPE 20 : La page du paiement est affichee ******");
		assertTrue("La page paiement n'est pas affichee", PagePayment.verifyPaiementisDisplayed());

	}

	@After
	public void teardown() {
		driver.quit();
	}

}
