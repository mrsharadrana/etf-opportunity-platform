package com.sharad.platformapi.service;

import com.sharad.platformapi.domain.CrashLayer;
import com.sharad.platformapi.dto.CrashLayerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrashLayerService {

    private static final Logger log = LoggerFactory.getLogger(CrashLayerService.class);

    private final FearGreedServiceV2 fearGreedServiceV2;

    public CrashLayerService(FearGreedServiceV2 fearGreedServiceV2) {
        this.fearGreedServiceV2 = fearGreedServiceV2;
    }

    public CrashLayerDto getCurrentCrashLayer() {
        final FearGreedServiceV2.FearGreedV2Dto fearDto;

        try {
            fearDto = fearGreedServiceV2.calculate();
        } catch (Exception e) {
            log.error("FearGreedServiceV2.calculate() failed", e);
            throw new IllegalStateException("FearGreedServiceV2 unavailable", e);
        }

        if (fearDto == null) {
            log.error("FearGreedServiceV2 returned null");
            throw new IllegalStateException("FearGreedServiceV2 returned null");
        }

        int score = fearDto.fearScore();
        String state = fearDto.fearState();

        // Validate score range
        if (score < 0 || score > 100) {
            log.error("Invalid fearScore from FearGreedServiceV2: {}", score);
            throw new IllegalStateException("Invalid fearScore from FearGreedServiceV2: " + score);
        }

        CrashLayer layer = mapScoreToLayer(score);

        int remainingLayers = calculateRemainingLayers(layer);

        log.debug("Mapped fearScore={} fearState={} -> {} ({}%) with {} remaining layers",
                score, state, layer.name(), layer.getDeployPercent(), remainingLayers);

        return new CrashLayerDto(
                score,
                state,
                layer.name(),
                layer.getDeployPercent(),
                layer.getExplanation(),
                remainingLayers
        );
    }

    private CrashLayer mapScoreToLayer(int score) {
        if (score <= 20) {
            return CrashLayer.NO_DEPLOYMENT;
        }

        if (score <= 40) {
            return CrashLayer.LAYER_1;
        }

        if (score <= 55) {
            return CrashLayer.LAYER_2;
        }

        if (score <= 70) {
            return CrashLayer.LAYER_3;
        }

        if (score <= 85) {
            return CrashLayer.LAYER_4;
        }

        return CrashLayer.LAYER_5;
    }

    private int calculateRemainingLayers(CrashLayer layer) {
        return (CrashLayer.values().length - 1) - layer.ordinal();
    }
}