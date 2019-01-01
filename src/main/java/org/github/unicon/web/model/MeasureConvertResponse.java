package org.github.unicon.web.model;

import org.github.unicon.conv.model.MeasureUnit;
import org.github.unicon.conv.model.MeasureType;

import java.math.BigDecimal;

public class MeasureConvertResponse extends BaseResponse {
    private MeasureType type;
    private BigDecimal value;
    private MeasureUnit unit;

    public MeasureType getType() {
        return type;
    }

    public void setType(MeasureType type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public MeasureUnit getUnit() {
        return unit;
    }

    public void setUnit(MeasureUnit unit) {
        this.unit = unit;
    }
}
