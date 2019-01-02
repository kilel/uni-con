package org.github.unicon.conv.measure;

import java.math.BigDecimal;

public class MeasuredValue<T extends MeasureUnit> {
    private final BigDecimal value;
    private final T unit;

    public MeasuredValue(BigDecimal value, T unit) {
        this.value = value;
        this.unit = unit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public T getUnit() {
        return unit;
    }

    public MeasuredValue<T> to(T targetUnit) {
        if (targetUnit == unit) {
            return this;
        }

        return new MeasuredValue<>(
            unit.convert(value, targetUnit),
            targetUnit
        );
    }
}
