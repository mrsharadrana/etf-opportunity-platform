package com.sharad.platformapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ActionPlanRequestDto(

        @NotNull
        @DecimalMin("0.01")
        BigDecimal opportunityBuffer

) {
}