package com.amazon.core;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base {
    protected WebDriver driver;

    public Base(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(WebDriver driver,
			final By by, int timeOutInSeconds) {
		WebElement element;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
			element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
			return element; // return the element
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<WebElement> waitAllElementsVisible(WebDriver driver, final By by,
			int timeOutInSeconds) {
		List<WebElement> elements;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
			elements = driver.findElements(by);
			elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
			return elements; // return the element
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
