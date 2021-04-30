package com.mycompany.app;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BasketPage {

	Logger logger = org.apache.logging.log4j.LogManager.getLogger(this);

	protected WebDriver driver;

	private By basketPrice = By.className("new-price");
	private By amountDropdown = By.cssSelector("select[class = 'amount']");
	private By buttonDelete = By.className("btn-delete");
	private By productListField = By.className("product-items-container");

	public BasketPage(WebDriver driver) {
		this.driver=driver;
	}

	public boolean checkPrice(String price) {
		//Check the price is correct
		if(driver.findElement(basketPrice).getText().equals(price)) 
		{
			logger.info("Price is correct");
			return true;
		}else {
			logger.error("Price is NOT correct");
			return false;
		}
	}

	public void setNewAmount() {
		Select drpCount = new Select (driver.findElement(amountDropdown));
		try {
			drpCount.selectByValue("2");
			logger.info("Product amount is changed to 2");
		} catch (Exception e) {
			logger.error(e);
		}
		
	}

	public boolean checkAmount() {
		//Check the product amount
		try {
			TimeUnit.SECONDS.sleep(3);
			logger.info("Waited for 3 seconds");
		} catch (InterruptedException e) {

			logger.error(e);
		}
		//logger.info("Waited for 3 seconds");
		if("2".equals(driver.findElement(amountDropdown).getAttribute("value"))) {
			logger.info("Product amount is correct");	
			return true;
		}else {
			logger.error("Product amount is NOT correct");		
			return false;
		}
	}

	public void remove() {
		driver.findElement(buttonDelete).click();
		logger.info("Product removed from basket");
	}

	public boolean checkIsBasketEmpty() {
		try {
			TimeUnit.SECONDS.sleep(3);
			logger.info("Waited for 3 seconds");
		} catch (InterruptedException e) {

			logger.error(e);
		}
		WebElement productList=driver.findElement(productListField);
		if (productList.findElements(By.tagName("div")).size()>0) {
			logger.error("Basket is not empty");
			return false;
		}else {
			logger.info("Basket is empty");
			return true;
		}
	}
}
