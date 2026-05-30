package com.sharad.platformapi.service.indicators.ta4j;

import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;

import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;

import java.util.List;

@Service
public class IndicatorFacadeService {

    private final ETFPriceHistoryRepository repository;

    private final Ta4jSeriesBuilderService seriesBuilder;

    private final RsiIndicatorService rsiService;

    private final SmaIndicatorService smaService;

    private final EmaIndicatorService emaService;

    private final MacdIndicatorService macdService;

    public IndicatorFacadeService(
            ETFPriceHistoryRepository repository,
            Ta4jSeriesBuilderService seriesBuilder,
            RsiIndicatorService rsiService,
            SmaIndicatorService smaService,
            EmaIndicatorService emaService,
            MacdIndicatorService macdService
    ) {
        this.repository = repository;
        this.seriesBuilder = seriesBuilder;
        this.rsiService = rsiService;
        this.smaService = smaService;
        this.emaService = emaService;
        this.macdService = macdService;
    }

    public double getRsi(
            String symbol
    ) {

        BarSeries series =
                buildSeries(symbol);

        return rsiService.calculateRsi(series);
    }

    public double getSma50(
            String symbol
    ) {

        BarSeries series =
                buildSeries(symbol);

        return smaService.calculateSma(series, 50);
    }

    public double getSma200(
            String symbol
    ) {

        BarSeries series =
                buildSeries(symbol);

        return smaService.calculateSma(series, 200);
    }

    public double getEma50(
            String symbol
    ) {

        BarSeries series =
                buildSeries(symbol);

        return emaService.calculateEma(series, 50);
    }

    public MacdIndicatorService.MacdResult getMacd(
            String symbol
    ) {

        BarSeries series =
                buildSeries(symbol);

        return macdService.calculateMacd(series);
    }

    private BarSeries buildSeries(
            String symbol
    ) {

        List<ETFPriceHistory> history =
                repository.findBySymbolOrderByTradeDateAsc(
                        symbol
                );

        return seriesBuilder.buildSeries(history);
    }
}