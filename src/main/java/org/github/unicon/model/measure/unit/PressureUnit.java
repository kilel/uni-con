package org.github.unicon.model.measure.unit;

import org.github.unicon.model.measure.MeasureUnit;

public enum PressureUnit implements MeasureUnit {
    PASCAL(1, "pascal"),
    BAR(2, "bar"),
    MMHG(3, "mmhg"),
    ;

    private final long id;
    private final String code;

    PressureUnit(long id, String code) {
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
