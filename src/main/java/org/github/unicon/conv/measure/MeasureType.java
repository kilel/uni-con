package org.github.unicon.conv.measure;

import org.github.unicon.conv.measure.impl.*;

import java.util.Arrays;

public enum MeasureType {
    LENGTH(LengthUnit.values()),
    WEIGHT(WeightUnit.values()),
    TEMPERATURE(TemperatureUnit.values()),
    PRESSURE(PressureUnit.values()),
    DURATION(DurationUnit.values()),
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
            .filter(x -> x.getCode().equalsIgnoreCase(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format(
                "Unknown unit [%s] for measure [%s]",
                name,
                toString())
            ));
    }
}
