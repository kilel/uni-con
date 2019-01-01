package org.github.unicon.conv.model;

import java.util.Arrays;

public enum MeasureType {
    LENGTH(LengthUnit.values()),
    WEIGHT(WeightUnit.values()),
    ;

    private final MeasureUnit[] units;

    MeasureType(MeasureUnit[] units) {
        this.units = units;
    }

    public MeasureUnit[] getUnits() {
        return units;
    }

    public MeasureUnit getUnit(String name) {
        return Arrays.stream(units)
            .filter(x -> x.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format(
                "Unknown unit [%s] for measure [%s]",
                name,
                toString())
            ));
    }
}
