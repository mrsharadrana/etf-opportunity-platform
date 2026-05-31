package com.sharad.platformapi.dto;

import java.math.BigDecimal;

public record HoldingResponseDto(

        Long id,

        String symbol,

        BigDecimal quantity,

        BigDecimal entryPrice,

        BigDecimal currentPrice,

        BigDecimal pnl,

        Long holdingDays,

        BigDecimal trailingStopLoss,

        String state

) {
}