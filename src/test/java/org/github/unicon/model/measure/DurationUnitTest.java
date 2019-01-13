package org.github.unicon.model.measure;

import org.junit.Test;

import java.math.BigDecimal;

import static org.github.unicon.model.measure.unit.DurationUnit.*;

public class DurationUnitTest extends AbstractMeasureUnitTests {


    @Test
    public void simpleTest() {
        assertConvert(NANOSECOND, 150, MICROSECOND, 0.15);
        assertConvert(MICROSECOND, 2056, MILLISECOND, 2.056);
        assertConvert(MILLISECOND, 1.5, SECOND, 0.0015);
        assertConvert(SECOND, 5, MINUTE, 0.0833333);
        assertConvert(MINUTE, 30, HOUR, 0.5);
        assertConvert(HOUR, 4.8, DAY, 0.2);
    }

    @Test
    public void bigTest() {
        assertConvert(NANOSECOND, new BigDecimal("1654651065465132132"), MICROSECOND, new BigDecimal("1654651065465132.132"));
        assertConvert(MICROSECOND, new BigDecimal("6262656516542654"), MILLISECOND, new BigDecimal("6262656516542.654"));
        assertConvert(MILLISECOND, new BigDecimal("565616544604165165"), SECOND, new BigDecimal("565616544604165.165"));
        assertConvert(SECOND, new BigDecimal("150000000000000000000000"), MINUTE, new BigDecimal("2.5e+21"));
        assertConvert(MINUTE, new BigDecimal("30000000000000000000"), HOUR, new BigDecimal("500000000000000000"));
        assertConvert(HOUR, new BigDecimal("5416160565416516541"), DAY, new BigDecimal("225673356892354848"));
    }

    @Test
    public void littleTest() {
        assertConvert(NANOSECOND, 150, MICROSECOND, 0.15);
        assertConvert(MICROSECOND, 2056, MILLISECOND, 2.056);
        assertConvert(SECOND, 5, MINUTE, 0.0833333);
        assertConvert(MINUTE, 30, HOUR, 0.5);
        assertConvert(HOUR, 4.8, DAY, 0.2);
        assertConvert(SECOND, new BigDecimal("0.00100100000000000000000001"), MILLISECOND, new BigDecimal("1.0009999999999998899"));
    }

    @Test
    public void zeroTest() {
        assertConvert(NANOSECOND, 0, MICROSECOND, 0);
        assertConvert(MICROSECOND, 0, MILLISECOND, 0);
        assertConvert(MILLISECOND, 0, SECOND, 0);
        assertConvert(SECOND, 0, MINUTE, 0);
        assertConvert(MINUTE, 0, HOUR, 0);
        assertConvert(HOUR, 0, DAY, 0);
    }

    @Test
    public void negativeTest() {
        assertConvert(NANOSECOND, -150, MICROSECOND, -0.15);
        assertConvert(MICROSECOND, -2056, MILLISECOND, -2.056);
        assertConvert(MILLISECOND, -1.5, SECOND, -0.0015);
        assertConvert(SECOND, -5, MINUTE, -0.0833333);
        assertConvert(MINUTE, -30, HOUR, -0.5);
        assertConvert(HOUR, -4.8, DAY, -0.2);
    }


}
