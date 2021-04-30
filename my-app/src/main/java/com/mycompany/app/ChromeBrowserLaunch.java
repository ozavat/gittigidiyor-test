package com.mycompany.app;


import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class ChromeBrowserLaunch {


	/**
	 * @param args
	 * @throws Exception
	 */

	static Logger logger = org.apache.logging.log4j.LogManager.getLogger(ChromeBrowserLaunch.class);

	public static void main(String[] args) throws Exception {

		//Creating a driver object referencing WebDriver interface
		WebDriver driver;

		//Setting the webdriver.chrome.driver property to its executable's location
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");

		//Instantiating driver object
		driver = new ChromeDriver();

		//Opening default web page
		try {	
			driver.get("https://www.gittigidiyor.com");
			if (checkWebPageIsOpen(driver)==false) {
				throw new Exception("Page is not opened");

			}else {
				logger.info("Launching page is opened");
			}

		} catch (Exception e) {
			logger.error(e);	
		}

		//Open login page
		try {
			driver.get("https://www.gittigidiyor.com/uye-girisi");
			logger.info("Login page is opened");

		} catch (Exception e) {
			logger.error(e);
		}

		//Set user name and password and login
		WebElement userNameTextBox = driver.findElement(By.id("L-UserNameField"));
		userNameTextBox.sendKeys("email@email.com");
		logger.info("Username inserted");

		WebElement passwordTextBox = driver.findElement(By.id("L-PasswordField"));
		passwordTextBox.sendKeys("dummypassword");
		logger.info("Password inserted");

		driver.findElement(By.id("gg-login-enter")).click();
		logger.info("Login button clicked");

		//check is user logged in
		driver.get("https://www.gittigidiyor.com/uye-girisi");
		if(driver.getCurrentUrl().indexOf("uye-girisi")>-1) {
			logger.error("Login failed");
		}else {
			logger.info("Login success");
		}

		//focus search input and search for "bilgisayar" keyword
		WebElement inputBox = driver.findElements(By.cssSelector("input[data-cy='header-search-input']")).get(1);	
		inputBox.click();
		logger.info("Focused on search input");
		inputBox.sendKeys("bilgisayar");
		logger.info("'Bilgisayar' keyword inserted");
		inputBox.sendKeys(Keys.ENTER);
		logger.info("Send EnterKeys to search input");

		//scroll down to page selection part
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		logger.info("Scrolled down to the page");

		//select the second page
		List<WebElement> searchList=driver.findElements(By.className("pager"));
		WebElement pager = searchList.get(0);
		for (WebElement webElement : pager.findElements(By.tagName("a"))) {
			if("2".equals(webElement.getText().trim())) {
				webElement.click();
				logger.info("Second page button is clicked");
				break;
			}
		}

		//check second page is opened
		if("2".equals(driver.getCurrentUrl().split("=")[2])) {
			logger.info("Second page is opened");
		};

		//Getting all product on the screen
		List<WebElement> catalogList=driver.findElements(By.className("catalog-view"));
		List<WebElement> catalogDetailedList=catalogList.get(0).findElements(By.className("srp-item-list"));

		//select random product
		Random rand = new Random();
		int rand_int1 = rand.nextInt(catalogDetailedList.size());
		catalogDetailedList.get(rand_int1).click();
		logger.info("Random product is clicked");

		//Get product price from product screen
		String price="";
		if (!driver.findElement(By.id("sp-price-discountPrice")).getText().trim().isEmpty()) {
			price=driver.findElement(By.id("sp-price-discountPrice")).getText().trim();
			logger.info("Product has 2 type of discount");
		} else if (!driver.findElement(By.id("sp-price-lowPrice")).getText().trim().isEmpty()) {
			price=driver.findElement(By.id("sp-price-lowPrice")).getText().trim(); 
			logger.info("Product has 1 type of discount");
		}
		else if (!driver.findElement(By.id("sp-price-highPrice")).getText().trim().isEmpty()) {
			price=driver.findElement(By.id("sp-price-highPrice")).getText().trim();
			logger.info("Product has no discount");
		}

		//Close policy alert
		try {
			driver.findElements(By.className("policy-alert-close")).get(0).click();
			logger.info("Policy alert is closed");
		} catch (Exception e) {
			logger.error(e);		
		}

		//Add product to basket
		driver.findElement(By.id("add-to-basket")).click();
		logger.info("Product added to basket");
		TimeUnit.SECONDS.sleep(3);
		logger.info("Waited for 3 seconds");
		//Go to basket page
		driver.get("https://www.gittigidiyor.com/sepetim");
		logger.info("Basket page is opened");

		//Check the price is correct
		if(driver.findElement(By.className("new-price")).getText().equals(price)) 
		{
			logger.info("Price is correct");
		}else {
			logger.error("Price is NOT correct");
		}

		//Set new product amount
		Select drpCount = new Select (driver.findElement(By.cssSelector("select[class = 'amount']")));
		drpCount.selectByValue("2");
		logger.info("Product amount is changed to 2");

		//Check the product amount
		TimeUnit.SECONDS.sleep(3);
		logger.info("Waited for 3 seconds");
		if("2".equals(driver.findElement(By.cssSelector("select[class = 'amount']")).getAttribute("value"))) {
			logger.info("Product amount is correct");	
		}else {
			logger.error("Product amount is NOT correct");
		}

		//remove product from basket
		driver.findElement(By.className("btn-delete")).click();
		logger.info("Product removed from basket");

		WebElement productList=driver.findElement(By.className("product-items-container"));
		if (productList.findElements(By.tagName("div")).size()>0) {
			System.out.println("Basket is empty");
		}else {
			System.out.println("Basket is not empty");
		}		
		driver.quit();
		logger.info("Application closed");
	}

	public static boolean checkWebPageIsOpen(WebDriver driver) {
		if (driver.getTitle()==null) {
			return false;
		}else if(driver.getTitle().trim().length()>0){
			return true;
		}
		return false;
	}
}
