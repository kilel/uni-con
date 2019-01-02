package org.github.unicon.conv.measure;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface ScalableMeasureUnit<T extends ScalableMeasureUnit<T>> extends MeasureUnit<T> {

    BigDecimal getMultiplier();

    @Override
    default BigDecimal convert(BigDecimal value, T target) {
        return value
            .divide(getMultiplier(), RoundingMode.UNNECESSARY)
            .multiply(target.getMultiplier());
    }
}
