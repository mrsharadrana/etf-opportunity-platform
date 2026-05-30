package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.SelectorPerformanceDto;
import com.sharad.platformapi.service.SelectorPerformanceService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/selector/performance")
public class SelectorPerformanceController {

    private final SelectorPerformanceService service;

    public SelectorPerformanceController(
            SelectorPerformanceService service
    ) {
        this.service = service;
    }

    @GetMapping
    public SelectorPerformanceDto performance() {

        return service.getPerformance();
    }
}