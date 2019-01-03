package org.github.unicon.conv.date;

import org.github.unicon.conv.measure.MeasuredValue;
import org.github.unicon.conv.measure.impl.DurationUnit;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class DateService {

    public MeasuredValue<DurationUnit> durationBetween(Date source, Date target, DurationUnit unit) {
        return new MeasuredValue<>(
            DurationUnit.MILLISECOND.convert(BigDecimal.valueOf(target.getTime() - source.getTime()), unit),
            unit
        );
    }

    public Date dateAfter(Date source, MeasuredValue<DurationUnit> value) {
        return new Date(source.getTime() + value.to(DurationUnit.MILLISECOND).getValue().longValue());
    }
}
