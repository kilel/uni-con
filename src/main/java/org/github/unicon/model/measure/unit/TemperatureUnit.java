package org.github.unicon.model.measure.unit;

import org.github.unicon.model.measure.MeasureUnit;

public enum TemperatureUnit implements MeasureUnit {
    CELSIUS(1, "C"),
    KELVIN(2, "K"),
    FAHRENHEIT(3, "F"),
    ;

    private final long id;
    private final String code;

    TemperatureUnit(long id, String code) {
        this.id = id;
        this.code = code;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getCode() {
        return code;
    }
}
