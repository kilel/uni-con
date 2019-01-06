package org.github.unicon.conv.measure;

import org.github.unicon.UniconApplicationTests;
import org.github.unicon.conv.measure.impl.TemperatureUnit;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.github.unicon.conv.measure.impl.TemperatureUnit.*;

public class TemperatureUnitTest extends UniconApplicationTests {
    private static final double DELTA = 0.001;

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

    private void assertConvert(TemperatureUnit source, double value, TemperatureUnit target, double expected) {
        final BigDecimal result = source.convert(BigDecimal.valueOf(value), target);
        Assert.assertEquals(expected, result.doubleValue(),  Math.abs(expected) * DELTA);
    }
    private void assertConvert(TemperatureUnit source, BigDecimal value, TemperatureUnit target, BigDecimal expected) {
        final BigDecimal result = source.convert(value, target);
        Assert.assertTrue(checkNotDifferMuch(expected, result, DELTA));
    }

    private boolean checkNotDifferMuch(BigDecimal expected, BigDecimal result, double delta) {
        BigDecimal diff = expected.multiply(BigDecimal.valueOf(delta));
        return (expected.subtract(diff)).compareTo(result) < 0 && (expected.add(diff)).compareTo(result) > 0;
    }

}
