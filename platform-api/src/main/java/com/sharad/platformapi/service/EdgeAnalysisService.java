package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.EdgeAnalysisDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import com.sharad.platformapi.service.indicators.ta4j.MacdIndicatorService;
import com.sharad.platformapi.service.indicators.ta4j.RsiIndicatorService;
import com.sharad.platformapi.service.indicators.ta4j.SmaIndicatorService;
import com.sharad.platformapi.service.indicators.ta4j.Ta4jSeriesBuilderService;

import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;

import java.util.List;

@Service
public class EdgeAnalysisService {

    private final ETFPriceHistoryRepository repository;

    private final Ta4jSeriesBuilderService seriesBuilder;

    private final RsiIndicatorService rsiService;

    private final MacdIndicatorService macdService;

    private final SmaIndicatorService smaService;

    public EdgeAnalysisService(
            ETFPriceHistoryRepository repository,
            Ta4jSeriesBuilderService seriesBuilder,
            RsiIndicatorService rsiService,
            MacdIndicatorService macdService,
            SmaIndicatorService smaService
    ) {
        this.repository = repository;
        this.seriesBuilder = seriesBuilder;
        this.rsiService = rsiService;
        this.macdService = macdService;
        this.smaService = smaService;
    }

    private BarSeries buildSeries(
            List<ETFPriceHistory> history
    ) {

        return seriesBuilder.buildSeries(
                history
        );
    }

    public EdgeAnalysisDto analyzeMomentumEdge(
            String symbol
    ) {

        List<ETFPriceHistory> history =
                repository.findBySymbolOrderByTradeDateAsc(
                        symbol
                );

        int samples = 0;
        int wins = 0;

        double totalReturn = 0.0;

        double bestReturn =
                Double.NEGATIVE_INFINITY;

        double worstReturn =
                Double.POSITIVE_INFINITY;

        for (int i = 0;
             i < history.size() - 21;
             i++) {

            ETFPriceHistory current =
                    history.get(i);

            if (current.getMomentumScore() == null) {
                continue;
            }

            if (current.getMomentumScore()
                    .doubleValue() <= 20) {
                continue;
            }

            ETFPriceHistory future =
                    history.get(i + 21);

            double currentPrice =
                    current.getClosePrice()
                            .doubleValue();

            double futurePrice =
                    future.getClosePrice()
                            .doubleValue();

            double returnPct =
                    ((futurePrice - currentPrice)
                            / currentPrice)
                            * 100.0;

            samples++;

            totalReturn += returnPct;

            if (returnPct > 0) {
                wins++;
            }

            bestReturn =
                    Math.max(bestReturn, returnPct);

            worstReturn =
                    Math.min(worstReturn, returnPct);
        }

        double winRate =
                samples > 0
                        ? ((double) wins / samples) * 100.0
                        : 0.0;

        double avgReturn =
                samples > 0
                        ? totalReturn / samples
                        : 0.0;

        return new EdgeAnalysisDto(
                "Momentum > 20",
                samples,
                winRate,
                avgReturn,
                bestReturn,
                worstReturn
        );
    }

    public EdgeAnalysisDto analyzeRsiEdge(
            String symbol
    ) {

        List<ETFPriceHistory> history =
                repository.findBySymbolOrderByTradeDateAsc(
                        symbol
                );

        int samples = 0;
        int wins = 0;

        double totalReturn = 0.0;

        double bestReturn =
                Double.NEGATIVE_INFINITY;

        double worstReturn =
                Double.POSITIVE_INFINITY;

        for (int i = 50;
             i < history.size() - 21;
             i++) {

            List<ETFPriceHistory> subset =
                    history.subList(
                            0,
                            i + 1
                    );

            BarSeries series =
                    buildSeries(
                            subset
                    );

            double rsi =
                    rsiService.calculateRsi(
                            series
                    );

            if (rsi <= 50) {
                continue;
            }

            ETFPriceHistory current =
                    history.get(i);

            ETFPriceHistory future =
                    history.get(i + 21);

            double currentPrice =
                    current.getClosePrice()
                            .doubleValue();

            double futurePrice =
                    future.getClosePrice()
                            .doubleValue();

            double returnPct =
                    ((futurePrice - currentPrice)
                            / currentPrice)
                            * 100.0;

            samples++;

            totalReturn += returnPct;

            if (returnPct > 0) {
                wins++;
            }

            bestReturn =
                    Math.max(bestReturn, returnPct);

            worstReturn =
                    Math.min(worstReturn, returnPct);
        }

        double winRate =
                samples > 0
                        ? ((double) wins / samples) * 100.0
                        : 0.0;

        double avgReturn =
                samples > 0
                        ? totalReturn / samples
                        : 0.0;

        return new EdgeAnalysisDto(
                "RSI > 50",
                samples,
                winRate,
                avgReturn,
                bestReturn,
                worstReturn
        );
    }

