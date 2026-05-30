package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.MarketIndicatorDto;
import com.sharad.platformapi.entity.MarketIndicatorHistory;
import com.sharad.platformapi.repository.MarketIndicatorHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MarketIndicatorService {

    private final MarketIndicatorHistoryRepository repository;

    public MarketIndicatorService(
            MarketIndicatorHistoryRepository repository
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "repository must not be null"
        );
    }

    public MarketIndicatorHistory save(
            MarketIndicatorDto dto
    ) {
        Objects.requireNonNull(dto, "dto must not be null");

        MarketIndicatorHistory entity =
                repository.findByIndicatorNameAndTradeDate(
                                dto.indicatorName(),
                                dto.tradeDate()
                        )
                        .orElseGet(MarketIndicatorHistory::new);

        entity.setIndicatorName(dto.indicatorName());
        entity.setTradeDate(dto.tradeDate());
        entity.setIndicatorValue(dto.indicatorValue());
        entity.setSource(dto.source());
        entity.setCreatedAt(LocalDateTime.now());

        return repository.save(entity);
    }

    public Optional<MarketIndicatorHistory> findLatest(
            String indicatorName
    ) {
        return repository.findLatestByIndicatorName(indicatorName);
    }

    public Optional<java.math.BigDecimal> findLatestValue(
            String indicatorName
    ) {
        return findLatest(indicatorName)
                .map(MarketIndicatorHistory::getIndicatorValue);
    }

    public List<MarketIndicatorHistory> findHistory(
            String indicatorName
    ) {
        return repository.findByIndicatorNameOrderByTradeDateDesc(
                indicatorName
        );
    }
}
