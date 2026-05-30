package com.sharad.platformapi.service.ranking;

import com.sharad.platformapi.dto.RankingDto;

import com.sharad.platformapi.entity.ETFPriceHistory;

import com.sharad.platformapi.repository.ETFPriceHistoryRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingService {

    private final ETFPriceHistoryRepository repository;

    public RankingService(
            ETFPriceHistoryRepository repository
    ) {
        this.repository = repository;
    }

    public List<RankingDto> getLatestRankings() {

        List<ETFPriceHistory> rankings =
                repository.findLatestRankings();

        return rankings.stream()
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