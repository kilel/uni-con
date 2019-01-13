package org.github.unicon.model.measure.unit;

import org.github.unicon.model.measure.MeasureUnit;

public enum WeightUnit implements MeasureUnit {
    GRAM(1, "gram"),
    KILOGRAM(2, "kilogram"),
    TONNE(3, "tonne"),
    CENTNER(4, "centner"),
    CARAT(5, "carat"),
    POUND(6, "pound"),
    STONE(7, "stone"),
    OUNCE(8, "ounce"),
    ;

    private final long id;
    private final String code;

    WeightUnit(long id, String code) {
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
