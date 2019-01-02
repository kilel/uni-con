package org.github.unicon.conv.measure;

import java.math.BigDecimal;

public interface MeasureUnit<T extends MeasureUnit<T>> {
    BigDecimal convert(BigDecimal value, T target);

    String getCode();

    String getDescr();
}
