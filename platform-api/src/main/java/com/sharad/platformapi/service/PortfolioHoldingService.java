package com.sharad.platformapi.service;

import com.sharad.platformapi.domain.PositionState;
import com.sharad.platformapi.dto.CreateHoldingRequest;
import com.sharad.platformapi.dto.HoldingResponseDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.entity.PortfolioHolding;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import com.sharad.platformapi.repository.PortfolioHoldingRepository;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PortfolioHoldingService {

    private final PortfolioHoldingRepository repository;

    private final ETFPriceHistoryRepository priceRepository;

    public PortfolioHoldingService(
            PortfolioHoldingRepository repository,
            ETFPriceHistoryRepository priceRepository
    ) {
        this.repository = repository;
        this.priceRepository = priceRepository;
    }

public HoldingResponseDto create(
        CreateHoldingRequest request
) {

    PortfolioHolding holding =
            new PortfolioHolding();

    holding.setSymbol(
            request.symbol()
    );

    holding.setQuantity(
            request.quantity()
    );

    holding.setEntryPrice(
            request.entryPrice()
    );

    holding.setEntryDate(
            LocalDate.now()
    );

    holding.setHighestPriceSinceEntry(
            request.entryPrice()
    );

    holding.setTrailingStopLoss(
            request.entryPrice()
                    .multiply(
                            BigDecimal.valueOf(0.92)
                    )
                    .setScale(
                            2,
                            RoundingMode.HALF_UP
                    )
    );

    holding.setCurrentState(
            PositionState.HOLDING
    );

    PortfolioHolding saved =
            repository.save(
                    holding
            );

    return toDto(
            saved
    );
}

    public List<HoldingResponseDto> getAll() {

        return repository.findAll()
                .stream()
                .map(
                        this::toDto
                )
                .toList();
    }

    public void delete(
            Long id
    ) {
        repository.deleteById(
                id
        );
    }

    @Scheduled(
            cron = "0 0 18 * * MON-FRI"
    )
    public void updateTrailingStops() {

        List<PortfolioHolding> holdings =
                repository.findAll();

        for (PortfolioHolding holding :
                holdings) {

            ETFPriceHistory latest =
                    priceRepository
                            .findTopBySymbolOrderByTradeDateDesc(
                                    holding.getSymbol()
                            );

            if (latest == null) {
                continue;
            }

            BigDecimal currentPrice =
                    latest.getClosePrice();

            if (
                    currentPrice.compareTo(
                            holding.getHighestPriceSinceEntry()
                    ) > 0
            ) {

                holding.setHighestPriceSinceEntry(
                        currentPrice
                );

                holding.setTrailingStopLoss(
                        currentPrice
                                .multiply(
                                        BigDecimal.valueOf(0.92)
                                )
                                .setScale(
                                        2,
                                        RoundingMode.HALF_UP
                                )
                );

                holding.setCurrentState(
                        PositionState.TRAILING
                );

                repository.save(
                        holding
                );
            }
        }
    }

    private HoldingResponseDto toDto(
            PortfolioHolding holding
    ) {

        ETFPriceHistory latest =
                priceRepository
                        .findTopBySymbolOrderByTradeDateDesc(
                                holding.getSymbol()
                        );

        BigDecimal currentPrice =
                latest.getClosePrice();

        BigDecimal pnl =
                currentPrice.subtract(
                                holding.getEntryPrice()
                        )
                        .multiply(
                                holding.getQuantity()
                        )
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        long holdingDays =
                ChronoUnit.DAYS.between(
                        holding.getEntryDate(),
                        LocalDate.now()
                );

        return new HoldingResponseDto(
                holding.getId(),
                holding.getSymbol(),
                holding.getQuantity(),
                holding.getEntryPrice(),
                currentPrice,
                pnl,
                holdingDays,
                holding.getTrailingStopLoss(),
                holding.getCurrentState()
                        .name()
        );
    }
}