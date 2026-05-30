package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.BacktestResultDto;
import com.sharad.platformapi.dto.BenchmarkComparisonDto;
import com.sharad.platformapi.dto.SelectorPerformanceDto;

import org.springframework.stereotype.Service;

@Service
public class SelectorPerformanceService {

    private final BacktestService backtestService;

    private final BenchmarkService benchmarkService;

    public SelectorPerformanceService(
            BacktestService backtestService,
            BenchmarkService benchmarkService
    ) {
        this.backtestService = backtestService;
        this.benchmarkService = benchmarkService;
    }

    public SelectorPerformanceDto getPerformance() {

        BacktestResultDto backtest =
                backtestService.runBacktest();

        BenchmarkComparisonDto benchmark =
                benchmarkService.compare();

        return new SelectorPerformanceDto(
                backtest.totalReturnPct(),
                backtest.cagr(),
                backtest.winRatePct(),
                backtest.maxDrawdownPct(),
                benchmark.bestPerformer(),
                switch (benchmark.bestPerformer()) {

                    case "MON100" ->
                            benchmark.mon100Return();

                    case "GOLDBEES" ->
                            benchmark.goldBeesReturn();

                    default ->
                            benchmark.niftyBeesReturn();
                }
        );
    }
}