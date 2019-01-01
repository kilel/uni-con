package org.github.unicon.conv.model;

import java.math.BigDecimal;

public interface MeasureUnit {
    BigDecimal getMultiplier();

    default String getName() {
        return toString();
    }
}
