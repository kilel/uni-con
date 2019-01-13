package org.github.unicon.web;

import org.github.unicon.UniconApplicationTests;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumWebPageTest extends UniconApplicationTests {
    @LocalServerPort
    private int port;

    private WebDriver driver;

    @Before
    public void init() {
        System.setProperty("webdriver.chrome.driver", "C:/Projects/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void test() throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/encode");

        WebElement sourceValue = driver.findElement(By.id("source-value"));
        Select sourceType = new Select(driver.findElement(By.id("source-type")));
        Select targetType = new Select(driver.findElement(By.id("target-type")));
        WebElement targetValue = driver.findElement(By.id("target-value"));

        // convert forward
        sourceValue.sendKeys("some test text");
        sourceType.selectByVisibleText("plain");
        targetType.selectByVisibleText("bin");
        driver.findElement(By.id("encode-button")).click();

        Thread.sleep(100);

        Assert.assertEquals("111001101101111011011010110010100100000011101000110010101110011011101000010000001110100011001010111100001110100",
                targetValue.getAttribute("value"));

        // convert backward
        sourceType.selectByVisibleText("hex");
        driver.findElement(By.id("encode-back-button")).click();

        Thread.sleep(100);

        Assert.assertEquals("736f6d6520746573742074657874", sourceValue.getAttribute("value"));
    }

    @After
    public void destroy() {
        driver.quit();
    }
}
