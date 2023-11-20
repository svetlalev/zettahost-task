package com.amazon.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.amazon.components.Header;
import com.amazon.pages.HomePage;

public class AmazonCrawler {
    public static void main(String[] args) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        chromeOptions.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(chromeOptions);

        try {
            HomePage homePage = new HomePage(driver);
            homePage.open();
            Header header = new Header(driver);
            Map<String, String> departmentLinks = header.getAllSubsectionsLinks("Shop By Department");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = dateFormat.format(new Date());
            // Create the crawler_results folder if it doesn't exist
            File resultsFolder = new File("crawler_results");
            if (!resultsFolder.exists()) {
                resultsFolder.mkdirs();
            }
            String fileName = "crawler_results/" + timestamp + "_results.txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (Map.Entry<String, String> set :
             departmentLinks.entrySet()) {
                String status = getStatus(set.getValue());
                writer.write(String.format("%s, %s, %s%n", set.getValue(), set.getKey(), status));
             }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Map.Entry<String, String> set :
             departmentLinks.entrySet()) {
                set.getKey();
                set.getValue();
             }
            
        } finally {
            driver.quit();
        }
    }

    private static String getStatus(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.setInstanceFollowRedirects(true); // Follow redirects
            connection.setConnectTimeout(5000); // Set a timeout in milliseconds
            
            connection.connect();
            int responseCode = connection.getResponseCode();
            connection.disconnect();
    
            if (responseCode == 200) {
                return "OK";
            } else {
                return "Dead link";
            }
    
        } catch (IOException e) {
            // An exception occurred, indicating a potential issue with the connection
            return "Dead link";
        }
    }
}
