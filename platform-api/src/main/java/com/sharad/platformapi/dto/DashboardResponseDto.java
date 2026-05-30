package com.sharad.platformapi.dto;

import java.util.List;

public record DashboardResponseDto(

        SelectorResponseDto selector,

        SelectorPerformanceDto performance,

        List<SelectorHistoryDto> history

) {
}