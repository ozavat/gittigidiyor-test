package com.mycompany.app;


import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.bytebuddy.asm.Advice.Enter;

public class ChromeBrowserLaunch {


	/**
	 * @param args
	 * @throws Exception
	 */
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
		} catch (Exception e) {
			throw new Exception("Driver can not open the page");
		}



		if (checkWebPageIsOpen(driver)==false) {
			throw new Exception("Page is not opened");

		}

		//Open login page
		try {
			driver.get("https://www.gittigidiyor.com/uye-girisi");
		} catch (Exception e) {
			throw new Exception("Login page cannot open");
		}

		//Set username and password and login
		WebElement userNameTextBox = driver.findElement(By.id("L-UserNameField"));
		userNameTextBox.sendKeys("emailadress@email.com");

		WebElement passwordTextBox = driver.findElement(By.id("L-PasswordField"));
		passwordTextBox.sendKeys("password");


		driver.findElement(By.id("gg-login-enter")).click();


		//check is user logged in
		driver.get("https://www.gittigidiyor.com/uye-girisi");
		if(driver.getCurrentUrl().indexOf("uye-girisi")>-1) {
			throw new Exception("Cannot login");
		}else {
			System.out.println("login oldu");
		}

		//focus search input and search for "bilgisayar" keyword
		WebElement inputBox = driver.findElements(By.cssSelector("input[data-cy='header-search-input']")).get(1);
		inputBox.click();
		inputBox.sendKeys("bilgisayar");
		inputBox.sendKeys(Keys.ENTER);


		//scroll down to page selection part
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

		//select the second page
		List<WebElement> searchList=driver.findElements(By.className("pager"));
		WebElement pager = searchList.get(0);
		for (WebElement webElement : pager.findElements(By.tagName("a"))) {
			if("2".equals(webElement.getText().trim())) {
				System.out.println("clickledim");
				webElement.click();
				break;
			}
		}


		//check second page is opened
		if("2".equals(driver.getCurrentUrl().split("=")[2])) {
			System.out.println("2. sayfadayım");
		};


		//Getting all product on the screen
		List<WebElement> catalogList=driver.findElements(By.className("catalog-view"));
		List<WebElement> catalogDetailedList=catalogList.get(0).findElements(By.className("srp-item-list"));

		//select random product
		Random rand = new Random();
		int rand_int1 = rand.nextInt(catalogDetailedList.size());
		catalogDetailedList.get(rand_int1).click();

		//Get product price from product screen
		String price="";
		if (!driver.findElement(By.id("sp-price-discountPrice")).getText().trim().isEmpty()) {
			price=driver.findElement(By.id("sp-price-discountPrice")).getText().trim();
		} else if (!driver.findElement(By.id("sp-price-lowPrice")).getText().trim().isEmpty()) {
			price=driver.findElement(By.id("sp-price-lowPrice")).getText().trim(); 
		}
		else if (!driver.findElement(By.id("sp-price-highPrice")).getText().trim().isEmpty()) {
			price=driver.findElement(By.id("sp-price-highPrice")).getText().trim(); 
		}


		System.out.println(price);

		//Close policy alert
		try {
			driver.findElements(By.className("policy-alert-close")).get(0).click();

		} catch (Exception e) {
			// TODO: handle exception
		}

		//Add product to basket
		driver.findElement(By.id("add-to-basket")).click();
		TimeUnit.SECONDS.sleep(3);
		//Go to basket page
		driver.get("https://www.gittigidiyor.com/sepetim");

		//Check the price is correct
		if(driver.findElement(By.className("new-price")).getText().equals(price)) 
		{
			System.out.println("price doğru");
		}else {
			System.out.println("price yanlış");
		}

		//Set new product amount
		Select drpCount = new Select (driver.findElement(By.cssSelector("select[class = 'amount']")));
		drpCount.selectByValue("2");	

		//Check the product amount
		TimeUnit.SECONDS.sleep(3);
		if("2".equals(driver.findElement(By.cssSelector("select[class = 'amount']")).getAttribute("value"))) {
			System.out.println("ürün adedi doğru");
		}else {
			System.out.println("değil");
		}

		//remove product from basket
		driver.findElement(By.className("btn-delete")).click();
		driver.quit();

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
