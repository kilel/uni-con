package org.github.unicon.conv.measure;

import org.github.unicon.UniconApplicationTests;
import org.github.unicon.conv.measure.impl.LengthUnit;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.github.unicon.conv.measure.impl.LengthUnit.*;

public class LengthUnitTest extends UniconApplicationTests {
    private static final double DELTA = 0.001;

    @Test
    public void simpleTest() {
        assertConvert(METER, 150, KILOMETER, 0.15);
        assertConvert(KILOMETER, 20, MILE, 12.4274);
        assertConvert(MILE, 1.5, YARD, 2640);
    }

    @Test
    public void bigTest() {
        assertConvert(YARD, new BigDecimal("20000000000000"), INCH, new BigDecimal("720000000000000"));
        assertConvert(INCH, new BigDecimal("45678200000000"), LEAGUE, new BigDecimal("240310396"));
        assertConvert(LEAGUE, new BigDecimal("150000000000"), FURLONG, new BigDecimal("3600000000000"));
    }

    @Test
    public void littleTest() {
        assertConvert(FURLONG, 0.0100000001, CHAIN, 0.100000001);
        assertConvert(CHAIN, 0.0000001, METER, 0.00000201168);
        assertConvert(CHAIN, 0.100000000000001, FURLONG, 0.0100000000000001);
        assertConvert(FURLONG, new BigDecimal("0.00000024"), LEAGUE, new BigDecimal("0.00000001"));
    }

    @Test
    public void zeroTest() {
        assertConvert(LEAGUE, 0, INCH, 0);
        assertConvert(INCH, 0, YARD, 0);
        assertConvert(YARD, 0, MILE, 0);
    }

    @Test
    public void negativeTest() {
        assertConvert(MILE, -20, FOOT, -105600);
        assertConvert(FOOT, -1879, KILOMETER, -0.5727192);
        assertConvert(KILOMETER, -15.5, METER, -15500);
    }

    private void assertConvert(LengthUnit source, double value, LengthUnit target, double expected) {
        final BigDecimal result = source.convert(BigDecimal.valueOf(value), target);
        Assert.assertEquals(expected, result.doubleValue(),  Math.abs(expected) * DELTA);
    }
    private void assertConvert(LengthUnit source, BigDecimal value, LengthUnit target, BigDecimal expected) {
        final BigDecimal result = source.convert(value, target);
        Assert.assertTrue(checkNotDifferMuch(expected, result, DELTA));
    }

    private boolean checkNotDifferMuch(BigDecimal expected, BigDecimal result, double delta) {
        BigDecimal diff = expected.multiply(BigDecimal.valueOf(delta));
        return (expected.subtract(diff)).compareTo(result) < 0 && (expected.add(diff)).compareTo(result) > 0;
    }

}
