package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.PortfolioAllocationDto;
import com.sharad.platformapi.dto.PortfolioBacktestDto;
import com.sharad.platformapi.dto.PortfolioResponseDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioBacktestService {

    private final PortfolioAllocatorService allocatorService;

    private final BenchmarkService benchmarkService;

    private final ETFPriceHistoryRepository repository;

    public PortfolioBacktestService(
            PortfolioAllocatorService allocatorService,
            BenchmarkService benchmarkService,
            ETFPriceHistoryRepository repository
    ) {
        this.allocatorService = allocatorService;
        this.benchmarkService = benchmarkService;
        this.repository = repository;
    }

    public PortfolioBacktestDto run() {

        PortfolioResponseDto portfolio =
                allocatorService.allocate();

        double allocatorReturn = 0.0;

        for (PortfolioAllocationDto allocation :
                portfolio.allocations()) {

            double etfReturn =
                    calculateReturn(
                            allocation.symbol()
                    );

            allocatorReturn +=
                    etfReturn
                            * allocation.allocationPct()
                            / 100.0;
        }

        double recommendationReturn =
                benchmarkService.compare()
                        .strategyReturn();

        String winner =
                allocatorReturn >
                        recommendationReturn
                        ? "ALLOCATOR"
                        : "RECOMMENDATION";

        return new PortfolioBacktestDto(
                recommendationReturn,
                allocatorReturn,
                winner
        );
    }

    private double calculateReturn(
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