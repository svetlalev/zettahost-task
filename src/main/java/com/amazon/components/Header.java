package com.amazon.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.amazon.core.Base;

public class Header extends Base {
    protected By hamburgerMenuButton = By.id("nav-hamburger-menu");
    // firstMenuSelector introduced as when opened with Selenium, hmenu gets duplicated for some reason.
    protected String firstMenuSelector = "(//div[@id=\"hmenu-content\"])[1]";
    protected By mainMenuTitles = By.cssSelector(".hmenu-visible>li>div.hmenu-title");
    protected By seeAllLinks = By.xpath(firstMenuSelector + "//div[text()=\"see all\"]");
    protected By seeLessLinks = By.xpath(firstMenuSelector + "//div[text()=\"see less\"]");
    protected By menuItems = By.xpath(firstMenuSelector + "//ul[contains(@class, \"hmenu-visible\")]//*[contains(@class,\"hmenu-item\")]");
    protected By lastItemsInSection = By.xpath("//ul[@class=\"hmenu-compress-section\"]/li[last()]");
    protected By vibileMenuItems = By.cssSelector("ul.hmenu-visible li a:not(.hmenu-back-button)");
    protected By backToMainMenuButton = By.xpath("(//ul[contains(@class, 'hmenu-visible')]//a[contains(@class, 'hmenu-back-button')])[2]");

    public Header(WebDriver driver) {
        super(driver);
    }

    public Map<String, String> getAllSubsectionsLinks(String sectionName) {
        clickHamburgerMenu();
        Map<String, String> menuItems = new HashMap<String, String>();
        ArrayList<String> subsections = getAllSubsections(sectionName);
        for (String subsection : subsections ) {
            openSubsection(subsection);

            List<WebElement> subsectionItems = driver.findElements(vibileMenuItems);
            for (WebElement subsectionItem : subsectionItems) {
                String text = subsectionItem.getText();
                String link = subsectionItem.getAttribute("href");
                menuItems.put(text, link);
            }
            driver.navigate().refresh();
        }

        return menuItems;
    }
    
    public ArrayList<String> getAllSubsections(String sectionName) {
        clickHamburgerMenu();

        expandAllSections();
        
        List<WebElement> menuItemsElements = driver.findElements(menuItems);
        ArrayList<String> menuItemsTexts = new ArrayList<String>();
        for (WebElement menuItem : menuItemsElements) {
            menuItemsTexts.add(menuItem.getText());
        }

        int sectionIndex = menuItemsTexts.indexOf(sectionName);

        ArrayList<String> sectionItems = new ArrayList<String>();
        for (int i = (sectionIndex + 1); i < menuItemsTexts.size() && !menuItemsTexts.get(i).isEmpty(); i++) {
            sectionItems.add(menuItemsTexts.get(i));
        }

        return sectionItems;
    }

    public void openSubsection(String subsectionName) {
        clickHamburgerMenu();
        if (!isElementPresent(driver, By.linkText(subsectionName))) {
            expandAllSections();
        }
        WebElement subsection = driver.findElement(By.linkText(subsectionName));
        String menuID = subsection.getAttribute("data-menu-id");
        subsection.click();
        waitForElementPresent(driver, By.cssSelector("ul[data-menu-id=\"" + menuID + "\"]"), 2);
    }

    public void expandAllSections() {
        List<WebElement> seeAllLinksElements = driver.findElements(seeAllLinks);

        for (WebElement seeAllLink : seeAllLinksElements) {
            seeAllLink.click();
        }
        waitAllElementsVisible(driver, lastItemsInSection, 5);
        waitForElementPresent(driver, seeLessLinks, 5);
    }
    
    public void clickHamburgerMenu() {
        if (!isElementPresent(driver, By.xpath(firstMenuSelector))) {
            waitForElementPresent(driver, hamburgerMenuButton, 5);
            driver.findElement(hamburgerMenuButton).click();
            waitForElementPresent(driver, seeAllLinks, 5);
            waitForElementPresent(driver, By.cssSelector(".hmenu-visible>li>div.hmenu-title"), 5);
            waitForElementPresent(driver, mainMenuTitles, 5);
        }
    }
}
