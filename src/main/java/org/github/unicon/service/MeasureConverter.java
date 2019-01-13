package org.github.unicon.service;

import org.github.unicon.model.measure.MeasureUnit;
import org.github.unicon.model.measure.MeasuredValue;

import java.math.BigDecimal;

public interface MeasureConverter<T extends MeasureUnit> {
    BigDecimal convert(BigDecimal value, T source, T target);

    default long convert(long value, T source, T target) {
        return convert(BigDecimal.valueOf(value), source, target).longValue();
    }

    default double convert(double value, T source, T target) {
        return convert(BigDecimal.valueOf(value), source, target).doubleValue();
    }

    default MeasuredValue<T> convert(MeasuredValue<T> value, T targetUnit) {
        if (targetUnit == value.getUnit()) {
            return value;
        }

        return new MeasuredValue<T>(
            convert(value.getValue(), value.getUnit(), targetUnit),
            targetUnit
        );
    }
}
