package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.SelectorHistoryDto;
import com.sharad.platformapi.entity.MarketRegime;
import com.sharad.platformapi.repository.MarketRegimeRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectorHistoryService {

    private final MarketRegimeRepository repository;

    public SelectorHistoryService(
            MarketRegimeRepository repository
    ) {
        this.repository = repository;
    }

    public List<SelectorHistoryDto> getHistory() {

        return repository
                .findTop30ByOrderByTradeDateDesc()
                .stream()
                .map(
                        this::toDto
                )
                .toList();
    }

    private SelectorHistoryDto toDto(
            MarketRegime regime
    ) {

        String recommendedETF;

        if ("RISK_OFF".equals(
                regime.getMarketRegime()
        )) {

            recommendedETF =
                    "GOLDBEES.NS";

        } else {

            recommendedETF =
                    regime.getTopRankedEtf();
        }

        return new SelectorHistoryDto(
                regime.getTradeDate(),
                regime.getMarketRegime(),
                recommendedETF
        );
    }
}