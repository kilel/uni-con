package org.github.unicon.conv.text;

import java.util.Arrays;

public enum EscapeType {
    URL("url"),
    XML("xml");

    private final String code;

    EscapeType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static EscapeType parse(String code) {
        return Arrays.stream(values())
            .filter(x -> x.code.equalsIgnoreCase(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown escape type"));
    }
}
