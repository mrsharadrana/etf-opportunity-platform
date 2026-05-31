package com.sharad.platformapi.dto;

import java.math.BigDecimal;

public record PortfolioAllocationDto(

        String symbol,

        Integer score,

        Integer allocationPct,

        BigDecimal allocationAmount

) {
}