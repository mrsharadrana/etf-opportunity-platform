package com.sharad.platformapi.dto;

import java.math.BigDecimal;
import java.util.List;

public record PortfolioResponseDto(

        BigDecimal capital,

        BigDecimal investedCapital,

        BigDecimal cashReserve,

        String marketRegime,

        Integer totalScore,

        List<PortfolioAllocationDto> allocations

) {
}