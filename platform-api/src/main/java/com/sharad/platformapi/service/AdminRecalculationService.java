package com.sharad.platformapi.service;

import com.sharad.platformapi.service.analytics.AnalyticsEngineService;
import org.springframework.stereotype.Service;

@Service
public class AdminRecalculationService {

    private final AnalyticsEngineService analyticsEngineService;

    private final ScorePersistenceService scorePersistenceService;

    public AdminRecalculationService(
            AnalyticsEngineService analyticsEngineService,
            ScorePersistenceService scorePersistenceService
    ) {
        this.analyticsEngineService =
                analyticsEngineService;

        this.scorePersistenceService =
                scorePersistenceService;
    }

    public void recalculate() {

        analyticsEngineService.generateRankings();

        scorePersistenceService.persistScores();
    }
}