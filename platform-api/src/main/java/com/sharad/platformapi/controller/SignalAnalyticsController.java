package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.SignalAnalyticsDto;
import com.sharad.platformapi.service.SignalAnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignalAnalyticsController {

    private final SignalAnalyticsService service;

    public SignalAnalyticsController(
            SignalAnalyticsService service
    ) {
        this.service = service;
    }

    @GetMapping(
            "/api/validation/{symbol}"
    )
    public SignalAnalyticsDto validate(
            @PathVariable String symbol
    ) {
        return service.analyze(
                symbol
        );
    }
}