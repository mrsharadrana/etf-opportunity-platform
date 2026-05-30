package com.sharad.platformapi.dto;

import java.math.BigDecimal;

public record EtfAllocationDto(

        String symbol,

        BigDecimal momentumScore,

        BigDecimal weightPercent,

        BigDecimal allocationAmount

) {
}