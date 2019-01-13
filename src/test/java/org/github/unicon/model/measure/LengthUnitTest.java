package org.github.unicon.model.measure;

import org.github.unicon.model.measure.unit.LengthUnit;
import org.junit.Test;

import java.math.BigDecimal;

public class LengthUnitTest extends AbstractMeasureUnitTests {

    @Test
    public void simpleTest() {
        assertConvert(LengthUnit.METER, 150, LengthUnit.KILOMETER, 0.15);
        assertConvert(LengthUnit.KILOMETER, 20, LengthUnit.MILE, 12.4274);
        assertConvert(LengthUnit.MILE, 1.5, LengthUnit.YARD, 2640);
    }

    @Test
    public void bigTest() {
        assertConvert(LengthUnit.YARD, new BigDecimal("20000000000000"), LengthUnit.INCH, new BigDecimal("720000000000000"));
        assertConvert(LengthUnit.INCH, new BigDecimal("45678200000000"), LengthUnit.LEAGUE, new BigDecimal("240310396"));
        assertConvert(LengthUnit.LEAGUE, new BigDecimal("150000000000"), LengthUnit.FURLONG, new BigDecimal("3600000000000"));
    }

    @Test
    public void littleTest() {
        assertConvert(LengthUnit.FURLONG, 0.0100000001, LengthUnit.CHAIN, 0.100000001);
        assertConvert(LengthUnit.CHAIN, 0.0000001, LengthUnit.METER, 0.00000201168);
        assertConvert(LengthUnit.CHAIN, 0.100000000000001, LengthUnit.FURLONG, 0.0100000000000001);
        assertConvert(LengthUnit.FURLONG, new BigDecimal("0.00000024"), LengthUnit.LEAGUE, new BigDecimal("0.00000001"));
    }

    @Test
    public void zeroTest() {
        assertConvert(LengthUnit.LEAGUE, 0, LengthUnit.INCH, 0);
        assertConvert(LengthUnit.INCH, 0, LengthUnit.YARD, 0);
        assertConvert(LengthUnit.YARD, 0, LengthUnit.MILE, 0);
    }

    @Test
    public void negativeTest() {
        assertConvert(LengthUnit.MILE, -20, LengthUnit.FOOT, -105600);
        assertConvert(LengthUnit.FOOT, -1879, LengthUnit.KILOMETER, -0.5727192);
        assertConvert(LengthUnit.KILOMETER, -15.5, LengthUnit.METER, -15500);
    }

}
