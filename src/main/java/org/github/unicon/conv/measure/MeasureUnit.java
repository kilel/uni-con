package org.github.unicon.conv.measure;

import java.math.BigDecimal;

public interface MeasureUnit {
    BigDecimal getMultiplier();

    String getCode();

    String getDescr();
}
