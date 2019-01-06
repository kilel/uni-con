package org.github.unicon.conv.measure;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public interface ScalableMeasureUnit<T extends ScalableMeasureUnit<T>> extends MeasureUnit<T> {

    BigDecimal getMultiplier();

    @Override
    default BigDecimal convert(BigDecimal value, T target) {
        return value
            .multiply(getMultiplier())
            .divide(target.getMultiplier(), MathContext.DECIMAL64)
                .stripTrailingZeros();
    }
}
