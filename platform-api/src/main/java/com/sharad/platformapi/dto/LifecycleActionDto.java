package com.sharad.platformapi.dto;

import java.math.BigDecimal;

public record LifecycleActionDto(

        String symbol,

        String signal,

        String action,

        BigDecimal currentPrice,

        BigDecimal holdingQuantity,

        BigDecimal sellQuantity,

        BigDecimal sellAmount,

        String state,

        String reason

) {
}