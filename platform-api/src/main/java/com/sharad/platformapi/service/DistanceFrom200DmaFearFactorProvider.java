package com.sharad.platformapi.service;

import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DistanceFrom200DmaFearFactorProvider
        implements FearFactorProvider {

    private static final String MARKET_SYMBOL = "NIFTYBEES.NS";

    private static final double MAX_DISTANCE = 10.0;

    private static final double MIN_DISTANCE = -20.0;

    private static final double WEIGHT = 25.0;

    private final ETFPriceHistoryRepository historyRepository;

    public DistanceFrom200DmaFearFactorProvider(
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
            double closePrice = latest.getClosePrice() != null
                    ? latest.getClosePrice().doubleValue()
                    : 0.0;
            double sma200 = latest.getSma200() != null
                    ? latest.getSma200().doubleValue()
                    : 0.0;

            if (sma200 > 0) {
                rawValue = ((closePrice - sma200) / sma200) * 100.0;
            }
        }

        double normalizedScore =
                normalize(rawValue);

        return new FearGreedServiceV2.FearFactorDto(
                "DISTANCE_FROM_200_DMA",
                rawValue,
                normalizedScore,
                WEIGHT,
                normalizedScore * WEIGHT
        );
    }

    private double normalize(
            double distancePct
    ) {

        if (distancePct >= MAX_DISTANCE) {
            return 0.0;
        }

        if (distancePct <= MIN_DISTANCE) {
            return 100.0;
        }

        return ((MAX_DISTANCE - distancePct)
                / (MAX_DISTANCE - MIN_DISTANCE))
                * 100.0;
    }
}
