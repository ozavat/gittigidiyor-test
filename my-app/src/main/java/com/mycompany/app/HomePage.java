package com.mycompany.app;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

	Logger logger = org.apache.logging.log4j.LogManager.getLogger(this);

	protected WebDriver driver;

	private By searchField = By.cssSelector("input[data-cy='header-search-input']");

	public HomePage(WebDriver driver) {
		this.driver=driver;
	}

	public LogInPage redirectToLogInPage() {
		driver.get("https://www.gittigidiyor.com/uye-girisi");
		logger.info("Login page is opened");
		return new LogInPage(driver);
	}

	public boolean checkHomePageIsOpen() {
		if (driver.getTitle()==null) {
			logger.error("Cannot open homePage");
			return false;
		}else if(driver.getTitle().trim().length()>0){
			logger.info("Home page is opened");
			return true;
		}else {
			logger.error("Cannot open homePage");
			return false;
		}
	}

	public boolean checkLogInIsSuccess() {
		driver.get("https://www.gittigidiyor.com/uye-girisi");
		if(driver.getCurrentUrl().indexOf("uye-girisi")>-1) {
			logger.error("Login Failed");
			return false;
		}else {
			logger.info("Login success");
			return true;
		}
	}

	public SearchPage search(String keyWord) {
		WebElement inputBox = driver.findElements(searchField).get(1);	
		inputBox.click();
		logger.info("Focused on search input");
		inputBox.sendKeys(keyWord);
		logger.info("'Bilgisayar' keyword inserted");
		inputBox.sendKeys(Keys.ENTER);
		logger.info("Send Enter to search input");
		return new SearchPage(driver);
	}

}
