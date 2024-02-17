package com.guttery.madii.domain.achievement.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;

import java.util.stream.Stream;

public enum Satisfaction {
    BAD, SO_SO, GOOD, GREAT, EXCELLENT;

    @JsonCreator
    public static Satisfaction fromString(final String value) {
        return Stream.of(Satisfaction.values())
                .filter(s -> s.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> CustomException.of(ErrorDetails.INVALID_SATISFACTION_ENUM));
    }

    @JsonValue
    public String toJson() {
        return name();
    }
}
