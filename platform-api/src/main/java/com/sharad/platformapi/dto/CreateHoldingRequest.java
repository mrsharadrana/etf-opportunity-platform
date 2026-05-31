package com.sharad.platformapi.dto;

import java.math.BigDecimal;

public record CreateHoldingRequest(

        String symbol,

        BigDecimal quantity,

        BigDecimal entryPrice

) {
}