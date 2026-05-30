package com.sharad.platformapi.service;

import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DrawdownFearFactorProvider
        implements FearFactorProvider {

    private static final String MARKET_SYMBOL = "NIFTYBEES.NS";

    private static final double MAX_DRAWDOWN = -30.0;

    private static final double MIN_DRAWDOWN = 0.0;

    private static final double WEIGHT = 25.0;

    private final ETFPriceHistoryRepository historyRepository;

    public DrawdownFearFactorProvider(
            ETFPriceHistoryRepository historyRepository
    ) {
        this.historyRepository = Objects.requireNonNull(
                historyRepository,
                "historyRepository must not be null"
        );
    }

    @Override
    public FearGreedServiceV2.FearFactorDto calculate() {

        List<ETFPriceHistory> history =
                historyRepository
                        .findBySymbolOrderByTradeDateDesc(
                                MARKET_SYMBOL
                        );

        double rawValue = 0.0;

        if (!history.isEmpty()) {
            ETFPriceHistory latest = history.get(0);

            double currentPrice =
                    latest.getClosePrice() != null
                            ? latest.getClosePrice().doubleValue()
                            : 0.0;

            double highestPrice = history.stream()
                    .map(ETFPriceHistory::getClosePrice)
                    .filter(Objects::nonNull)
                    .mapToDouble(x -> x.doubleValue())
                    .max()
                    .orElse(currentPrice);

            if (highestPrice > 0) {
                rawValue = ((currentPrice - highestPrice)
                        / highestPrice) * 100.0;
            }
        }

        double normalizedScore =
                normalize(rawValue);

        return new FearGreedServiceV2.FearFactorDto(
                "DRAWDOWN_ATH",
                rawValue,
                normalizedScore,
                WEIGHT,
                normalizedScore * WEIGHT
        );
    }

    private double normalize(
            double drawdownPct
    ) {

        if (drawdownPct >= MIN_DRAWDOWN) {
            return 0.0;
        }

        if (drawdownPct <= MAX_DRAWDOWN) {
            return 100.0;
        }

        return ((MIN_DRAWDOWN - drawdownPct)
                / (MIN_DRAWDOWN - MAX_DRAWDOWN))
                * 100.0;
    }
}
