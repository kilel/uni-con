package org.github.unicon.model.measure;

import org.github.unicon.UniconApplicationTests;
import org.github.unicon.service.MeasureConverter;
import org.github.unicon.service.MeasureService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.github.unicon.TestUtils.assertNotDifferMuch;

public abstract class AbstractMeasureUnitTests extends UniconApplicationTests {
    protected static final double DELTA = 0.001;

    @Autowired
    private MeasureService measureService;

    protected <T extends MeasureUnit> void assertConvert(T source, double value, T target, double expected) {
        final MeasureConverter<T> converter = measureService.getConverter(source);
        final BigDecimal result = converter.convert(BigDecimal.valueOf(value), source, target);
        Assert.assertEquals(expected, result.doubleValue(), Math.abs(expected) * DELTA);
    }

    protected <T extends MeasureUnit> void assertConvert(T source, BigDecimal value, T target, BigDecimal expected) {
        final MeasureConverter<T> converter = measureService.getConverter(source);
        final BigDecimal result = converter.convert(value, source, target);
        assertNotDifferMuch(expected, result, DELTA);
    }
}
