package org.github.unicon.service;

import org.github.unicon.model.measure.MeasuredValue;
import org.github.unicon.model.measure.unit.DurationUnit;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import static org.github.unicon.model.measure.unit.DurationUnit.MILLISECOND;

@Service
public class DateService {
    private final MeasureService measureService;

    public DateService(MeasureService measureService) {
        this.measureService = measureService;
    }

    public MeasuredValue<DurationUnit> durationBetween(Date source, Date target, DurationUnit unit) {
        final BigDecimal datesDeltaMs = BigDecimal.valueOf(target.getTime() - source.getTime());
        final MeasuredValue<DurationUnit> targetValue = new MeasuredValue<>(datesDeltaMs, MILLISECOND);
        return getConverter().convert(targetValue, unit);
    }

    public Date dateShift(Date source, MeasuredValue<DurationUnit> value) {
        final MeasuredValue<DurationUnit> valueMs = getConverter().convert(value, MILLISECOND);
        return new Date(source.getTime() + valueMs.getValue().longValue());
    }

    private MeasureConverter<DurationUnit> getConverter() {
        return measureService.getConverter(DurationUnit.class);
    }
}
