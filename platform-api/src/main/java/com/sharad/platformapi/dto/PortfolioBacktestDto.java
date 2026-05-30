package com.sharad.platformapi.dto;

public record PortfolioBacktestDto(
        double recommendationReturn,
        double allocatorReturn,
        String winner
) {
}