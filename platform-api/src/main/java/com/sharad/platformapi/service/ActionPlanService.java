package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.ActionPlanDto;
import com.sharad.platformapi.dto.CrashLayerDto;
import com.sharad.platformapi.dto.LifecycleActionDto;
import com.sharad.platformapi.dto.PortfolioAllocationDto;
import com.sharad.platformapi.dto.PortfolioResponseDto;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActionPlanService {

    private final CrashLayerService crashLayerService;

    private final LifecycleService lifecycleService;

    private final PortfolioAllocatorService portfolioAllocatorService;

    public ActionPlanService(
            CrashLayerService crashLayerService,
            LifecycleService lifecycleService,
            PortfolioAllocatorService portfolioAllocatorService
    ) {
        this.crashLayerService =
                crashLayerService;

        this.lifecycleService =
                lifecycleService;

        this.portfolioAllocatorService =
                portfolioAllocatorService;
    }

    public ActionPlanDto generate(
            BigDecimal opportunityBuffer
    ) {

        CrashLayerDto crashLayer =
                crashLayerService
                        .getCurrentCrashLayer();

        BigDecimal deployAmount =
                opportunityBuffer.multiply(
                                BigDecimal.valueOf(
                                        crashLayer.deployPercent()
                                )
                        )
                        .divide(
                                BigDecimal.valueOf(100),
                                2,
                                RoundingMode.HALF_UP
                        );

        PortfolioResponseDto portfolio =
                portfolioAllocatorService.allocate();

        List<String> buy =
                new ArrayList<>();

        List<String> reduce =
                new ArrayList<>();

        List<String> exit =
                new ArrayList<>();

        List<LifecycleActionDto> actions =
                lifecycleService.generate();

        for (
                LifecycleActionDto action :
                        actions
        ) {

            switch (
                    action.action()
            ) {

                case "BUY" -> {

                    PortfolioAllocationDto allocation =
                            portfolio.allocations()
                                    .stream()
                                    .filter(
                                            x ->
                                                    x.symbol()
                                                            .equals(
                                                                    action.symbol()
                                                            )
                                    )
                                    .findFirst()
                                    .orElse(
                                            null
                                    );

                    if (allocation != null) {

                        BigDecimal suggestedAmount =
                                deployAmount.multiply(
                                                BigDecimal.valueOf(
                                                        allocation.allocationPct()
                                                )
                                        )
                                        .divide(
                                                BigDecimal.valueOf(100),
                                                0,
                                                RoundingMode.HALF_UP
                                        );

                        buy.add(
                                action.symbol()
                                        + " ₹"
                                        + suggestedAmount.intValue()
                        );

                    } else {

                        buy.add(
                                action.symbol()
                        );
                    }
                }

                case "REDUCE_20",
                     "REDUCE_40" ->

                        reduce.add(
                                action.symbol()
                                        + " "
                                        + action.action()
                        );

                case "EXIT" ->

                        exit.add(
                                action.symbol()
                        );

                default -> {
                }
            }
        }

        return new ActionPlanDto(
                crashLayer.fearScore(),
                crashLayer.fearState(),
                crashLayer.crashLayer(),
                crashLayer.deployPercent(),
                deployAmount,
                buy,
                reduce,
                exit
        );
    }
}