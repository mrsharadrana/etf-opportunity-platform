package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.SignalHistoryDto;
import com.sharad.platformapi.dto.SignalValidationDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SignalValidationService {

    private final SignalHistoryService signalHistoryService;

    private final ETFPriceHistoryRepository repository;

    public SignalValidationService(
            SignalHistoryService signalHistoryService,
            ETFPriceHistoryRepository repository
    ) {
        this.signalHistoryService = signalHistoryService;
        this.repository = repository;
    }

    public SignalValidationDto validate(
            String symbol
    ) {

        List<SignalHistoryDto> signals =
                signalHistoryService.generateSignals();

        int signalCount = 0;
        int wins = 0;

        double totalReturn = 0.0;

        double bestReturn =
                Double.NEGATIVE_INFINITY;

        double worstReturn =
                Double.POSITIVE_INFINITY;

        long totalHoldingDays = 0;

        SignalHistoryDto entrySignal = null;

        for (int i = 0; i < signals.size(); i++) {

            SignalHistoryDto current =
                    signals.get(i);

            if (entrySignal == null) {

                if (symbol.equalsIgnoreCase(
                        current.selectedEtf()
                )) {
                    entrySignal = current;
                }

                continue;
            }

            boolean exitedPosition =
                    !symbol.equalsIgnoreCase(
                            current.selectedEtf()
                    );

            boolean lastSignal =
                    i == signals.size() - 1;

            if (!exitedPosition && !lastSignal) {
                continue;
            }

            ETFPriceHistory entry =
                    repository.findBySymbolAndTradeDate(
                            symbol,
                            entrySignal.tradeDate()
                    );

            ETFPriceHistory exit =
                    repository.findBySymbolAndTradeDate(
                            symbol,
                            current.tradeDate()
                    );

            if (entry != null && exit != null) {

                double entryPrice =
                        entry.getClosePrice()
                                .doubleValue();

                double exitPrice =
                        exit.getClosePrice()
                                .doubleValue();

                double returnPct =
                        ((exitPrice - entryPrice)
                                / entryPrice)
                                * 100.0;

                signalCount++;

                totalReturn += returnPct;

                bestReturn =
                        Math.max(
                                bestReturn,
                                returnPct
                        );

                worstReturn =
                        Math.min(
                                worstReturn,
                                returnPct
                        );

                if (returnPct > 0) {
                    wins++;
                }

                totalHoldingDays +=
                        ChronoUnit.DAYS.between(
                                entrySignal.tradeDate(),
                                current.tradeDate()
                        );
            }

            entrySignal = null;

            if (symbol.equalsIgnoreCase(
                    current.selectedEtf()
            )) {
                entrySignal = current;
            }
        }

        if (signalCount == 0) {

            return new SignalValidationDto(
                    symbol,
                    0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    "LOW"
            );
        }

        double winRate =
                ((double) wins
                        / signalCount)
                        * 100.0;

        double averageReturn =
                totalReturn
                        / signalCount;

        double averageHoldingDays =
                (double) totalHoldingDays
                        / signalCount;

        String confidence;

        if (winRate >= 70) {
            confidence = "HIGH";
        } else if (winRate >= 55) {
            confidence = "MEDIUM";
        } else {
            confidence = "LOW";
        }

        return new SignalValidationDto(
                symbol,
                signalCount,
                winRate,
                averageReturn,
                bestReturn,
                worstReturn,
                averageHoldingDays,
                confidence
        );
    }
}