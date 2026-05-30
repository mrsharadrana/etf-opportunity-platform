package com.sharad.platformapi.service;

import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MarketBreadthFearFactorProvider
        implements FearFactorProvider {

    private static final double MAX_BREADTH = 80.0;

    private static final double MIN_BREADTH = 20.0;

    private static final double WEIGHT = 20.0;

    private final EtfUniverseService universeService;

    private final ETFPriceHistoryRepository historyRepository;

    public MarketBreadthFearFactorProvider(
            EtfUniverseService universeService,
            ETFPriceHistoryRepository historyRepository
    ) {
        this.universeService = Objects.requireNonNull(
                universeService,
                "universeService must not be null"
        );
        this.historyRepository = Objects.requireNonNull(
                historyRepository,
                "historyRepository must not be null"
        );
    }

    @Override
    public FearGreedServiceV2.FearFactorDto calculate() {

        List<String> symbols =
                universeService.getActiveSymbols();

        java.time.LocalDate latestDate =
                historyRepository.findMaxTradeDate();

        if (latestDate == null || symbols.isEmpty()) {
            return new FearGreedServiceV2.FearFactorDto(
                    "MARKET_BREADTH",
                    0.0,
                    100.0,
                    WEIGHT,
                    100.0 * WEIGHT
            );
        }

        List<ETFPriceHistory> latestRows =
                historyRepository
                        .findByTradeDateAndSymbolIn(
                                latestDate,
                                symbols
                        );

        int total = 0;
        int above200 = 0;

        for (ETFPriceHistory latest : latestRows) {
            if (latest.getClosePrice() == null
                    || latest.getSma200() == null) {
                continue;
            }

            total++;

            double close =
                    latest.getClosePrice().doubleValue();
            double sma200 =
                    latest.getSma200().doubleValue();

            if (close >= sma200) {
                above200++;
            }
        }

        double breadthPct =
                total <= 0
                        ? 0.0
                        : (above200 / (double) total) * 100.0;

        double normalizedScore =
                normalize(breadthPct);

        return new FearGreedServiceV2.FearFactorDto(
                "MARKET_BREADTH",
                breadthPct,
                normalizedScore,
                WEIGHT,
                normalizedScore * WEIGHT
        );
    }

    private double normalize(
            double breadthPct
    ) {

        if (breadthPct >= MAX_BREADTH) {
            return 0.0;
        }

        if (breadthPct <= MIN_BREADTH) {
            return 100.0;
        }

        return ((MAX_BREADTH - breadthPct)
                / (MAX_BREADTH - MIN_BREADTH))
                * 100.0;
    }
}
