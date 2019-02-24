package org.github.unicon.web;

import org.apache.commons.lang3.SystemUtils;
import org.github.unicon.TestUtils;
import org.github.unicon.UniconApplicationTests;
import org.github.unicon.model.measure.MeasureType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumWebPageTest extends UniconApplicationTests {
    private static final double DELTA = 0.001;

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @Before
    public void init() {
        if (SystemUtils.IS_OS_WINDOWS) {
            System.setProperty("webdriver.chrome.driver", "selenium/win32/chromedriver.exe");
        } else if (SystemUtils.IS_OS_LINUX) {
            System.setProperty("webdriver.chrome.driver", "selenium/linux64/chromedriver");
        } else if (SystemUtils.IS_OS_MAC) {
            System.setProperty("webdriver.chrome.driver", "selenium/mac63/chromedriver");
        }
        driver = new ChromeDriver();
    }

    @Test
    public void encodeTest() throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/encode");
        pageCommonDataCheck("Text encoder");

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

        assertEquals(
                "111001101101111011011010110010100100000011101000110010101110011011101000010000001110100011001010111100001110100",
                targetValue.getAttribute("value"));

        // convert backward
        sourceType.selectByVisibleText("hex");
        driver.findElement(By.id("encode-back-button")).click();

        Thread.sleep(100);

        assertEquals("736f6d6520746573742074657874", sourceValue.getAttribute("value"));
    }

    @Test
    public void escapeTest() throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/escape");
        pageCommonDataCheck("Text escaper");

        WebElement sourceValue = driver.findElement(By.id("source-value"));
        Select sourceType = new Select(driver.findElement(By.id("escape-type")));
        WebElement targetValue = driver.findElement(By.id("target-value"));

        // escape
        sourceValue.sendKeys("~!@");
        sourceType.selectByVisibleText("url");
        driver.findElement(By.id("escape-button")).click();

        Thread.sleep(100);

        assertEquals("%7E%21%40",
                targetValue.getAttribute("value"));

        // unescape
        driver.findElement(By.id("unescape-button")).click();

        Thread.sleep(100);

        assertEquals("~!@", sourceValue.getAttribute("value"));
    }

    @Test
    public void hashTest() throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/hash");
        pageCommonDataCheck("Text hasher");

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

        assertEquals("f6340d9534a3b87669c7abc5106370b4",
                targetValue.getAttribute("value"));
    }

    @Test
    public void dateTest() throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/date");
        pageCommonDataCheck("Date converter");

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

        assertEquals("1.5",
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

        assertEquals("2019-01-02 12:00:00.000+0300",
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
    public void temperatureTest() throws InterruptedException {
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

    @Test
    public void togglerTest() throws InterruptedException {
        driver.get("http://localhost:" + port);
        Dimension originalSize = driver.manage().window().getSize();

        try {
            driver.manage().window().setSize(new Dimension(500, 500));
            WebElement menu = driver.findElement(By.id("navbarSupportedContent"));
            WebElement toggler = driver.findElement(By.id("navbar-toggler"));
            assertTrue(toggler.isDisplayed());
            assertFalse(menu.isDisplayed());

            toggler.click();
            assertTrue(menu.isDisplayed());

            Thread.sleep(1000);
            toggler.click();
            Thread.sleep(1000);
            assertFalse(menu.isDisplayed());
        } finally {
            driver.manage().window().setSize(originalSize);
        }
    }

    @Test
    public void checkLinksWorkTest() {
        driver.get("http://localhost:" + port);
        driver.findElement(By.id("main-page-ref")).click();
        assertEquals("Universal converter", driver.getTitle());
//        checkLink("Universal Converter", "Universal Converter");

        checkLink("Date", "Date converter");

        List<String> measureTypes = Arrays.stream(MeasureType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        for (String measureType : measureTypes) {
            driver.findElement(By.id("measure-menu-button")).click();
            checkLink(measureType, measureType + " converter");
        }

        checkLink("Date", "Date converter");

        driver.findElement(By.id("text-menu-button")).click();
        checkLink("Hash", "Text hasher");
        driver.findElement(By.id("text-menu-button")).click();
        checkLink("Escape", "Text escaper");
        driver.findElement(By.id("text-menu-button")).click();
        checkLink("Encode", "Text encoder");
    }

    @Test
    public void measureWrongValueTest() throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/measure?type=LENGTH");
        // check converting
        wrongValueTest(
                "source-value",
                "target-value",
                "1",
                "a",
                "convert-button",
                "Failed to convert value of type 'java.lang.String' to required type 'java.math.BigDecimal'");

        // check converting back
        wrongValueTest(
                "target-value",
                "source-value",
                "1",
                "a",
                "convert-back-button",
                "Failed to convert value of type 'java.lang.String' to required type 'java.math.BigDecimal'"
        );
    }

    @Test
    public void dateWrongValueTest() throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/date");

        // check interval calculating with wrong source date
        wrongValueTest(
                "source-date",
                "duration-value",
                "2018-12-31 13:55:02.255+0300",
                "a",
                "convert-to-interval",
                "Unparseable date: \"a\""
        );

        // check interval calculating with wrong target date
        WebElement sourceDate = driver.findElement(By.id("source-date"));
        sourceDate.clear();
        sourceDate.sendKeys("2018-12-31 13:55:02.255+0300");
        wrongValueTest(
                "target-date",
                "duration-value",
                "2019-01-03 20:04:71.427+0300",
                "a",
                "convert-to-interval",
                "Unparseable date: \"a\""
        );

        // check date calculating
        wrongValueTest(
                "source-date-2",
                "target-date-2",
                "2018-12-31 13:55:02.255+0300",
                "a",
                "convert-to-date",
                "Unparseable date: \"a\""
        );

    }

    @Test
    public void hashWrongValueTest() throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/hash");
        new Select(driver.findElement(By.id("encode-type"))).selectByVisibleText("bin");
        wrongValueTest(
                "source-value",
                "target-value",
                "10",
                "20",
                "hash-button",
                "Failed to get response! For input string: \"20\""
                );

    }

    @Test
    public void encodeWrongValueTest() throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/encode");
        new Select(driver.findElement(By.id("source-type"))).selectByVisibleText("bin");
        new Select(driver.findElement(By.id("target-type"))).selectByVisibleText("bin");

        // check encode
        wrongValueTest(
                "source-value",
                "target-value",
                "10",
                "20",
                "encode-button",
                "Failed to get response! For input string: \"20\""
        );

        // check encode back
        wrongValueTest(
                "target-value",
                "source-value",
                "10",
                "20",
                "encode-back-button",
                "Failed to get response! For input string: \"20\""
        );
    }

    private void measureTest(String type,
                             String sValue,
                             String sUnit,
                             String tUnit,
                             String backSourceUnit,
                             BigDecimal expected1,
                             BigDecimal expected2) throws InterruptedException {
        driver.get("http://localhost:" + port + "/convert/measure?type=" + type);
        pageCommonDataCheck(type + " converter");

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

    private void pageCommonDataCheck(String expectedTitle) {
        assertEquals(expectedTitle, driver.getTitle());

        int year = LocalDate.now().getYear();
        WebElement footer = driver.findElement(By.id("footer-text"));
        assertTrue(footer.isDisplayed());
        assertEquals("Universal converter - " + year, footer.getText());

        WebElement header = driver.findElement(By.id("page-header"));
        assertTrue(header.isDisplayed());

        // check home button is visible
        assertTrue(header.findElement(By.id("main-page-ref")).isDisplayed());

        // check date button is visible
        assertTrue(header.findElement(By.id("data-button")).isDisplayed());

        // check measure menu is visible
        WebElement measureMenuButton = header.findElement(By.id("measure-menu-button"));
        assertTrue(measureMenuButton.isDisplayed());
        WebElement measureMenu = header.findElement(By.id("measure-menu"));
        List<String> expectedMeasureTypes = Arrays.stream(MeasureType.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        checkDropdownMenuVisible(measureMenu, expectedMeasureTypes, false);

        measureMenuButton.click();
        checkDropdownMenuVisible(measureMenu, expectedMeasureTypes, true);

        measureMenuButton.click();
        checkDropdownMenuVisible(measureMenu, expectedMeasureTypes, false);

        // check text menu is visible
        WebElement textMenuButton = header.findElement(By.id("text-menu-button"));
        assertTrue(textMenuButton.isDisplayed());
        WebElement textMenu = header.findElement(By.id("text-menu"));
        List<String> expectedTextTypes = Arrays.asList("Hash", "Encode", "Escape");

        checkDropdownMenuVisible(textMenu, expectedTextTypes, false);

        textMenuButton.click();
        checkDropdownMenuVisible(textMenu, expectedTextTypes, true);

        textMenuButton.click();
        checkDropdownMenuVisible(textMenu, expectedTextTypes, false);
    }

    private void checkDropdownMenuVisible(WebElement menu, List<String> expectedItems, boolean isVisible) {

        assertEquals(isVisible, menu.isDisplayed());

        List<String> elements = menu.findElements(By.tagName("a")).stream()
                .filter(x -> x.isDisplayed() == isVisible)
                .map(WebElement::getText)
                .collect(Collectors.toList());

        assertEquals(expectedItems.size(), elements.size());
        if (!isVisible) {
            return;
        }

        for (String item : expectedItems) {
            assertTrue(elements.contains(item));
        }
    }

    private void checkLink(String link, String expectedTitle) {
        driver.findElement(By.linkText(link)).click();
        assertEquals(expectedTitle, driver.getTitle());
    }

    private void wrongValueTest(String sourceID,
                                String targetID,
                                String rightValue,
                                String wrongValue,
                                String buttonID,
                                String alertText) throws InterruptedException {

        WebElement source = driver.findElement(By.id(sourceID));
        WebElement target = driver.findElement(By.id(targetID));

        source.clear();
        source.sendKeys(rightValue);
        driver.findElement(By.id(buttonID)).click();

        source.clear();
        source.sendKeys(wrongValue);
        driver.findElement(By.id(buttonID)).click();
        Thread.sleep(300);
        Alert alert = ExpectedConditions.alertIsPresent().apply(driver);
        assertTrue(alert.getText().contains(alertText));
        alert.accept();
        assertTrue(target.getText().isEmpty());
    }

    @After
    public void destroy() {
        driver.quit();
    }
}
