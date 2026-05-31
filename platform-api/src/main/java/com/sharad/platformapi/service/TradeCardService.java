package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.SignalStrengthDto;
import com.sharad.platformapi.dto.TradeCardDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.entity.MarketRegime;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import com.sharad.platformapi.repository.MarketRegimeRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradeCardService {

    private final ETFPriceHistoryRepository repository;

    private final SignalStrengthService signalStrengthService;

    private final MarketRegimeRepository marketRegimeRepository;

    public TradeCardService(
            ETFPriceHistoryRepository repository,
            SignalStrengthService signalStrengthService,
            MarketRegimeRepository marketRegimeRepository
    ) {
        this.repository = repository;
        this.signalStrengthService = signalStrengthService;
        this.marketRegimeRepository = marketRegimeRepository;
    }

    public TradeCardDto generate(
            String symbol
    ) {

        String signal =
                signalStrengthService.generateSignals()
                        .stream()
                        .filter(
                                s -> s.symbol().equals(symbol)
                        )
                        .map(
                                SignalStrengthDto::signal
                        )
                        .findFirst()
                        .orElse("AVOID");

        MarketRegime regime =
                marketRegimeRepository.findLatestRegime();

        if (
                !"BUY".equals(signal)
                        &&
                        !"STRONG_BUY".equals(signal)
        ) {

            return new TradeCardDto(
                    symbol,
                    signal,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    regime.getMarketRegime(),
                    "No trade setup available.",
                    LocalDateTime.now()
            );
        }

        ETFPriceHistory latest =
                repository.findTopBySymbolOrderByTradeDateDesc(
                        symbol
                );

        List<ETFPriceHistory> candles =
                repository.findTop20BySymbolOrderByTradeDateDesc(
                        symbol
                );

        BigDecimal entryPrice =
                latest.getClosePrice();

        BigDecimal atr =
                calculateAtr(
                        candles
                );

        BigDecimal swingLow =
                candles.stream()
                        .map(
                                ETFPriceHistory::getLowPrice
                        )
                        .min(
                                BigDecimal::compareTo
                        )
                        .orElse(entryPrice);

        BigDecimal atrStop =
                entryPrice.subtract(
                        atr.multiply(
                                BigDecimal.valueOf(1.5)
                        )
                );

        BigDecimal stopLoss =
                atrStop.max(
                        swingLow
                );

        BigDecimal atrMultiplier =
                "STRONG_BUY".equals(signal)
                        ? BigDecimal.valueOf(5)
                        : BigDecimal.valueOf(3);

        BigDecimal atrTarget =
                entryPrice.add(
                        atr.multiply(
                                atrMultiplier
                        )
                );

        BigDecimal pivotResistance =
                findPivotResistance(
                        candles,
                        entryPrice
                );

        BigDecimal targetPrice;

        String targetSource;

        if (
                pivotResistance != null
                        &&
                        pivotResistance.compareTo(
                        atrTarget
                ) > 0
        ) {

            targetPrice =
                    pivotResistance;

            targetSource =
                    "PIVOT_RESISTANCE";

        } else {

            targetPrice =
                    atrTarget;

            targetSource =
                    "STRONG_BUY".equals(signal)
                            ? "ATR_5X"
                            : "ATR_3X";
        }

        BigDecimal expectedReturn =
                targetPrice.subtract(
                                entryPrice
                        )
                        .divide(
                                entryPrice,
                                4,
                                RoundingMode.HALF_UP
                        )
                        .multiply(
                                BigDecimal.valueOf(100)
                        );

        BigDecimal expectedRisk =
                entryPrice.subtract(
                                stopLoss
                        )
                        .divide(
                                entryPrice,
                                4,
                                RoundingMode.HALF_UP
                        )
                        .multiply(
                                BigDecimal.valueOf(100)
                        );

        BigDecimal riskReward =
                expectedReturn.divide(
                        expectedRisk,
                        2,
                        RoundingMode.HALF_UP
                );

        BigDecimal trailingStop =
                entryPrice.multiply(
                                BigDecimal.valueOf(
                                        0.92
                                )
                        )
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        String targetConfidence =
                determineTargetConfidence(
                        signal,
                        targetSource
                );

        int tradeQualityScore =
                calculateTradeQualityScore(
                        signal,
                        riskReward
                );

        String tradeGrade =
                determineTradeGrade(
                        tradeQualityScore
                );

        return new TradeCardDto(
                symbol,
                signal,
                entryPrice,
                targetPrice,
                targetSource,
                targetConfidence,
                stopLoss,
                trailingStop,
                riskReward,
                expectedReturn,
                expectedRisk,
                tradeQualityScore,
                tradeGrade,
                regime.getMarketRegime(),
                null,
                LocalDateTime.now()
        );
    }

    private BigDecimal findPivotResistance(
            List<ETFPriceHistory> candles,
            BigDecimal entryPrice
    ) {

        for (int i = 2; i < candles.size() - 2; i++) {

            BigDecimal current =
                    candles.get(i)
                            .getHighPrice();

            boolean pivot =
                    current.compareTo(
                            candles.get(i - 1)
                                    .getHighPrice()
                    ) > 0
                            &&
                            current.compareTo(
                                    candles.get(i - 2)
                                            .getHighPrice()
                            ) > 0
                            &&
                            current.compareTo(
                                    candles.get(i + 1)
                                            .getHighPrice()
                            ) > 0
                            &&
                            current.compareTo(
                                    candles.get(i + 2)
                                            .getHighPrice()
                            ) > 0;

            if (
                    pivot
                            &&
                            current.compareTo(
                                    entryPrice
                            ) > 0
            ) {

                BigDecimal distance =
                        current.subtract(
                                entryPrice
                        );

                BigDecimal atrThreshold =
                        entryPrice.multiply(
                                BigDecimal.valueOf(
                                        0.01
                                )
                        );

                if (
                        distance.compareTo(
                                atrThreshold
                        ) > 0
                ) {
                    return current;
                }
            }
        }

        return null;
    }

    private String determineTargetConfidence(
            String signal,
            String source
    ) {

        if (
                "PIVOT_RESISTANCE".equals(source)
                        &&
                        "STRONG_BUY".equals(signal)
        ) {
            return "HIGH";
        }

        if (
                "PIVOT_RESISTANCE".equals(source)
        ) {
            return "MEDIUM";
        }

        if (
                "ATR_5X".equals(source)
        ) {
            return "MEDIUM";
        }

        return "LOW";
    }

    private int calculateTradeQualityScore(
            String signal,
            BigDecimal riskReward
    ) {

        int score =
                riskReward.multiply(
                                BigDecimal.valueOf(20)
                        )
                        .intValue();

        if (
                "STRONG_BUY".equals(signal)
        ) {

            score += 30;

        } else {

            score += 15;
        }

        return Math.min(
                score,
                100
        );
    }

    private String determineTradeGrade(
            int score
    ) {

        if (score >= 90) {
            return "A+";
        }

        if (score >= 80) {
            return "A";
        }

        if (score >= 70) {
            return "B";
        }

        if (score >= 60) {
            return "C";
        }

        return "D";
    }

    private BigDecimal calculateAtr(
            List<ETFPriceHistory> candles
    ) {

        if (candles.size() < 2) {
            return BigDecimal.ZERO;
        }

        BigDecimal total =
                BigDecimal.ZERO;

        for (int i = 1; i < Math.min(
                candles.size(),
                15
        ); i++) {

            ETFPriceHistory current =
                    candles.get(i - 1);

            ETFPriceHistory previous =
                    candles.get(i);

            BigDecimal tr1 =
                    current.getHighPrice()
                            .subtract(
                                    current.getLowPrice()
                            );

            BigDecimal tr2 =
                    current.getHighPrice()
                            .subtract(
                                    previous.getClosePrice()
                            )
                            .abs();

            BigDecimal tr3 =
                    current.getLowPrice()
                            .subtract(
                                    previous.getClosePrice()
                            )
                            .abs();

            BigDecimal tr =
                    tr1.max(tr2)
                            .max(tr3);

            total =
                    total.add(tr);
        }

        return total.divide(
                BigDecimal.valueOf(14),
                4,
                RoundingMode.HALF_UP
        );
    }
}