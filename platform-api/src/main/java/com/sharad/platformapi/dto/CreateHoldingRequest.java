package com.sharad.platformapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateHoldingRequest(

        @NotBlank(message = "Symbol is required")
        String symbol,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be greater than 0")
        BigDecimal quantity,

        @NotNull(message = "Entry price is required")
        @Positive(message = "Entry price must be greater than 0")
        BigDecimal entryPrice

) {
}