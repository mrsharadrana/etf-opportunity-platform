package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.BenchmarkComparisonDto;
import com.sharad.platformapi.service.BenchmarkService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/backtest/benchmark")
public class BenchmarkController {

    private final BenchmarkService service;

    public BenchmarkController(
            BenchmarkService service
    ) {
        this.service = service;
    }

    @GetMapping
    public BenchmarkComparisonDto compare() {

        return service.compare();
    }
}