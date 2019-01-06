package org.github.unicon.conv.measure;

import org.junit.Test;

import java.math.BigDecimal;

import static org.github.unicon.conv.measure.impl.TemperatureUnit.*;

public class TemperatureUnitTest extends AbstractMeasureUnitTests {

    @Test
    public void simpleTest() {
        assertConvert(CELSIUS, 15, KELVIN, 288.15);
        assertConvert(KELVIN, 2000, FAHRENHEIT, 3140.33);
        assertConvert(FAHRENHEIT, 50.5, CELSIUS, 10.27778);
    }

    @Test
    public void bigTest() {
        assertConvert(CELSIUS, new BigDecimal("100000000"), FAHRENHEIT, new BigDecimal("180000032"));
        assertConvert(FAHRENHEIT, new BigDecimal("100000000"), KELVIN, new BigDecimal("55555810.928"));
        assertConvert(KELVIN, new BigDecimal("100500000"), CELSIUS, new BigDecimal("100499726.85"));
    }

    @Test
    public void littleTest() {
        assertConvert(CELSIUS, 0.01000001, KELVIN, 273.16000001);
        assertConvert(KELVIN, 0.0001, FAHRENHEIT, -459.66982);
        assertConvert(FAHRENHEIT, 0.0001, CELSIUS, -17.7777222);
        assertConvert(FAHRENHEIT, new BigDecimal("0.00000016511000154"), CELSIUS, new BigDecimal("-17.777777686050001194"));
    }

    @Test
    public void zeroTest() {
        assertConvert(CELSIUS, 0, FAHRENHEIT, 32);
        assertConvert(FAHRENHEIT, 0, KELVIN, 255.372);
        assertConvert(KELVIN, 0, CELSIUS, -273.15);
    }

    @Test
    public void negativeTest() {
        assertConvert(CELSIUS, -2000, KELVIN, -1726.85);
        assertConvert(KELVIN, -1879, FAHRENHEIT, -3841.87);
        assertConvert(FAHRENHEIT, -1541, CELSIUS, -873.8889);
    }
}
