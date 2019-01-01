package org.github.unicon.conv.model;

import java.math.BigDecimal;

public enum WeightUnit implements MeasureUnit {
    GRAMM(BigDecimal.ONE),
    POUND(BigDecimal.valueOf(0.00220462)),
    STONE(BigDecimal.valueOf(0.000157473)),
    ;


    private final BigDecimal multiplier;

    WeightUnit(BigDecimal multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public BigDecimal getMultiplier() {
        return null;
    }
}
