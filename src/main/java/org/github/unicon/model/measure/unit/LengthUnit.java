package org.github.unicon.model.measure.unit;

import org.github.unicon.model.measure.MeasureUnit;

public enum LengthUnit implements MeasureUnit {
    METER(1, "meter"),
    KILOMETER(2, "kilometer"),
    FOOT(3, "foot"),
    MILE(4, "mile"),
    YARD(5, "yard"),
    INCH(6, "inch"),
    LEAGUE(7, "league"),
    FURLONG(8, "furlong"),
    CHAIN(9, "chain"),
    ;

    private final long id;
    private final String code;

    LengthUnit(long id, String code) {
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
