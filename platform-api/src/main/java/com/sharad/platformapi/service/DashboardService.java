package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.DashboardResponseDto;
import com.sharad.platformapi.dto.SelectorHistoryDto;
import com.sharad.platformapi.dto.SelectorPerformanceDto;
import com.sharad.platformapi.dto.SelectorResponseDto;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final SelectorService selectorService;

    private final SelectorPerformanceService
            performanceService;

    private final SelectorHistoryService
            historyService;

    public DashboardService(
            SelectorService selectorService,
            SelectorPerformanceService performanceService,
            SelectorHistoryService historyService
    ) {
        this.selectorService = selectorService;
        this.performanceService = performanceService;
        this.historyService = historyService;
    }

    public DashboardResponseDto getDashboard() {

        SelectorResponseDto selector =
                selectorService.getSelector();

        SelectorPerformanceDto performance =
                performanceService.getPerformance();

        List<SelectorHistoryDto> history =
                historyService.getHistory();

        return new DashboardResponseDto(
                selector,
                performance,
                history
        );
    }
}