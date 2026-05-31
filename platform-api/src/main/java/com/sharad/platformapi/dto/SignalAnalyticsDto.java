package com.sharad.platformapi.dto;

public record SignalAnalyticsDto(

        String symbol,

        SignalValidationDto validation,

        BacktestResultDto backtest,

        BenchmarkComparisonDto benchmark

) {
}