package org.github.unicon.web.model;

import org.github.unicon.conv.measure.MeasureType;
import org.github.unicon.conv.measure.MeasureUnit;

import java.util.Map;

public class MeasuresListResponse extends BaseResponse {
    private MeasureType[] types;
    private Map<MeasureType, MeasureUnitDto[]> measureUnits;

    public MeasureType[] getTypes() {
        return types;
    }

    public void setTypes(MeasureType[] types) {
        this.types = types;
    }

    public Map<MeasureType, MeasureUnitDto[]> getMeasureUnits() {
        return measureUnits;
    }

    public void setMeasureUnits(Map<MeasureType, MeasureUnitDto[]> measureUnits) {
        this.measureUnits = measureUnits;
    }
}
