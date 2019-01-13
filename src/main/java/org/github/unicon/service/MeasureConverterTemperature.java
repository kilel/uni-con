package org.github.unicon.service;

import org.github.unicon.model.measure.unit.TemperatureUnit;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MeasureConverterTemperature implements MeasureConverter<TemperatureUnit> {
    @Override
    public BigDecimal convert(BigDecimal value, TemperatureUnit source, TemperatureUnit target) {
        return cToX(xToC(value, source), target);
    }

    private BigDecimal xToC(BigDecimal value, TemperatureUnit unit) {
        switch (unit) {
            case CELSIUS:
                return value;
            case KELVIN:
                return value.subtract(BigDecimal.valueOf(273.15));
            case FAHRENHEIT:
                return value
                    .subtract(BigDecimal.valueOf(32))
                    .multiply(BigDecimal.valueOf(5))
                    .divide(BigDecimal.valueOf(9), MathContext.DECIMAL64);
            default:
                throw new IllegalArgumentException(String.format("Convert [%s] to C is not supported",
                    unit.getCode()));
        }
    }

    private BigDecimal cToX(BigDecimal value, TemperatureUnit unit) {
        switch (unit) {
            case CELSIUS:
                return value;
            case KELVIN:
                return value.add(BigDecimal.valueOf(273.15));
            case FAHRENHEIT:
                return value
                    .multiply(BigDecimal.valueOf(9))
                    .divide(BigDecimal.valueOf(5), RoundingMode.FLOOR)
                    .add(BigDecimal.valueOf(32));
            default:
                throw new IllegalArgumentException(String.format("Convert [%s] to C is not supported",
                    unit.getCode()));
        }
    }
}
