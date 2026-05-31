package com.sharad.platformapi.dto;

import java.math.BigDecimal;

public record PortfolioHoldingDto(

        String symbol,

        Integer weightPct,

        BigDecimal amount

) {
}