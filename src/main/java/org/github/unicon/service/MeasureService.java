package org.github.unicon.service;

import org.github.unicon.model.measure.MeasureType;
import org.github.unicon.model.measure.MeasureUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MeasureService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MeasureService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public MeasureUnit[] getUnits(MeasureType type) {
        try {
            return (MeasureUnit[]) type.getUnitType().getMethod("values").invoke(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MeasureUnit getUnit(MeasureType type, String name) {
        return Arrays.stream(getUnits(type))
            .filter(x -> x.getCode().equalsIgnoreCase(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format(
                "Unknown unit [%s] for measure [%s]",
                name,
                toString())
            ));
    }

    public <T extends MeasureUnit> MeasureConverter<T> getConverter(T unit) {
        return getConverter((Class<T>) unit.getClass());
    }

    public <T extends MeasureUnit> MeasureConverter<T> getConverter(Class<T> type) {
        final MeasureType measureType = Arrays.stream(MeasureType.values())
            .filter(x -> x.getUnitType() == type)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Whong measure type: " + type));

        return getConverter(measureType);
    }

    public <T extends MeasureUnit> MeasureConverter<T> getConverter(MeasureType type) {
        switch (type) {
            case LENGTH:
            case WEIGHT:
            case PRESSURE:
            case DURATION:
                return new MeasureConverterDb<>(jdbcTemplate, type);
            case TEMPERATURE:
                return (MeasureConverter<T>) new MeasureConverterTemperature();
            default:
                throw new RuntimeException("No converter implementation for " + type.name());
        }
    }
}
