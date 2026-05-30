package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.EtfScoreDto;
import com.sharad.platformapi.dto.MarketStateDto;
import com.sharad.platformapi.dto.OpportunityResponseDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class OpportunityEngineService {

    private final MarketStateService marketStateService;

    private final EtfUniverseService etfUniverseService;

    private final EtfScoreService etfScoreService;

    public OpportunityEngineService(
            MarketStateService marketStateService,
            EtfUniverseService etfUniverseService,
            EtfScoreService etfScoreService
    ) {
        this.marketStateService = marketStateService;
        this.etfUniverseService = etfUniverseService;
        this.etfScoreService = etfScoreService;
    }

    public OpportunityResponseDto getOpportunities() {

        MarketStateDto market =
                marketStateService.getMarketState();

        List<String> topEtfs =
                etfUniverseService
                        .getActiveSymbols()
                        .stream()
                        .map(etfScoreService::calculate)
                        .sorted(
                                Comparator.comparing(
                                        EtfScoreDto::totalScore
                                ).reversed()
                        )
                        .limit(5)
                        .map(EtfScoreDto::symbol)
                        .toList();

        return new OpportunityResponseDto(
                market.india(),
                market.usa(),
                market.china(),
                market.gold(),
                market.silver(),
                topEtfs
        );
    }
}