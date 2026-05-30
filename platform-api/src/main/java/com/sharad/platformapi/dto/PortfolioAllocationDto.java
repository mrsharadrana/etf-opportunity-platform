package com.sharad.platformapi.dto;

public record PortfolioAllocationDto(
        String symbol,
        Integer score,
        Integer allocationPct
) {
}