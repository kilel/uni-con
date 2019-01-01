package org.github.unicon.web.model;

import org.github.unicon.conv.model.MeasureType;
import org.github.unicon.conv.model.MeasureUnit;

import java.util.Map;

public class MeasuresListResponse extends BaseResponse {
    private MeasureType[] types;
    private Map<MeasureType, MeasureUnit[]> measureUnits;

    public MeasureType[] getTypes() {
        return types;
    }

    public void setTypes(MeasureType[] types) {
        this.types = types;
    }

    public Map<MeasureType, MeasureUnit[]> getMeasureUnits() {
        return measureUnits;
    }

    public void setMeasureUnits(Map<MeasureType, MeasureUnit[]> measureUnits) {
        this.measureUnits = measureUnits;
    }
}
