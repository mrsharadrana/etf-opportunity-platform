package com.sharad.platformapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TradeCardDto(

        String symbol,

        String signal,

        BigDecimal entryPrice,

        BigDecimal targetPrice,

        String targetSource,

        String targetConfidence,

        BigDecimal initialStopLoss,

        BigDecimal trailingStopLoss,

        BigDecimal riskRewardRatio,

        BigDecimal expectedReturnPct,

        BigDecimal expectedRiskPct,

        Integer tradeQualityScore,

        String tradeGrade,

        String marketRegime,

        String message,

        LocalDateTime lastUpdated

) {
}