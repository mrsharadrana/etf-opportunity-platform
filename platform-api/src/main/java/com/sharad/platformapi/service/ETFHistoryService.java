package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.RankingDto;

import com.sharad.platformapi.entity.ETFPriceHistory;

import com.sharad.platformapi.repository.ETFPriceHistoryRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ETFHistoryService {

    private final ETFPriceHistoryRepository repository;

    public ETFHistoryService(
            ETFPriceHistoryRepository repository
    ) {
        this.repository = repository;
    }

    public List<RankingDto> getETFHistory(
            String symbol
    ) {

        List<ETFPriceHistory> history =
                repository
                        .findTop30BySymbolOrderByTradeDateDesc(
                                symbol
                        );

        return history.stream()
                .map(this::mapToDto)
                .toList();
    }

    private RankingDto mapToDto(
            ETFPriceHistory entity
    ) {

        RankingDto dto =
                new RankingDto();

        dto.setSymbol(
                entity.getSymbol()
        );

        dto.setTradeDate(
                entity.getTradeDate()
        );

        dto.setClosePrice(
                entity.getClosePrice()
        );

        dto.setMomentumScore(
                entity.getMomentumScore()
        );

        dto.setRank(
                entity.getRank()
        );

        dto.setSignal(
                entity.getSignal()
        );

        return dto;
    }
}