package org.github.unicon.conv.measure;

import org.github.unicon.UniconApplicationTests;
import org.github.unicon.conv.measure.impl.PressureUnit;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.github.unicon.conv.measure.impl.PressureUnit.*;

public class PressureUnitTest extends UniconApplicationTests {
    private static final double DELTA = 0.001;

    @Test
    public void simpleTest() {
        assertConvert(PASCAL, 150, BAR, 0.0015);
        assertConvert(BAR, 2, MMHG, 1500.12);
        assertConvert(MMHG, 18, PASCAL, 2399.8);
    }

    @Test
    public void bigTest() {
        assertConvert(PASCAL, new BigDecimal("18000000000"), MMHG, new BigDecimal("135011083.65222"));
        assertConvert(MMHG, new BigDecimal("180000000000"), BAR, new BigDecimal("239980297.347"));
        assertConvert(BAR, new BigDecimal("1500000000"), PASCAL, new BigDecimal("150000000000000"));
    }

    @Test
    public void littleTest() {
        assertConvert(PASCAL, 0.00001, BAR, 0.0000000001);
        assertConvert(BAR, 0.000001, MMHG, 0.00075006157585);
        assertConvert(MMHG, 0.000001, PASCAL, 0.000133322387);
    }

    @Test
    public void zeroTest() {
        assertConvert(PASCAL, 0, MMHG, 0);
        assertConvert(MMHG, 0, BAR, 0);
        assertConvert(BAR, 0, PASCAL, 0);
    }

    @Test
    public void negativeTest() {
        assertConvert(PASCAL, -20, MMHG, -0.150012);
        assertConvert(BAR, -20, PASCAL, -2000000);
        assertConvert(BAR, -15.5, MMHG, -11625.95);
    }

    private void assertConvert(PressureUnit source, double value, PressureUnit target, double expected) {
        final BigDecimal result = source.convert(BigDecimal.valueOf(value), target);
        Assert.assertEquals(expected, result.doubleValue(),  Math.abs(expected) * DELTA);
    }
    private void assertConvert(PressureUnit source, BigDecimal value, PressureUnit target, BigDecimal expected) {
        final BigDecimal result = source.convert(value, target);
        Assert.assertTrue(checkNotDifferMuch(expected, result, DELTA));
    }

    private boolean checkNotDifferMuch(BigDecimal expected, BigDecimal result, double delta) {
        BigDecimal diff = expected.multiply(BigDecimal.valueOf(delta));
        return (expected.subtract(diff)).compareTo(result) < 0 && (expected.add(diff)).compareTo(result) > 0;
    }

}