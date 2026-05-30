package com.sharad.platformapi.dto;

import java.math.BigDecimal;
import java.util.List;

public record AllocationRecommendationDto(

        int fearScore,

        String fearState,

        String crashLayer,

        int deployPercent,

        BigDecimal deployAmount,

        List<EtfAllocationDto> allocations

) {
}