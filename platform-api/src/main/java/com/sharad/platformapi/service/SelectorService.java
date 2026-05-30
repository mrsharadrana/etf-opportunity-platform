package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.RecommendationDto;
import com.sharad.platformapi.dto.SelectorRankingDto;
import com.sharad.platformapi.dto.SelectorResponseDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectorService {

    private final RecommendationService recommendationService;

    private final ETFPriceHistoryRepository repository;

    public SelectorService(
            RecommendationService recommendationService,
            ETFPriceHistoryRepository repository
    ) {
        this.recommendationService = recommendationService;
        this.repository = repository;
    }

    public SelectorResponseDto getSelector() {

        RecommendationDto recommendation =
                recommendationService.getRecommendation();

        List<SelectorRankingDto> rankings =
                repository.findLatestRankings()
                        .stream()
                        .limit(3)
                        .map(
                                e -> new SelectorRankingDto(
                                        e.getSymbol(),
                                        e.getRank(),
                                        e.getMomentumScore()
                                                .doubleValue(),
                                        e.getSignal()
                                )
                        )
                        .toList();

        return new SelectorResponseDto(
                recommendation.marketRegime(),
                recommendation.recommendedETF(),
                recommendation.confidence(),
                recommendation.rating(),
                rankings
        );
    }
}