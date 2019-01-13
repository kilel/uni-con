package org.github.unicon.model.measure;

import org.github.unicon.model.measure.unit.*;

public enum MeasureType {
    LENGTH(1, "length", LengthUnit.class),
    WEIGHT(2, "weight", WeightUnit.class),
    TEMPERATURE(3, "temperature", TemperatureUnit.class),
    PRESSURE(4, "pressure", PressureUnit.class),
    DURATION(5, "duration", DurationUnit.class),
    ;

    private final long id;
    private final String code;
    private final Class<? extends MeasureUnit> unitType;

    MeasureType(long id, String code, Class<? extends MeasureUnit> unitType) {
        this.id = id;
        this.code = code;
        this.unitType = unitType;
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public Class<? extends MeasureUnit> getUnitType() {
        return unitType;
    }
}
