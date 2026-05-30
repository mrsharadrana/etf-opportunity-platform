package com.sharad.platformapi.scheduler;

import com.sharad.platformapi.service.analytics.AnalyticsEngineService;

import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;

@Component
public class AnalyticsScheduler {

    private final AnalyticsEngineService analyticsService;

    public AnalyticsScheduler(
            AnalyticsEngineService analyticsService
    ) {
        this.analyticsService = analyticsService;
    }

    @Scheduled(cron = "0 0 18 * * MON-FRI")
    public void runDailyAnalytics() {

        System.out.println(
                "Running scheduled analytics..."
        );

        analyticsService.generateRankings();

        System.out.println(
                "Analytics completed successfully"
        );
    }
}