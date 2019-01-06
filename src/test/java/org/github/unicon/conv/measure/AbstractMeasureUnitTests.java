package org.github.unicon.conv.measure;

import org.github.unicon.UniconApplicationTests;
import org.junit.Assert;

import java.math.BigDecimal;

public abstract class AbstractMeasureUnitTests extends UniconApplicationTests {
    protected static final double DELTA = 0.001;

    protected <T extends MeasureUnit<T>> void assertConvert(T source, double value, T target, double expected) {
        final BigDecimal result = source.convert(BigDecimal.valueOf(value), target);
        Assert.assertEquals(expected, result.doubleValue(), Math.abs(expected) * DELTA);
    }

    protected <T extends MeasureUnit<T>> void assertConvert(T source, BigDecimal value, T target, BigDecimal expected) {
        final BigDecimal result = source.convert(value, target);
        Assert.assertTrue(checkNotDifferMuch(expected, result, DELTA));
    }

    protected boolean checkNotDifferMuch(BigDecimal expected, BigDecimal result, double delta) {
        BigDecimal diff = expected.multiply(BigDecimal.valueOf(delta)).abs();
        return (expected.subtract(diff)).compareTo(result) < 0 && (expected.add(diff)).compareTo(result) > 0;
    }
}
