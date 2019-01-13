package org.github.unicon.web.api.model;

import org.github.unicon.model.measure.MeasureType;
import org.github.unicon.model.measure.MeasureUnit;
import org.github.unicon.model.measure.MeasuredValue;

import java.math.BigDecimal;

public class MeasureConvertResponse extends BaseResponse {
    private MeasureType type;
    private BigDecimal value;
    private MeasureUnitDto unit;

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

    public MeasureUnitDto getUnit() {
        return unit;
    }

    public void setUnit(MeasureUnitDto unit) {
        this.unit = unit;
    }

    public static MeasureConvertResponse build(MeasureType type, MeasuredValue<MeasureUnit> result) {
        final MeasureConvertResponse response = new MeasureConvertResponse();
        response.setType(type);
        response.setUnit(MeasureUnitDto.build(result.getUnit()));
        response.setValue(result.getValue());
        return response;
    }
}
