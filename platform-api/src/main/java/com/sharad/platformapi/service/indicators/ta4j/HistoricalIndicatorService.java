package com.sharad.platformapi.service.indicators.ta4j;

import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;

import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;

import java.time.LocalDate;
import java.util.List;

@Service
public class HistoricalIndicatorService {

    private final ETFPriceHistoryRepository repository;

    private final Ta4jSeriesBuilderService seriesBuilder;

    private final SmaIndicatorService smaService;

    public HistoricalIndicatorService(
            ETFPriceHistoryRepository repository,
            Ta4jSeriesBuilderService seriesBuilder,
            SmaIndicatorService smaService
    ) {
        this.repository = repository;
        this.seriesBuilder = seriesBuilder;
        this.smaService = smaService;
    }

    public double getHistoricalSma(
            String symbol,
            int period,
            LocalDate tradeDate
    ) {

        List<ETFPriceHistory> history =
                repository.findBySymbolOrderByTradeDateAsc(
                        symbol
                );

        List<ETFPriceHistory> filtered =
                history.stream()
                        .filter(
                                h -> !h.getTradeDate()
                                        .isAfter(tradeDate)
                        )
                        .toList();

        if (filtered.size() < period) {
            return 0.0;
        }

        BarSeries series =
                seriesBuilder.buildSeries(
                        filtered
                );

        return smaService.calculateSma(
                series,
                period
        );
    }
}