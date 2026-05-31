package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.BacktestResultDto;
import com.sharad.platformapi.dto.SignalHistoryDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class BacktestService {

    private final SignalHistoryService signalHistoryService;

    private final ETFPriceHistoryRepository repository;

    public BacktestService(
            SignalHistoryService signalHistoryService,
            ETFPriceHistoryRepository repository
    ) {
        this.signalHistoryService = signalHistoryService;
        this.repository = repository;
    }

    public BacktestResultDto runBacktest() {

        double capital = 100000.0;
        double initialCapital = capital;

        double peakCapital = capital;
        double maxDrawdown = 0.0;

        int trades = 0;
        int wins = 0;
        int losses = 0;

        double totalTradeReturn = 0.0;

        double bestTradeReturn = Double.NEGATIVE_INFINITY;

        double worstTradeReturn = Double.POSITIVE_INFINITY;

        long totalHoldingDays = 0;

        List<Double> tradeReturns = new ArrayList<>();

        List<SignalHistoryDto> signals =
                signalHistoryService.generateSignals();

        if (signals.size() < 2) {

            return new BacktestResultDto(
                    initialCapital,
                    capital,
                    0.0,
                    0.0,
                    0,
                    0,
                    0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0
            );
        }

        String currentPosition =
                signals.getFirst().selectedEtf();

        SignalHistoryDto positionStartSignal =
                signals.getFirst();

        for (int i = 1; i < signals.size(); i++) {

            SignalHistoryDto signal =
                    signals.get(i);

            boolean positionChanged =
                    !currentPosition.equalsIgnoreCase(
                            signal.selectedEtf()
                    );

            boolean lastSignal =
                    i == signals.size() - 1;

            if (!positionChanged && !lastSignal) {
                continue;
            }

            ETFPriceHistory entry =
                    repository.findBySymbolAndTradeDate(
                            currentPosition,
                            positionStartSignal.tradeDate()
                    );

            ETFPriceHistory exit =
                    repository.findBySymbolAndTradeDate(
                            currentPosition,
                            signal.tradeDate()
                    );

            if (entry != null && exit != null) {

                double entryPrice =
                        entry.getClosePrice()
                                .doubleValue();

                double exitPrice =
                        exit.getClosePrice()
                                .doubleValue();

                double tradeReturn =
                        (exitPrice - entryPrice)
                                / entryPrice;

                double tradeReturnPct =
                        tradeReturn * 100.0;

                tradeReturns.add(
                        tradeReturnPct
                );

                totalTradeReturn += tradeReturnPct;

                bestTradeReturn =
                        Math.max(
                                bestTradeReturn,
                                tradeReturnPct
                        );

                worstTradeReturn =
                        Math.min(
                                worstTradeReturn,
                                tradeReturnPct
                        );

                long holdingDays =
                        ChronoUnit.DAYS.between(
                                positionStartSignal.tradeDate(),
                                signal.tradeDate()
                        );

                totalHoldingDays += holdingDays;

                capital =
                        capital * (1.0 + tradeReturn);

                trades++;

                if (tradeReturn > 0) {
                    wins++;
                } else {
                    losses++;
                }

                if (capital > peakCapital) {
                    peakCapital = capital;
                }

                double drawdown =
                        ((peakCapital - capital)
                                / peakCapital)
                                * 100.0;

                if (drawdown > maxDrawdown) {
                    maxDrawdown = drawdown;
                }
            }

            currentPosition =
                    signal.selectedEtf();

            positionStartSignal =
                    signal;
        }

        double totalReturnPct =
                ((capital - initialCapital)
                        / initialCapital)
                        * 100.0;

        long days =
                ChronoUnit.DAYS.between(
                        signals.getFirst().tradeDate(),
                        signals.getLast().tradeDate()
                );

        double years =
                days / 365.25;

        double cagr =
                years > 0
                        ? (Math.pow(
                                capital / initialCapital,
                                1.0 / years
                        ) - 1.0)
                        * 100.0
                        : 0.0;

        double winRate =
                trades > 0
                        ? ((double) wins / trades)
                        * 100.0
                        : 0.0;

        double averageTradeReturn =
                trades > 0
                        ? totalTradeReturn / trades
                        : 0.0;

        double averageHoldingDays =
                trades > 0
                        ? (double) totalHoldingDays / trades
                        : 0.0;

        double sharpeRatio =
                calculateSharpeRatio(
                        tradeReturns
                );

        double sortinoRatio =
                calculateSortinoRatio(
                        tradeReturns
                );

        if (bestTradeReturn == Double.NEGATIVE_INFINITY) {
            bestTradeReturn = 0.0;
        }

        if (worstTradeReturn == Double.POSITIVE_INFINITY) {
            worstTradeReturn = 0.0;
        }

        return new BacktestResultDto(
                initialCapital,
                capital,
                totalReturnPct,
                cagr,
                trades,
                wins,
                losses,
                winRate,
                maxDrawdown,
                averageTradeReturn,
                bestTradeReturn,
                worstTradeReturn,
                averageHoldingDays,
                sharpeRatio,
                sortinoRatio
        );
    }

    private double calculateSharpeRatio(
            List<Double> returns
    ) {

        if (returns.size() < 2) {
            return 0.0;
        }

        double mean =
                returns.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0);

        double variance =
                returns.stream()
                        .mapToDouble(
                                r -> Math.pow(
                                        r - mean,
                                        2
                                )
                        )
                        .average()
                        .orElse(0.0);

        double stdDev =
                Math.sqrt(
                        variance
                );

        if (stdDev == 0.0) {
            return 0.0;
        }

        return mean / stdDev;
    }

    private double calculateSortinoRatio(
            List<Double> returns
    ) {

        if (returns.size() < 2) {
            return 0.0;
        }

        double mean =
                returns.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0);

        List<Double> downsideReturns =
                returns.stream()
                        .filter(
                                r -> r < 0
                        )
                        .toList();

        if (downsideReturns.isEmpty()) {
            return 999.0;
        }

        double downsideVariance =
                downsideReturns.stream()
                        .mapToDouble(
                                r -> Math.pow(
                                        r,
                                        2
                                )
                        )
                        .average()
                        .orElse(0.0);

        double downsideDeviation =
                Math.sqrt(
                        downsideVariance
                );

        if (downsideDeviation == 0.0) {
            return 0.0;
        }

        return mean / downsideDeviation;
    }
}