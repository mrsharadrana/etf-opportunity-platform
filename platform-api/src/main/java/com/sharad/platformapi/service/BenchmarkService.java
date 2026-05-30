package com.sharad.platformapi.service;

import com.sharad.platformapi.config.BenchmarkConstants;
import com.sharad.platformapi.dto.BacktestResultDto;
import com.sharad.platformapi.dto.BenchmarkComparisonDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BenchmarkService {

    private final BacktestService backtestService;

    private final ETFPriceHistoryRepository repository;

    public BenchmarkService(
            BacktestService backtestService,
            ETFPriceHistoryRepository repository
    ) {
        this.backtestService = backtestService;
        this.repository = repository;
    }

    public BenchmarkComparisonDto compare() {

        BacktestResultDto strategy =
                backtestService.runBacktest();

        double nifty =
                calculateBuyAndHold(
                        BenchmarkConstants.NIFTY_BEES
                );

        double mon100 =
                calculateBuyAndHold(
                        BenchmarkConstants.MON100
                );

        double gold =
                calculateBuyAndHold(
                        BenchmarkConstants.GOLD_BEES
                );

        double strategyReturn =
                strategy.totalReturnPct();

        String winner = "Strategy";

        double best =
                strategyReturn;

        if (nifty > best) {
            best = nifty;
            winner = "NIFTYBEES";
        }

        if (mon100 > best) {
            best = mon100;
            winner = "MON100";
        }

        if (gold > best) {
            best = gold;
            winner = "GOLDBEES";
        }

        return new BenchmarkComparisonDto(
                strategyReturn,
                nifty,
                mon100,
                gold,
                winner
        );
    }

    private double calculateBuyAndHold(
            String symbol
    ) {

        List<ETFPriceHistory> history =
                repository.findBySymbolOrderByTradeDateAsc(
                        symbol
                );

        if (history.size() < 2) {
            return 0.0;
        }

        double first =
                history.getFirst()
                        .getClosePrice()
                        .doubleValue();

        double last =
                history.getLast()
                        .getClosePrice()
                        .doubleValue();

        return ((last - first)
                / first)
                * 100.0;
    }
}