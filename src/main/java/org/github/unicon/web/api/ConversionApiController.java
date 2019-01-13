package org.github.unicon.web.api;

import org.github.unicon.model.measure.MeasureType;
import org.github.unicon.model.measure.MeasureUnit;
import org.github.unicon.model.measure.MeasuredValue;
import org.github.unicon.model.measure.unit.DurationUnit;
import org.github.unicon.model.text.EncodingType;
import org.github.unicon.model.text.EscapeType;
import org.github.unicon.model.text.HashType;
import org.github.unicon.service.DateService;
import org.github.unicon.service.MeasureService;
import org.github.unicon.service.TextService;
import org.github.unicon.web.api.model.MeasureConvertResponse;
import org.github.unicon.web.api.model.MeasureUnitDto;
import org.github.unicon.web.api.model.MeasuresListResponse;
import org.github.unicon.web.api.model.ValueResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/convert")
public class ConversionApiController {
    private static ThreadLocal<DateFormat> DATE_FORMAT = ThreadLocal.withInitial(() ->
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ")
    );

    private final MeasureService measureService;
    private final DateService dateService;
    private final TextService textService;

    @Autowired
    public ConversionApiController(MeasureService measureService,
                                   DateService dateService,
                                   TextService textService) {
        this.measureService = measureService;
        this.dateService = dateService;
        this.textService = textService;
    }

    @GetMapping(path = "/measure-list")
    @ResponseBody
    public MeasuresListResponse listMeasureTypes() {
        final Map<MeasureType, MeasureUnitDto[]> measureUnits = Arrays.stream(MeasureType.values())
            .collect(Collectors.toMap(
                x -> x,
                x -> Arrays.stream(measureService.getUnits(x))
                    .map(MeasureUnitDto::build)
                    .toArray(MeasureUnitDto[]::new)
            ));

        final MeasuresListResponse response = new MeasuresListResponse();
        response.setTypes(MeasureType.values());
        response.setMeasureUnits(measureUnits);
        return response;
    }

    @GetMapping(path = "/measure")
    @ResponseBody
    public MeasureConvertResponse convertMeasures(@RequestParam("type") MeasureType type,
                                                  @RequestParam("source") String sourceUnitName,
                                                  @RequestParam("target") String targetUnitName,
                                                  @RequestParam("value") BigDecimal value) {
        final MeasureUnit sourceUnit = measureService.getUnit(type, sourceUnitName);
        final MeasureUnit targetUnit = measureService.getUnit(type, targetUnitName);

        final MeasuredValue<MeasureUnit> sourceValue = new MeasuredValue<>(value, sourceUnit);
        final MeasuredValue<MeasureUnit> result = measureService.getConverter(type).convert(sourceValue, targetUnit);
        return MeasureConvertResponse.build(type, result);
    }

    @GetMapping(path = "/date/toInterval")
    @ResponseBody
    public ValueResponse<BigDecimal> dateToInterval(@RequestParam("source") String sourceDate,
                                                    @RequestParam("target") String targetDate,
                                                    @RequestParam("durationUnit") String targetUnitName) {
        final DurationUnit targetUnit = (DurationUnit) measureService.getUnit(MeasureType.DURATION, targetUnitName);

        final MeasuredValue<DurationUnit> result;
        try {
            result = dateService.durationBetween(
                DATE_FORMAT.get().parse(sourceDate),
                DATE_FORMAT.get().parse(targetDate),
                targetUnit
            );

            return ValueResponse.build(result.getValue());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/date/toDate")
    @ResponseBody
    public ValueResponse<String> dateToDate(@RequestParam("source") String sourceDate,
                                            @RequestParam("interval") BigDecimal interval,
                                            @RequestParam("durationUnit") String targetUnitName) {
        final DurationUnit targetUnit = (DurationUnit) measureService.getUnit(MeasureType.DURATION, targetUnitName);

        final Date result;
        try {
            result = dateService.dateShift(
                DATE_FORMAT.get().parse(sourceDate),
                new MeasuredValue<>(interval, targetUnit)
            );

            return ValueResponse.build(DATE_FORMAT.get().format(result));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/text/escape")
    @ResponseBody
    public ValueResponse<String> textEscape(@RequestParam("source") String source,
                                            @RequestParam("type") String type) {
        return ValueResponse.build(textService.escape(source, EscapeType.parse(type)));
    }

    @GetMapping(path = "/text/unescape")
    @ResponseBody
    public ValueResponse<String> textUnescape(@RequestParam("source") String source,
                                              @RequestParam("type") String type) {
        return ValueResponse.build(textService.unescape(source, EscapeType.parse(type)));
    }

    @GetMapping(path = "/text/encode")
    @ResponseBody
    public ValueResponse<String> textEncode(@RequestParam("source") String source,
                                            @RequestParam("sourceType") String sourceType,
                                            @RequestParam("targetType") String targetType) {
        final EncodingType sourceEnc = EncodingType.parse(sourceType);
        final EncodingType targetEnc = EncodingType.parse(targetType);

        final byte[] data = textService.decode(source, sourceEnc);
        return ValueResponse.build(textService.encode(data, targetEnc));
    }

    @GetMapping(path = "/text/hash")
    @ResponseBody
    public ValueResponse<String> textHash(@RequestParam("source") String source,
                                          @RequestParam("encoding") String encodingCode,
                                          @RequestParam("encodingTgt") String encodingTgtCode,
                                          @RequestParam("hashType") String hashTypeCode) {
        final EncodingType encoding = EncodingType.parse(encodingCode);
        final EncodingType encodingTgt = EncodingType.parse(encodingTgtCode);
        final HashType hashType = HashType.parse(hashTypeCode);

        final byte[] data = textService.decode(source, encoding);
        final byte[] hash = textService.hash(data, hashType);
        return ValueResponse.build(textService.encode(hash, encodingTgt));
    }
}
