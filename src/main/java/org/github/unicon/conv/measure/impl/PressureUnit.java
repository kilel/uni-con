package org.github.unicon.conv.measure.impl;

import org.github.unicon.conv.measure.ScalableMeasureUnit;

import java.math.BigDecimal;

public enum PressureUnit implements ScalableMeasureUnit<PressureUnit> {
    PASCAL("pascal", BigDecimal.ONE, "Pascal"),
    BAR("bar", BigDecimal.valueOf(1_00_000), "Bar"),
    MMHG("mmhg", BigDecimal.valueOf(133.322), "mm HG"),
    ;

    private final String code;
    private final BigDecimal multiplier;
    private final String descr;

    PressureUnit(String code, BigDecimal multiplier, String descr) {
        this.code = code;
        this.multiplier = multiplier;
        this.descr = descr;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public BigDecimal getMultiplier() {
        return multiplier;
    }

    @Override
    public String getDescr() {
        return descr;
    }
}
