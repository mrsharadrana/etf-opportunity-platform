package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.EtfScorecardDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import com.sharad.platformapi.service.indicators.ta4j.IndicatorFacadeService;
import com.sharad.platformapi.service.indicators.ta4j.MacdIndicatorService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScorecardService {

    private final ETFPriceHistoryRepository repository;

    private final IndicatorFacadeService indicatorFacade;

    public ScorecardService(
            ETFPriceHistoryRepository repository,
            IndicatorFacadeService indicatorFacade
    ) {
        this.repository = repository;
        this.indicatorFacade = indicatorFacade;
    }

    public EtfScorecardDto getScorecard(
            String symbol
    ) {

        List<ETFPriceHistory> history =
                repository.findBySymbolOrderByTradeDateDesc(
                        symbol
                );

        if (history.isEmpty()) {

            return new EtfScorecardDto(
                    symbol,
                    0.0,
                    0.0,
                    "NO_DATA",
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    false,
                    "UNKNOWN"
            );
        }

        ETFPriceHistory latest =
                history.getFirst();

        double currentPrice =
                latest.getClosePrice()
                        .doubleValue();

        double momentumScore =
                latest.getMomentumScore() == null
                        ? 0.0
                        : latest.getMomentumScore()
                                .doubleValue();

        String signal =
                latest.getSignal();

        double rsi =
                indicatorFacade.getRsi(symbol);

        double sma50 =
                indicatorFacade.getSma50(symbol);

        double sma200 =
                indicatorFacade.getSma200(symbol);

        double ema50 =
                indicatorFacade.getEma50(symbol);

        MacdIndicatorService.MacdResult macd =
                indicatorFacade.getMacd(symbol);

        boolean above200DMA =
                currentPrice > sma200;

        String trendStrength =
                calculateTrendStrength(
                        currentPrice,
                        sma50,
                        sma200,
                        ema50,
                        rsi,
                        macd.histogram()
                );

        return new EtfScorecardDto(
                symbol,
                currentPrice,
                momentumScore,
                signal,
                rsi,
                sma50,
                sma200,
                ema50,
                macd.macd(),
                macd.signal(),
                macd.histogram(),
                above200DMA,
                trendStrength
        );
    }

    private String calculateTrendStrength(
            double price,
            double sma50,
            double sma200,
            double ema50,
            double rsi,
            double macdHistogram
    ) {

        if (price > sma50
                && price > sma200
                && price > ema50
                && rsi > 60
                && macdHistogram > 0) {

            return "STRONG_BULLISH";
        }

        if (price > sma200
                && rsi > 50) {

            return "BULLISH";
        }

        if (price < sma200
                && rsi < 50) {

            return "BEARISH";
        }

        return "NEUTRAL";
    }
}