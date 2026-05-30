package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.CrashLayerDto;
import com.sharad.platformapi.dto.DeploymentRecommendationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeploymentServiceTest {

    private CrashLayerService crashLayerService;
    private DeploymentService deploymentService;

    @BeforeEach
    public void setUp() {
        crashLayerService = mock(CrashLayerService.class);
        deploymentService = new DeploymentService(crashLayerService);
    }

    @Test
    public void deploy10Percent() {
        CrashLayerDto crashLayerDto = new CrashLayerDto(
                30,
                "GREED",
                "LAYER_1",
                10,
                4,
                "Low fear detected. Initial 10% deployment recommended."
        );
        when(crashLayerService.getCurrentCrashLayer()).thenReturn(crashLayerDto);

        DeploymentRecommendationDto result = deploymentService.calculateDeployment(new BigDecimal("300000"));

        assertEquals(new BigDecimal("300000.00"), result.opportunityBuffer());
        assertEquals(new BigDecimal("30000.00"), result.deployAmount());
        assertEquals(new BigDecimal("270000.00"), result.remainingCash());
        assertEquals(10, result.deployPercent());
    }

    @Test
    public void deploy20Percent() {
        CrashLayerDto crashLayerDto = new CrashLayerDto(
                50,
                "NEUTRAL",
                "LAYER_2",
                20,
                3,
                "Moderate fear detected. Maintain 20% deployment recommended."
        );
        when(crashLayerService.getCurrentCrashLayer()).thenReturn(crashLayerDto);

        DeploymentRecommendationDto result = deploymentService.calculateDeployment(new BigDecimal("100000"));

        assertEquals(new BigDecimal("20000.00"), result.deployAmount());
        assertEquals(new BigDecimal("80000.00"), result.remainingCash());
    }

    @Test
    public void deploy30Percent() {
        CrashLayerDto crashLayerDto = new CrashLayerDto(
                90,
                "PANIC",
                "LAYER_5",
                30,
                0,
                "Extreme fear detected. Deploy maximum 30% protective layer."
        );
        when(crashLayerService.getCurrentCrashLayer()).thenReturn(crashLayerDto);

        DeploymentRecommendationDto result = deploymentService.calculateDeployment(new BigDecimal("50000"));

        assertEquals(new BigDecimal("15000.00"), result.deployAmount());
        assertEquals(new BigDecimal("35000.00"), result.remainingCash());
    }

    @Test
    public void invalidBuffer() {
        assertThrows(IllegalArgumentException.class, () ->
                deploymentService.calculateDeployment(null)
        );
    }

    @Test
    public void zeroBuffer() {
        assertThrows(IllegalArgumentException.class, () ->
                deploymentService.calculateDeployment(BigDecimal.ZERO)
        );
    }

    @Test
    public void negativeBuffer() {
        assertThrows(IllegalArgumentException.class, () ->
                deploymentService.calculateDeployment(new BigDecimal("-1"))
        );
    }
}
