package com.sharad.platformapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DeploymentRequestDto(

        @NotNull(message = "opportunityBuffer is required")
        @DecimalMin(
                value = "0.01",
                inclusive = true,
                message = "opportunityBuffer must be greater than zero"
        )
        BigDecimal opportunityBuffer

) {
}