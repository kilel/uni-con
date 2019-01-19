package org.github.unicon.web;

import com.sun.corba.se.spi.ior.ObjectKey;
import org.github.unicon.TestUtils;
import org.github.unicon.UniconApplicationTests;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumWebPageTest extends UniconApplicationTests {
    private static final double DELTA = 0.001;

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @Before
    public void init() {
        System.setProperty("webdriver.chrome.driver", "C:/Projects/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void encodeTest() throws InterruptedException {
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

    @Test
    public void escapeTest() throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/escape");

        WebElement sourceValue = driver.findElement(By.id("source-value"));
        Select sourceType = new Select(driver.findElement(By.id("escape-type")));
        WebElement targetValue = driver.findElement(By.id("target-value"));

        // escape
        sourceValue.sendKeys("~!@");
        sourceType.selectByVisibleText("url");
        driver.findElement(By.id("escape-button")).click();

        Thread.sleep(100);

        Assert.assertEquals("%7E%21%40",
                targetValue.getAttribute("value"));

        // unescape
        driver.findElement(By.id("unescape-button")).click();

        Thread.sleep(100);

        Assert.assertEquals("~!@", sourceValue.getAttribute("value"));
    }

    @Test
    public void hashTest() throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/hash");

        WebElement sourceValue = driver.findElement(By.id("source-value"));
        Select sourceType = new Select(driver.findElement(By.id("encode-type")));
        Select targetType = new Select(driver.findElement(By.id("encode-type-tgt")));
        Select hashType = new Select(driver.findElement(By.id("hash-type")));
        WebElement targetValue = driver.findElement(By.id("target-value"));

        // hash
        sourceValue.sendKeys("serdgsrtysrthgbxcv cv r5t");
        sourceType.selectByVisibleText("plain");
        targetType.selectByVisibleText("hex");
        hashType.selectByVisibleText("md5");
        driver.findElement(By.id("hash-button")).click();

        Thread.sleep(100);

        Assert.assertEquals("f6340d9534a3b87669c7abc5106370b4",
                targetValue.getAttribute("value"));
    }

    @Test
    public void dateTest() throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/date");

        // calculate interval
        WebElement sourceDate = driver.findElement(By.id("source-date"));
        WebElement targetDate = driver.findElement(By.id("target-date"));
        Select durationUnit = new Select(driver.findElement(By.id("duration-unit")));
        WebElement durationValue = driver.findElement(By.id("duration-value"));

        sourceDate.clear();
        sourceDate.sendKeys("2019-01-01 00:00:00.000+0300");
        targetDate.clear();
        targetDate.sendKeys("2019-01-02 12:00:00.000+0300");
        durationUnit.selectByVisibleText("day");
        driver.findElement(By.id("convert-to-interval")).click();

        Thread.sleep(300);

        Assert.assertEquals("1.5",
                durationValue.getAttribute("value"));

        // calculate date
        WebElement sourceDate2 = driver.findElement(By.id("source-date-2"));
        Select durationUnit2 = new Select(driver.findElement(By.id("duration-unit-2")));
        WebElement durationValue2 = driver.findElement(By.id("duration-value-2"));
        WebElement targetDate2 = driver.findElement(By.id("target-date-2"));

        sourceDate2.clear();
        sourceDate2.sendKeys("2019-01-01 00:00:00.000+0300");
        durationUnit2.selectByVisibleText("day");
        durationValue2.sendKeys("1.5");
        driver.findElement(By.id("convert-to-date")).click();

        Thread.sleep(300);

        Assert.assertEquals("2019-01-02 12:00:00.000+0300",
                targetDate2.getAttribute("value"));
    }

    @Test
    public void lengthTest() throws InterruptedException {
        measureTest("LENGTH", "456", "meter", "kilometer", "mile",
                BigDecimal.valueOf(0.456), BigDecimal.valueOf(0.28334526));
    }

    @Test
    public void pressureTest() throws InterruptedException {
        measureTest("PRESSURE", "150", "pascal", "bar", "mmhg",
                BigDecimal.valueOf(0.0015), BigDecimal.valueOf(1.12509236));
    }

    @Test
    public void TemperatureTest() throws InterruptedException {
        measureTest("TEMPERATURE", "15", "C", "K", "F",
                BigDecimal.valueOf(288.15), BigDecimal.valueOf(59));
    }

    @Test
    public void weightTest() throws InterruptedException {
        measureTest("WEIGHT", "150", "gram", "kilogram", "centner",
                BigDecimal.valueOf(0.15), BigDecimal.valueOf(0.0015));
    }

    @Test
    public void durationTest() throws InterruptedException {
        measureTest("DURATION", "1500", "millisecond", "second", "microsecond",
                BigDecimal.valueOf(1.5), BigDecimal.valueOf(1500000));
    }

    private void measureTest(String type, String sValue, String sUnit, String tUnit, String backSourceUnit, BigDecimal expected1, BigDecimal expected2) throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/measure?type=" + type);

        WebElement sourceValue = driver.findElement(By.id("source-value"));
        Select sourceUnit = new Select(driver.findElement(By.id("source-unit")));
        Select targetUnit = new Select(driver.findElement(By.id("target-unit")));
        WebElement targetValue = driver.findElement(By.id("target-value"));

        // convert forward
        sourceValue.sendKeys(sValue);
        sourceUnit.selectByVisibleText(sUnit);
        targetUnit.selectByVisibleText(tUnit);
        driver.findElement(By.id("convert-button")).click();

        Thread.sleep(500);

        BigDecimal result = new BigDecimal(targetValue.getAttribute("value"));
        TestUtils.assertNotDifferMuch(expected1,
                result,
                DELTA);

        // convert backward
        sourceUnit.selectByVisibleText(backSourceUnit);
        driver.findElement(By.id("convert-back-button")).click();

        Thread.sleep(500);

        BigDecimal result2 = new BigDecimal(sourceValue.getAttribute("value"));
        TestUtils.assertNotDifferMuch(expected2,
                result2,
                DELTA);
    }

    @After
    public void destroy() {
        driver.quit();
    }
}
