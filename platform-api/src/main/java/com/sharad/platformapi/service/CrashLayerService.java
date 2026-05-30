package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.CrashLayerDto;
import com.sharad.platformapi.dto.FearGreedDto;
import org.springframework.stereotype.Service;

@Service
public class CrashLayerService {

    private final FearGreedService fearGreedService;

    public CrashLayerService(
            FearGreedService fearGreedService
    ) {
        this.fearGreedService = fearGreedService;
    }

    public CrashLayerDto calculate() {

        FearGreedDto fear =
                fearGreedService.calculate();

        int layer;
        int deployPercent;

        int score = fear.fearScore();

        if (score < 20) {

            layer = 0;
            deployPercent = 0;

        } else if (score < 40) {

            layer = 1;
            deployPercent = 10;

        } else if (score < 55) {

            layer = 2;
            deployPercent = 20;

        } else if (score < 70) {

            layer = 3;
            deployPercent = 20;

        } else if (score < 85) {

            layer = 4;
            deployPercent = 20;

        } else {

            layer = 5;
            deployPercent = 30;
        }

        return new CrashLayerDto(
                fear.fearScore(),
                fear.state(),
                layer,
                deployPercent
        );
    }
}