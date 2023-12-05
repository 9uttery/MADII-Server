package com.guttery.madii.controller;

import com.guttery.madii.common.exception.CustomException;
import com.guttery.madii.common.exception.ErrorDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public String checkHealth() {
        return "healthy";
    }

    @GetMapping("/health/dto")
    public HealthResponse checkHealthDto() {
        return new HealthResponse("healthy");
    }

    @GetMapping("/health/error")
    public HealthResponse checkHealthError() {
        throw CustomException.of(ErrorDetails.BAD_REQUEST);
    }

    public record HealthResponse(String status) {
    }
}
