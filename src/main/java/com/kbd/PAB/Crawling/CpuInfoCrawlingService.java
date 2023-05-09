package com.kbd.PAB.Crawling;


import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CpuInfoCrawlingService {
    private Document doc;
    private Elements elements;

    private String url;
    public CpuInfoCrawlingService(String url) {
        this.url = url;
    }

    public ArrayList<String> getInfoFromWebPage() throws IOException {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-application-cache");
        options.addArguments("--disable-cache");
        // Chrome 옵션을 설정합니다.
        options.addArguments("--headless"); // 창을 열지 않고 실행합니다.
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");

        // Chrome 웹 드라이버를 생성합니다.
        WebDriver driver = new ChromeDriver(options);
        ArrayList<String> tempString = new ArrayList<>();

        try {
            // 웹 드라이버를 사용하여 웹 페이지를 로드합니다.
            driver.get(url);

            // 웹 페이지가 로드될 때까지 최대 10초까지 기다립니다.
            driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // class 이름이 "a-size-large product-title-word-break"인 모든 요소를 가져옵니다.
            List<WebElement> elements = driver.findElements(By.className("product-title-word-break"));
            tempString.add(url);

            // 각 요소의 텍스트를 출력합니다.
            for (WebElement element : elements) {
                tempString.add(element.getText());
            }

            // class 이름이 "a-list-item"인 모든 요소를 가져옵니다.
            elements = driver.findElements(By.xpath("//td[@class='a-span9']//span[@class='a-size-base po-break-word']"));

            // 각 요소의 텍스트를 출력합니다.
            for (WebElement element : elements) {
                tempString.add(element.getText());
            }

            // 데이터 수집할 요소 XPath 설정
            elements = driver.findElements(By.xpath("//ul[@class='a-unordered-list a-vertical a-spacing-mini']//li//span[@class='a-list-item']"));

            for (WebElement element : elements) {
                tempString.add(element.getText().trim());
            }

            WebElement colorImages = driver.findElement(By.xpath("//*[@id=\"landingImage\"]"));

            // colorImages 값을 추출합니다.
            tempString.add(colorImages.getAttribute("src"));

            tempString.removeAll(Arrays.asList("", null));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 웹 드라이버를 종료합니다.
            driver.quit();
        }
        return tempString;
    }
}
