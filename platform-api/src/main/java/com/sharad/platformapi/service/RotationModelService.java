package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.RotationModelDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.entity.MarketRegime;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import com.sharad.platformapi.repository.MarketRegimeRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RotationModelService {

    private final MarketRegimeRepository marketRegimeRepository;

    private final ETFPriceHistoryRepository etfRepository;

    public RotationModelService(
            MarketRegimeRepository marketRegimeRepository,
            ETFPriceHistoryRepository etfRepository
    ) {
        this.marketRegimeRepository = marketRegimeRepository;
        this.etfRepository = etfRepository;
    }

    public RotationModelDto getRotationModel() {

        MarketRegime regime =
                marketRegimeRepository.findLatestRegime();

        List<ETFPriceHistory> rankings =
                etfRepository.findLatestRankings();

        if (regime == null || rankings.isEmpty()) {

            return new RotationModelDto(
                    "UNKNOWN",
                    "NONE",
                    0,
                    "HOLD",
                    List.of("No data available")
            );
        }

        ETFPriceHistory topEtf =
                rankings.getFirst();

        List<String> reasoning =
                new ArrayList<>();

        int confidence = 0;

        if (topEtf.getMomentumScore() != null
                && topEtf.getMomentumScore().doubleValue() > 20) {

            confidence += 40;

            reasoning.add(
                    "Highest momentum score"
            );
        }

        if ("BUY".equalsIgnoreCase(
                topEtf.getSignal()
        )) {

            confidence += 30;

            reasoning.add(
                    "Strong ranking signal"
            );
        }

        if ("RISK_ON".equalsIgnoreCase(
                regime.getMarketRegime()
        )) {

            confidence += 30;

            reasoning.add(
                    "Risk-on market regime"
            );
        }

        String rating;

        if (confidence >= 80) {

            rating = "STRONG_BUY";

        } else if (confidence >= 60) {

            rating = "BUY";

        } else if (confidence >= 40) {

            rating = "HOLD";

        } else {

            rating = "AVOID";
        }

        return new RotationModelDto(
                regime.getMarketRegime(),
                topEtf.getSymbol(),
                confidence,
                rating,
                reasoning
        );
    }
}