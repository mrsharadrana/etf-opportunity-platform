package com.sharad.platformapi.service;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class IndiaVixFearFactorProvider
        implements FearFactorProvider {

    private static final String INDIA_VIX_INDICATOR = "INDIA_VIX";

    private static final double MIN_VIX = 12.0;

    private static final double MAX_VIX = 38.0;

    private static final double WEIGHT = 30.0;

    private final MarketIndicatorService marketIndicatorService;

    public IndiaVixFearFactorProvider(
            MarketIndicatorService marketIndicatorService
    ) {
        this.marketIndicatorService = Objects.requireNonNull(
                marketIndicatorService,
                "marketIndicatorService must not be null"
        );
    }

    @Override
    public FearGreedServiceV2.FearFactorDto calculate() {

        double rawValue = marketIndicatorService
                .findLatestValue(INDIA_VIX_INDICATOR)
                .map(java.math.BigDecimal::doubleValue)
                .orElse(MIN_VIX);

        double normalizedScore =
                normalize(rawValue);

        return new FearGreedServiceV2.FearFactorDto(
                "INDIA_VIX",
                rawValue,
                normalizedScore,
                WEIGHT,
                normalizedScore * WEIGHT
        );
    }

    private double normalize(
            double value
    ) {

        if (value <= MIN_VIX) {
            return 0.0;
        }

        if (value >= MAX_VIX) {
            return 100.0;
        }

        return ((value - MIN_VIX)
                / (MAX_VIX - MIN_VIX))
                * 100.0;
    }
}
