package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.BacktestResultDto;
import com.sharad.platformapi.dto.BenchmarkComparisonDto;
import com.sharad.platformapi.dto.SignalAnalyticsDto;
import com.sharad.platformapi.dto.SignalValidationDto;
import org.springframework.stereotype.Service;

@Service
public class SignalAnalyticsService {

    private final SignalValidationService signalValidationService;

    private final BacktestService backtestService;

    private final BenchmarkService benchmarkService;

    public SignalAnalyticsService(
            SignalValidationService signalValidationService,
            BacktestService backtestService,
            BenchmarkService benchmarkService
    ) {
        this.signalValidationService = signalValidationService;
        this.backtestService = backtestService;
        this.benchmarkService = benchmarkService;
    }

    public SignalAnalyticsDto analyze(
            String symbol
    ) {

        SignalValidationDto validation =
                signalValidationService.validate(
                        symbol
                );

        BacktestResultDto backtest =
                backtestService.runBacktest();

        BenchmarkComparisonDto benchmark =
                benchmarkService.compare();

        return new SignalAnalyticsDto(
                symbol,
                validation,
                backtest,
                benchmark
        );
    }
}