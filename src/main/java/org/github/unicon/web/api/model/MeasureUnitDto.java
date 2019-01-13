package org.github.unicon.web.api.model;

import org.github.unicon.model.measure.MeasureUnit;

public class MeasureUnitDto {
    private String code;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static MeasureUnitDto build(MeasureUnit source) {
        final MeasureUnitDto result = new MeasureUnitDto();
        result.setCode(source.getCode());
        return result;
    }
}
