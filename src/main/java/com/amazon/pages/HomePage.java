package com.amazon.pages;

import org.openqa.selenium.WebDriver;

import com.amazon.core.Base;

public class HomePage extends Base {
    protected String url = "https://www.amazon.com";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(url);
        driver.navigate().refresh(); // needed as sometimes page does not load properly
    }
}
