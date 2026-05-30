package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.ScoreHistoryDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreHistoryService {

    private final ETFPriceHistoryRepository repository;

    public ScoreHistoryService(
            ETFPriceHistoryRepository repository
    ) {
        this.repository = repository;
    }

    public List<ScoreHistoryDto> getHistory(
            String symbol,
            int days
    ) {

        List<ETFPriceHistory> history;

        if (days <= 30) {

            history = repository
                    .findTop30BySymbolOrderByTradeDateDesc(
                            symbol
                    );

        } else {

            history = repository
                    .findBySymbolOrderByTradeDateDesc(
                            symbol
                    );
        }

        return history.stream()
                .filter(x -> x.getEtfScore() != null)
                .map(this::toDto)
                .toList();
    }

    private ScoreHistoryDto toDto(
            ETFPriceHistory etf
    ) {

        return new ScoreHistoryDto(
                etf.getTradeDate(),
                etf.getProbabilityScore(),
                etf.getRelativeStrengthScore(),
                etf.getEtfScore()
        );
    }
}