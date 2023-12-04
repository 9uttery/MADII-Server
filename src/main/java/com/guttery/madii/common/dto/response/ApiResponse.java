package com.guttery.madii.common.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@JsonPropertyOrder({"status", "code", "message", "data", "timestamp"})
public class ApiResponse<T> {
    private final int status;
    private final String code;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp;

    public static <T> ApiResponse<T> of(int status, String code, String message, T data) {
        return new ApiResponse<>(status, code, message, data, LocalDateTime.now());
    }
}
