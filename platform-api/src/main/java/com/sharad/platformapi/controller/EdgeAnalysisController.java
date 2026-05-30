package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.EdgeAnalysisDto;
import com.sharad.platformapi.service.EdgeAnalysisService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/edge")
public class EdgeAnalysisController {

    private final EdgeAnalysisService service;

    public EdgeAnalysisController(
            EdgeAnalysisService service
    ) {
        this.service = service;
    }

    @GetMapping("/momentum/{symbol}")
    public EdgeAnalysisDto momentumEdge(
            @PathVariable String symbol
    ) {
        return service.analyzeMomentumEdge(symbol);
    }

    @GetMapping("/rsi/{symbol}")
    public EdgeAnalysisDto rsiEdge(
            @PathVariable String symbol
    ) {
        return service.analyzeRsiEdge(symbol);
    }

    @GetMapping("/macd/{symbol}")
    public EdgeAnalysisDto macdEdge(
            @PathVariable String symbol
    ) {
        return service.analyzeMacdEdge(symbol);
    }

    @GetMapping("/trend/{symbol}")
    public EdgeAnalysisDto trendEdge(
            @PathVariable String symbol
    ) {
        return service.analyzeTrendEdge(symbol);
    }
}