package org.github.unicon.conv.measure.impl;

import org.github.unicon.conv.measure.MeasureUnit;

import java.math.BigDecimal;

public enum LengthUnit implements MeasureUnit {
    METER("m", BigDecimal.ONE, "Meter"),
    KILOMETER("km", BigDecimal.valueOf(1000), "Kilometer"),
    FOOT("foot", BigDecimal.valueOf(0.305), "Foot"),
    MILE("mile", BigDecimal.valueOf(1609.344), "Mile"),
    YARD("yard", BigDecimal.valueOf(0.9144), "Yard"),
    INCH("inch", BigDecimal.valueOf(0.0254), "inch"),
    LEAGUE("league", BigDecimal.valueOf(4828.032), "League"),
    FURLONG("furlong", BigDecimal.valueOf(201.16), "Furlong"),
    CHAIN("ch", BigDecimal.valueOf(20.1168), "Chain"),
    ;

    private final String code;
    private final BigDecimal multiplier;
    private final String descr;

    LengthUnit(String code, BigDecimal multiplier, String descr) {
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
