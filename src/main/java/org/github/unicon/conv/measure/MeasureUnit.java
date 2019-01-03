package org.github.unicon.conv.measure;

import java.math.BigDecimal;

public interface MeasureUnit<T extends MeasureUnit<T>> {
    BigDecimal convert(BigDecimal value, T target);

    default long convert(long value, T target) {
        return convert(BigDecimal.valueOf(value), target).longValue();
    }

    default double convert(double value, T target) {
        return convert(BigDecimal.valueOf(value), target).doubleValue();
    }

    String getCode();

    String getDescr();
}
