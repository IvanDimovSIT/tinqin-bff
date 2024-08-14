package com.tinqinacademy.bff.api.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum BffBedSize {
    SINGLE("single"),
    DOUBLE("double"),
    SMALL_DOUBLE("smallDouble"),
    KING_SIZE("kingSize"),
    QUEEN_SIZE("queenSize"),
    UNKNOWN("");

    private final String code;

    BffBedSize(String code) {
        this.code = code;
    }

    @JsonCreator
    public static BffBedSize getCode(String code) {
        return Arrays.stream(BffBedSize.values())
                .filter(bedSize -> bedSize.code.equals(code))
                .findFirst()
                .orElse(UNKNOWN);
    }

    @JsonValue
    public String toString() {
        return code;
    }
}
