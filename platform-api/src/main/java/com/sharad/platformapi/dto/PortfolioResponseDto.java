package com.sharad.platformapi.dto;

import java.util.List;

public record PortfolioResponseDto(
        Integer totalScore,
        List<PortfolioAllocationDto> allocations
) {
}