package org.github.unicon.service;

import org.github.unicon.model.measure.MeasureType;
import org.github.unicon.model.measure.MeasureUnit;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.math.MathContext;

public class MeasureConverterDb<T extends MeasureUnit> implements MeasureConverter<T> {
    private final JdbcTemplate jdbcTemplate;
    private final MeasureType type;

    public MeasureConverterDb(JdbcTemplate jdbcTemplate, MeasureType type) {
        this.jdbcTemplate = jdbcTemplate;
        this.type = type;
    }

    @Override
    public BigDecimal convert(BigDecimal value, T source, T target) {
        checkUnit(source);
        checkUnit(target);

        return value
            .multiply(getScale(source))
            .divide(getScale(target), MathContext.DECIMAL64)
            .stripTrailingZeros();
    }

    private void checkUnit(T source) {
        if (!type.getUnitType().isInstance(source)) {
            throw new IllegalArgumentException(String.format("Unit [%s] is unacceptable for type [%s]",
                source,
                type));
        }
    }

    private BigDecimal getScale(T unit) {
        return jdbcTemplate.queryForObject(
            "select scale from unit_scale where type_id = ? and unit_id = ?",
            BigDecimal.class,
            type.getId(),
            unit.getId()
        );
    }
}
