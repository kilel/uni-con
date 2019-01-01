package org.github.unicon.web;

import org.github.unicon.conv.model.MeasureType;
import org.github.unicon.conv.model.MeasureUnit;
import org.github.unicon.conv.model.MeasuredValue;
import org.github.unicon.web.model.MeasureConvertResponse;
import org.github.unicon.web.model.MeasuresListResponse;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/convert")
public class ConversionController {

    @GetMapping(path = "/measure-list")
    @ResponseBody
    public MeasuresListResponse listMeasureTypes() {
        final Map<MeasureType, MeasureUnit[]> measureUnits = Arrays.stream(MeasureType.values())
            .collect(Collectors.toMap(
                x -> x,
                MeasureType::getUnits
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
        final MeasureUnit sourceUnit = type.getUnit(sourceUnitName);
        final MeasureUnit targetUnit = type.getUnit(targetUnitName);

        final MeasuredValue<MeasureUnit> result = new MeasuredValue<>(value, sourceUnit).to(targetUnit);

        final MeasureConvertResponse response = new MeasureConvertResponse();
        response.setType(type);
        response.setUnit(targetUnit);
        response.setValue(result.getValue());
        return response;
    }
}
