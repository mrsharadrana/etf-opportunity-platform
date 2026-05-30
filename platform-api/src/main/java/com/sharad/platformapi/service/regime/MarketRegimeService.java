package com.sharad.platformapi.service.regime;

import com.sharad.platformapi.dto.MarketRegimeDto;

import com.sharad.platformapi.entity.MarketRegime;

import com.sharad.platformapi.repository.MarketRegimeRepository;

import org.springframework.stereotype.Service;

@Service
public class MarketRegimeService {

    private final MarketRegimeRepository repository;

    public MarketRegimeService(
            MarketRegimeRepository repository
    ) {
        this.repository = repository;
    }

    public MarketRegimeDto getLatestRegime() {

        MarketRegime regime =
                repository.findLatestRegime();

        MarketRegimeDto dto =
                new MarketRegimeDto();

        dto.setTradeDate(
                regime.getTradeDate()
        );

        dto.setMarketRegime(
                regime.getMarketRegime()
        );

        dto.setTopRankedEtf(
                regime.getTopRankedEtf()
        );

        return dto;
    }
}