package com.mycompany.app;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {

	Logger logger = org.apache.logging.log4j.LogManager.getLogger(this);

	protected WebDriver driver;

	private By discountPrice=	By.id("sp-price-discountPrice");
	private By lowPrice=	By.id("sp-price-lowPrice");
	private By highPrice=	By.id("sp-price-highPrice");
	private By policyAlert = By.className("policy-alert-close");
	private By addToBasket = By.id("add-to-basket");

	public ProductPage(WebDriver driver) {
		this.driver=driver;
	}

	public String getPrice() {
		//Get product price from product screen
		String price="";
		if (!driver.findElement(discountPrice).getText().trim().isEmpty()) {
			price=driver.findElement(discountPrice).getText().trim();
			logger.info("Product has 2 type of discount");
		} else if (!driver.findElement(lowPrice).getText().trim().isEmpty()) {
			price=driver.findElement(lowPrice).getText().trim(); 
			logger.info("Product has 1 type of discount");
		}
		else if (!driver.findElement(highPrice).getText().trim().isEmpty()) {
			price=driver.findElement(highPrice).getText().trim();
			logger.info("Product has no discount");
		}
		return price;
	}

	public void closePolicyAlert() {
		try {
			driver.findElements(policyAlert).get(0).click();
			logger.info("Policy alert is closed");
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public BasketPage addToBasket() {
		driver.findElement(addToBasket).click();
		logger.info("Product added to basket");

		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			logger.error(e);
		}

		logger.info("Waited for 3 seconds");
		//Go to basket page
		driver.get("https://www.gittigidiyor.com/sepetim");	
		return new BasketPage(driver);
	}
}
