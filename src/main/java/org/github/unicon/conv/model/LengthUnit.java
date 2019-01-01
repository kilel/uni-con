package org.github.unicon.conv.model;

import java.math.BigDecimal;

public enum LengthUnit implements MeasureUnit {
    METER(BigDecimal.ONE),
    FOOT(BigDecimal.valueOf(0.305)),
    MILE(BigDecimal.valueOf(1609)),
    ;

    private final BigDecimal multiplier;

    LengthUnit(BigDecimal multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public BigDecimal getMultiplier() {
        return multiplier;
    }
}
