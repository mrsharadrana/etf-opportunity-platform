package com.sharad.platformapi.dto;

import java.math.BigDecimal;

public record SignalStrengthDto(

        String symbol,

        Integer rank,

        BigDecimal momentumScore,

        String signal,

        String reason

) {
}