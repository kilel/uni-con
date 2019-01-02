package org.github.unicon.conv.measure.impl;

import org.github.unicon.conv.measure.MeasureUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum TemperatureUnit implements MeasureUnit<TemperatureUnit> {
    CELSIUS("C", "Celsius"),
    KELVIN("K", "Kilometer"),
    FAHRENHEIT("F", "Fahrenheit"),
    ;

    private final String code;
    private final String descr;

    TemperatureUnit(String code, String descr) {
        this.code = code;
        this.descr = descr;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescr() {
        return descr;
    }

    @Override
    public BigDecimal convert(BigDecimal value, TemperatureUnit target) {
        return target.fromC(toC(value));
    }

    private BigDecimal toC(BigDecimal value) {
        switch (this) {
            case CELSIUS:
                return value;
            case KELVIN:
                return value.add(BigDecimal.valueOf(273.15));
            case FAHRENHEIT:
                return value
                    .subtract(BigDecimal.valueOf(32))
                    .multiply(BigDecimal.valueOf(5))
                    .divide(BigDecimal.valueOf(9), RoundingMode.FLOOR);
            default:
                throw new IllegalArgumentException(String.format("Convert [%s] to C is not supported", getCode()));
        }
    }

    private BigDecimal fromC(BigDecimal value) {
        switch (this) {
            case CELSIUS:
                return value;
            case KELVIN:
                return value.subtract(BigDecimal.valueOf(273.15));
            case FAHRENHEIT:
                return value
                    .multiply(BigDecimal.valueOf(9))
                    .divide(BigDecimal.valueOf(5), RoundingMode.FLOOR)
                    .add(BigDecimal.valueOf(32));
            default:
                throw new IllegalArgumentException(String.format("Convert [%s] to C is not supported", getCode()));
        }
    }
}
