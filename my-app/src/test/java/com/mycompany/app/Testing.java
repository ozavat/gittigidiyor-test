package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class Testing {

	@Test
	public void testing() {
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe"); 
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.gittigidiyor.com/");
		HomePage homePage = new HomePage(driver);
		assertEquals(true, homePage.checkHomePageIsOpen());	
		
		LogInPage loginPage = homePage.redirectToLogInPage();
		homePage = loginPage.loginPage("email@email.com", "dummypasswod");		
		assertEquals(true, homePage.checkLogInIsSuccess());
		
		SearchPage searchPage = homePage.search("bilgisayar");
		searchPage.openSecondPage();
		assertEquals(true, searchPage.checkSecondPage());
		
		ProductPage productPage = searchPage.selectRandomProduct();
		String price = productPage.getPrice();
		productPage.closePolicyAlert();
		BasketPage basketPage = productPage.addToBasket();
		assertEquals(true, basketPage.checkPrice(price));
		
		basketPage.setNewAmount();
		assertEquals(true, basketPage.checkAmount());
		
		basketPage.remove();
		assertEquals(true, basketPage.checkIsBasketEmpty());	

		driver.quit();
	}

}
