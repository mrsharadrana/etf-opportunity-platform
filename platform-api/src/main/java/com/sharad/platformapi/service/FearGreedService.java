package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.FearGreedDto;
import com.sharad.platformapi.entity.ETFPriceHistory;
import com.sharad.platformapi.repository.ETFPriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FearGreedService {

    private final ETFPriceHistoryRepository repository;

    public FearGreedService(
            ETFPriceHistoryRepository repository
    ) {
        this.repository = repository;
    }

    public FearGreedDto calculate() {

        List<ETFPriceHistory> history =
                repository.findBySymbolOrderByTradeDateAsc(
                        "NIFTYBEES.NS"
                );

        if (history.isEmpty()) {

            return new FearGreedDto(
                    50,
                    "NEUTRAL",
                    0,
                    0
            );
        }

        ETFPriceHistory latest =
                history.getLast();

        double currentPrice =
                latest.getClosePrice()
                        .doubleValue();

        double sma200 =
                latest.getSma200() != null
                        ? latest.getSma200()
                        .doubleValue()
                        : currentPrice;

        double highest =
                history.stream()
                        .mapToDouble(x ->
                                x.getClosePrice()
                                        .doubleValue())
                        .max()
                        .orElse(currentPrice);

        double drawdownPct =
                ((currentPrice - highest)
                        / highest)
                        * 100.0;

        double distanceFrom200DMA =
                ((currentPrice - sma200)
                        / sma200)
                        * 100.0;

        int drawdownScore =
                calculateDrawdownScore(
                        drawdownPct
                );

        int dmaScore =
                calculateDmaScore(
                        distanceFrom200DMA
                );

        int fearScore =
                (int) Math.round(
                        drawdownScore * 0.6
                                + dmaScore * 0.4
                );

        String state =
                determineState(
                        fearScore
                );

        return new FearGreedDto(
                fearScore,
                state,
                round(drawdownPct),
                round(distanceFrom200DMA)
        );
    }

    private int calculateDrawdownScore(
            double drawdown
    ) {

        if (drawdown <= -30) {
            return 100;
        }

        if (drawdown <= -20) {
            return 80;
        }

        if (drawdown <= -15) {
            return 60;
        }

        if (drawdown <= -10) {
            return 40;
        }

        if (drawdown <= -5) {
            return 20;
        }

        return 0;
    }

    private int calculateDmaScore(
            double distance
    ) {

        if (distance <= -20) {
            return 100;
        }

        if (distance <= -15) {
            return 80;
        }

        if (distance <= -10) {
            return 60;
        }

        if (distance <= -5) {
            return 40;
        }

        if (distance <= 0) {
            return 20;
        }

        return 0;
    }

    private String determineState(
            int score
    ) {

        if (score >= 80) {
            return "PANIC";
        }

        if (score >= 60) {
            return "FEAR";
        }

        if (score >= 40) {
            return "NEUTRAL";
        }

        if (score >= 20) {
            return "GREED";
        }

        return "EUPHORIA";
    }

    private double round(
            double value
    ) {
        return Math.round(value * 100.0)
                / 100.0;
    }
}