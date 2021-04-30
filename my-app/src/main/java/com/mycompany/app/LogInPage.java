package com.mycompany.app;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LogInPage {

	Logger logger = org.apache.logging.log4j.LogManager.getLogger(this);

	protected WebDriver driver;

	private By userNameField=	By.id("L-UserNameField");
	private By passwordField=	By.id("L-PasswordField");
	private By loginButton=	By.id("gg-login-enter");

	public LogInPage(WebDriver driver) {
		this.driver=driver;
	};

	public HomePage loginPage(String userName, String password) {
		WebElement userNameElement = driver.findElement(userNameField);
		userNameElement.clear();
		logger.info("Username field is cleared");
		userNameElement.sendKeys(userName);
		logger.info("Username inserted");
		WebElement passwordElement = driver.findElement(passwordField);
		passwordElement.clear();
		logger.info("Password field is cleared");
		passwordElement.sendKeys(password);
		logger.info("Password inserted");
		driver.findElement(loginButton).click();
		logger.info("Login button clicked");
		return new HomePage(driver);
	}
}
