package com.sharad.platformapi.dto;

public record SignalValidationDto(

        String symbol,

        Integer signalCount,

        Double winRatePct,

        Double averageReturnPct,

        Double bestReturnPct,

        Double worstReturnPct,

        Double averageHoldingDays,

        String confidence

) {
}