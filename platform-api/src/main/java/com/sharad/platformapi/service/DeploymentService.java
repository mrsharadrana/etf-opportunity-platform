package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.CrashLayerDto;
import com.sharad.platformapi.dto.DeploymentRecommendationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DeploymentService {

    private static final Logger log =
            LoggerFactory.getLogger(DeploymentService.class);

    private static final BigDecimal ONE_HUNDRED =
            BigDecimal.valueOf(100);

    private final CrashLayerService crashLayerService;

    public DeploymentService(CrashLayerService crashLayerService) {
        this.crashLayerService = crashLayerService;
    }

    public DeploymentRecommendationDto calculateDeployment(
            BigDecimal opportunityBuffer
    ) {

        if (opportunityBuffer == null ||
                opportunityBuffer.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "opportunityBuffer must be greater than zero"
            );
        }

        CrashLayerDto crashLayer =
                crashLayerService.getCurrentCrashLayer();

        BigDecimal deployAmount =
                opportunityBuffer
                        .multiply(
                                BigDecimal.valueOf(
                                        crashLayer.deployPercent()
                                )
                        )
                        .divide(
                                ONE_HUNDRED,
                                2,
                                RoundingMode.HALF_UP
                        );

        BigDecimal remainingCash =
                opportunityBuffer
                        .subtract(deployAmount)
                        .setScale(
                                2,
                                RoundingMode.HALF_UP
                        );

        log.info(
                "Buffer={} Layer={} DeployPercent={} DeployAmount={}",
                opportunityBuffer,
                crashLayer.crashLayer(),
                crashLayer.deployPercent(),
                deployAmount
        );

        return new DeploymentRecommendationDto(
                crashLayer.fearScore(),
                crashLayer.fearState(),

                crashLayer.crashLayer(),
                crashLayer.deployPercent(),
                crashLayer.remainingLayers(),

                opportunityBuffer.setScale(
                        2,
                        RoundingMode.HALF_UP
                ),

                deployAmount,

                remainingCash,

                crashLayer.explanation()
        );
    }
}