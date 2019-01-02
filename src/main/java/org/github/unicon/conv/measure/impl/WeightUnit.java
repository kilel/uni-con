package org.github.unicon.conv.measure.impl;

import org.github.unicon.conv.measure.ScalableMeasureUnit;

import java.math.BigDecimal;

public enum WeightUnit implements ScalableMeasureUnit<WeightUnit> {
    GRAM("gram", BigDecimal.ONE, "Gram"),
    KILOGRAM("kilogram", BigDecimal.valueOf(1_000), "Kilogram"),
    TONNE("tonne", BigDecimal.valueOf(1_000_000), "Tonne"),
    CENTNER("centner", BigDecimal.valueOf(1_00_000), "Centener"),
    CARAT("carat", BigDecimal.valueOf(0.02), "Carat"),
    POUND("pound", BigDecimal.valueOf(453.59237), "Pound"),
    STONE("stone", BigDecimal.valueOf(6350.29318), "Stone"),
    OUNCE("ounce", BigDecimal.valueOf(28.349523125), "Stone"),
    ;

    private final String code;
    private final BigDecimal multiplier;
    private final String descr;

    WeightUnit(String code, BigDecimal multiplier, String descr) {
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