    public EdgeAnalysisDto analyzeMacdEdge(
            String symbol
    ) {

        List<ETFPriceHistory> history =
                repository.findBySymbolOrderByTradeDateAsc(
                        symbol
                );

        int samples = 0;
        int wins = 0;

        double totalReturn = 0.0;

        double bestReturn =
                Double.NEGATIVE_INFINITY;

        double worstReturn =
                Double.POSITIVE_INFINITY;

        for (int i = 35;
             i < history.size() - 21;
             i++) {

            List<ETFPriceHistory> subset =
                    history.subList(
                            0,
                            i + 1
                    );

            BarSeries series =
                    buildSeries(
                            subset
                    );

            MacdIndicatorService.MacdResult macd =
                    macdService.calculateMacd(
                            series
                    );

            if (macd.histogram() <= 0) {
                continue;
            }

            ETFPriceHistory current =
                    history.get(i);

            ETFPriceHistory future =
                    history.get(i + 21);

            double currentPrice =
                    current.getClosePrice()
                            .doubleValue();

            double futurePrice =
                    future.getClosePrice()
                            .doubleValue();

            double returnPct =
                    ((futurePrice - currentPrice)
                            / currentPrice)
                            * 100.0;

            samples++;

            totalReturn += returnPct;

            if (returnPct > 0) {
                wins++;
            }

            bestReturn =
                    Math.max(bestReturn, returnPct);

            worstReturn =
                    Math.min(worstReturn, returnPct);
        }

        double winRate =
                samples > 0
                        ? ((double) wins / samples) * 100.0
                        : 0.0;

        double avgReturn =
                samples > 0
                        ? totalReturn / samples
                        : 0.0;

        return new EdgeAnalysisDto(
                "MACD Histogram > 0",
                samples,
                winRate,
                avgReturn,
                bestReturn,
                worstReturn
        );
    }

    public EdgeAnalysisDto analyzeTrendEdge(
            String symbol
    ) {

        List<ETFPriceHistory> history =
                repository.findBySymbolOrderByTradeDateAsc(
                        symbol
                );

        int samples = 0;
        int wins = 0;

        double totalReturn = 0.0;

        double bestReturn =
                Double.NEGATIVE_INFINITY;

        double worstReturn =
                Double.POSITIVE_INFINITY;

        for (int i = 200;
             i < history.size() - 21;
             i++) {

            List<ETFPriceHistory> subset =
                    history.subList(
                            0,
                            i + 1
                    );

            BarSeries series =
                    buildSeries(
                            subset
                    );

            double sma200 =
                    smaService.calculateSma(
                            series,
                            200
                    );

            ETFPriceHistory current =
                    history.get(i);

            double currentPrice =
                    current.getClosePrice()
                            .doubleValue();

            if (currentPrice <= sma200) {
                continue;
            }

            ETFPriceHistory future =
                    history.get(i + 21);

            double futurePrice =
                    future.getClosePrice()
                            .doubleValue();

            double returnPct =
                    ((futurePrice - currentPrice)
                            / currentPrice)
                            * 100.0;

            samples++;

            totalReturn += returnPct;

            if (returnPct > 0) {
                wins++;
            }

            bestReturn =
                    Math.max(bestReturn, returnPct);

            worstReturn =
                    Math.min(worstReturn, returnPct);
        }

        double winRate =
                samples > 0
                        ? ((double) wins / samples) * 100.0
                        : 0.0;

        double avgReturn =
                samples > 0
                        ? totalReturn / samples
                        : 0.0;

        return new EdgeAnalysisDto(
                "Price > SMA200",
                samples,
                winRate,
                avgReturn,
                bestReturn,
                worstReturn
        );
    }
}