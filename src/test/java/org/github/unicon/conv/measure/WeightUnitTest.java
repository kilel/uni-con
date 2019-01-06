package org.github.unicon.conv.measure;

import org.junit.Test;

import java.math.BigDecimal;

import static org.github.unicon.conv.measure.impl.WeightUnit.*;

public class WeightUnitTest extends AbstractMeasureUnitTests {


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
        assertConvert(POUND, new BigDecimal("155610654051654651"), STONE, new BigDecimal("11115046717975332"));
    }

    @Test
    public void littleTest() {
        assertConvert(STONE, 0.00000001, OUNCE, 0.00000224);
        assertConvert(OUNCE, 0.0000001, GRAM, 0.00000283495);
        assertConvert(OUNCE, 0.0001, STONE, 0.000000446429);
        assertConvert(STONE, new BigDecimal("0.0000010000000000100000511"), OUNCE, new BigDecimal("0.00022400000000223998543"));
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


}
