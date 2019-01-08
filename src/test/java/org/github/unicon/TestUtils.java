package org.github.unicon;

import org.junit.Assert;

import java.math.BigDecimal;

public class TestUtils {
    public static void assertNotDifferMuch(BigDecimal expected, BigDecimal result, double delta) {
        BigDecimal diff = expected.multiply(BigDecimal.valueOf(delta)).abs();
        Assert.assertTrue((expected.subtract(diff)).compareTo(result) < 0 && (expected.add(diff)).compareTo(result) > 0);
    }
}
