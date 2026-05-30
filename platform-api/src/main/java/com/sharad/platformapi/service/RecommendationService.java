package com.sharad.platformapi.service;

import com.sharad.platformapi.config.EtfConstants;
import com.sharad.platformapi.dto.ProbabilityResponseDto;
import com.sharad.platformapi.dto.RecommendationDto;
import com.sharad.platformapi.entity.MarketRegime;
import com.sharad.platformapi.repository.MarketRegimeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    private final MarketRegimeRepository marketRegimeRepository;

    private final ProbabilityEngineService probabilityEngineService;

    public RecommendationService(
            MarketRegimeRepository marketRegimeRepository,
            ProbabilityEngineService probabilityEngineService
    ) {
        this.marketRegimeRepository = marketRegimeRepository;
        this.probabilityEngineService = probabilityEngineService;
    }

    public RecommendationDto getRecommendation() {

        MarketRegime regime =
                marketRegimeRepository.findLatestRegime();

        String recommendedETF;

        int allocationPct;

        if ("RISK_OFF".equals(
                regime.getMarketRegime()
        )) {

            recommendedETF =
                    EtfConstants.DEFAULT_DEFENSIVE_ETF;

            allocationPct =
                    100;

        } else {

            recommendedETF =
                    regime.getTopRankedEtf();

            allocationPct =
                    100;
        }

        ProbabilityResponseDto probability =
                probabilityEngineService.analyze(
                        recommendedETF
                );

        return new RecommendationDto(
                regime.getMarketRegime(),
                recommendedETF,
                probability.confidence(),
                probability.rating(),
                allocationPct
        );
    }
}