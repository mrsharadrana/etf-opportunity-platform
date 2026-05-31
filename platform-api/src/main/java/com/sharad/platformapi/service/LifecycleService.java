package com.sharad.platformapi.service;

import com.sharad.platformapi.domain.PositionState;
import com.sharad.platformapi.dto.LifecycleActionDto;
import com.sharad.platformapi.dto.SignalStrengthDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.entity.PortfolioHolding;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import com.sharad.platformapi.repository.PortfolioHoldingRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class LifecycleService {

    private final SignalStrengthService signalStrengthService;

    private final PortfolioHoldingRepository holdingRepository;

    private final ETFPriceHistoryRepository priceRepository;

    public LifecycleService(
            SignalStrengthService signalStrengthService,
            PortfolioHoldingRepository holdingRepository,
            ETFPriceHistoryRepository priceRepository
    ) {
        this.signalStrengthService =
                signalStrengthService;

        this.holdingRepository =
                holdingRepository;

        this.priceRepository =
                priceRepository;
    }

    @Transactional
public List<LifecycleActionDto> generate() {

        List<LifecycleActionDto> actions =
                new ArrayList<>();

        List<SignalStrengthDto> signals =
                signalStrengthService.generateSignals();

        for (SignalStrengthDto signal :
                signals) {

            List<PortfolioHolding> holdings =
                    holdingRepository.findAllBySymbol(
                            signal.symbol()
                    );

            ETFPriceHistory latest =
                    priceRepository
                            .findTopBySymbolOrderByTradeDateDesc(
                                    signal.symbol()
                            );

            BigDecimal currentPrice =
                    latest != null
                            ? latest.getClosePrice()
                            : BigDecimal.ZERO;

            actions.add(
                    buildAction(
                            signal,
                            holdings,
                            currentPrice
                    )
            );
        }

        return actions;
    }

    private LifecycleActionDto buildAction(
            SignalStrengthDto signal,
            List<PortfolioHolding> holdings,
            BigDecimal currentPrice
    ) {

        BigDecimal totalQuantity =
                holdings.stream()
                        .map(
                                PortfolioHolding::getQuantity
                        )
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        PortfolioHolding primaryHolding =
                holdings.isEmpty()
                        ? null
                        : holdings.get(0);

        boolean trailingStopBreached =
                holdings.stream()
                        .anyMatch(
                                holding ->
                                        currentPrice.compareTo(
                                                holding.getTrailingStopLoss()
                                        ) < 0
                                                &&
                                                holding.getCurrentState()
                                                        != PositionState.EXITED
                        );

        if (
                trailingStopBreached
        ) {

            for (PortfolioHolding holding :
                    holdings) {

                if (
                        currentPrice.compareTo(
                                holding.getTrailingStopLoss()
                        ) < 0
                                &&
                                holding.getCurrentState()
                                != PositionState.EXITED
                ) {

                    holding.setCurrentState(
                            PositionState.EXITED
                    );

                    holdingRepository.save(
                            holding
                    );
                }
            }

            BigDecimal sellAmount =
                    totalQuantity
                            .multiply(
                                    currentPrice
                            )
                            .setScale(
                                    2,
                                    RoundingMode.HALF_UP
                            );

            return new LifecycleActionDto(
                    signal.symbol(),
                    signal.signal(),
                    "EXIT",
                    currentPrice,
                    totalQuantity,
                    totalQuantity,
                    sellAmount,
                    PositionState.EXITED.name(),
                    "TRAILING_STOP_BREACHED"
            );
        }

        String action =
                normalizeAction(
                        signal.signal()
                );

        BigDecimal sellQuantity =
                BigDecimal.ZERO;

        if (
                "REDUCE_20".equals(
                        signal.signal()
                )
        ) {

            sellQuantity =
                    totalQuantity.multiply(
                            BigDecimal.valueOf(
                                    0.20
                            )
                    );

        } else if (
                "REDUCE_40".equals(
                        signal.signal()
                )
        ) {

            sellQuantity =
                    totalQuantity.multiply(
                            BigDecimal.valueOf(
                                    0.40
                            )
                    );

        } else if (
                "EXIT".equals(
                        signal.signal()
                )
        ) {

            sellQuantity =
                    totalQuantity;
        }

        BigDecimal sellAmount =
                sellQuantity
                        .multiply(
                                currentPrice
                        )
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        String state =
                primaryHolding != null
                        ? primaryHolding
                        .getCurrentState()
                        .name()
                        : "NO_POSITION";

        return new LifecycleActionDto(
                signal.symbol(),
                signal.signal(),
                action,
                currentPrice,
                totalQuantity,
                sellQuantity,
                sellAmount,
                state,
                signal.reason()
        );
    }

    private String normalizeAction(
            String signal
    ) {

        if (
                "STRONG_BUY".equals(
                        signal
                )
        ) {

            return "BUY";
        }

        return signal;
    }
}