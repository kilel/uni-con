package org.github.unicon.conv.measure;

import org.github.unicon.UniconApplicationTests;
import org.github.unicon.conv.measure.impl.WeightUnit;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.github.unicon.conv.measure.impl.WeightUnit.*;

public class WeightUnitTest extends UniconApplicationTests {
    private static final double DELTA = 0.001;

    @Test
    public void simpleTest() {
        assertConvert(GRAM, 150, KILOGRAM, 0.15);
        assertConvert(KILOGRAM, 2056, TONNE, 2.056);
        assertConvert(TONNE, 1.5, CENTNER, 15);
    }

    @Test
    public void bigTest() {
        assertConvert(CENTNER, new BigDecimal("100000"), CARAT, new BigDecimal("50000000000"));
        assertConvert(CARAT, new BigDecimal("100000000000"), POUND, new BigDecimal("44092452.437"));
        assertConvert(POUND, new BigDecimal("1500000000"), STONE, new BigDecimal("107142857.1429"));
    }

    @Test
    public void littleTest() {
        assertConvert(STONE, 0.00000001, OUNCE, 0.00000224);
        assertConvert(OUNCE, 0.0000001, GRAM, 0.00000283495);
        assertConvert(OUNCE, 0.0001, STONE, 0.000000446429);
    }

    @Test
    public void zeroTest() {
        assertConvert(STONE, 0, POUND, 0);
        assertConvert(POUND, 0, CARAT, 0);
        assertConvert(CARAT, 0, CENTNER, 0);
    }

    @Test
    public void negativeTest() {
        assertConvert(CENTNER, -20, TONNE, -2);
        assertConvert(TONNE, -0.15, KILOGRAM, -150);
        assertConvert(KILOGRAM, -18, GRAM, -18000);
    }

    private void assertConvert(WeightUnit source, double value, WeightUnit target, double expected) {
        final BigDecimal result = source.convert(BigDecimal.valueOf(value), target);
        Assert.assertEquals(expected, result.doubleValue(),  Math.abs(expected) * DELTA);
    }
    private void assertConvert(WeightUnit source, BigDecimal value, WeightUnit target, BigDecimal expected) {
        final BigDecimal result = source.convert(value, target);
        Assert.assertTrue(checkNotDifferMuch(expected, result, DELTA));
    }

    private boolean checkNotDifferMuch(BigDecimal expected, BigDecimal result, double delta) {
        BigDecimal diff = expected.multiply(BigDecimal.valueOf(delta));
        return (expected.subtract(diff)).compareTo(result) < 0 && (expected.add(diff)).compareTo(result) > 0;
    }

}
