package com.sharad.platformapi.dto;

public record BacktestResultDto(

        Double initialCapital,

        Double finalCapital,

        Double totalReturnPct,

        Double cagr,

        Integer tradesExecuted,

        Integer winningTrades,

        Integer losingTrades,

        Double winRatePct,

        Double maxDrawdownPct,

        Double averageTradeReturnPct,

        Double bestTradeReturnPct,

        Double worstTradeReturnPct,

        Double averageHoldingDays

) {
}