package com.mycompany.app;

import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchPage {

	Logger logger = org.apache.logging.log4j.LogManager.getLogger(this);

	protected WebDriver driver;

	private By pagerField=	By.className("pager");
	private By linkTagName =  By.tagName("a");
	private By catalogView = By.className("catalog-view");
	private By srpItemList = By.className("srp-item-list");

	public SearchPage(WebDriver driver) {
		this.driver=driver;
	}

	public void openSecondPage() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		logger.info("Scrolled down to the page");
		List<WebElement> searchList=driver.findElements(pagerField);
		WebElement pager = searchList.get(0);
		for (WebElement webElement : pager.findElements(linkTagName)) {
			if("2".equals(webElement.getText().trim())) {
				webElement.click();		
				logger.info("Second page button is clicked");
				break;
			}
		}
	}

	public boolean checkSecondPage() {
		if("2".equals(driver.getCurrentUrl().split("=")[2])) {
			logger.info("Second page is opened");
			return true;
		}else {
			return false;
		}
	}

	public ProductPage selectRandomProduct() {

		//Getting all product on the screen
		List<WebElement> catalogList=driver.findElements(catalogView);
		List<WebElement> catalogDetailedList=catalogList.get(0).findElements(srpItemList);
		//select random product
		Random rand = new Random();
		int rand_int1 = rand.nextInt(catalogDetailedList.size());
		catalogDetailedList.get(rand_int1).click();
		logger.info("Random product is clicked");
		//logger.info("Random product is clicked");
		return new ProductPage(driver);
	}
}
