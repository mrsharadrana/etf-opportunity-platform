package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.ActionPlanDto;
import com.sharad.platformapi.dto.CrashLayerDto;
import com.sharad.platformapi.dto.LifecycleActionDto;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActionPlanService {

    private final CrashLayerService crashLayerService;

    private final LifecycleService lifecycleService;

    public ActionPlanService(
            CrashLayerService crashLayerService,
            LifecycleService lifecycleService
    ) {
        this.crashLayerService =
                crashLayerService;

        this.lifecycleService =
                lifecycleService;
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
                ).divide(
                        BigDecimal.valueOf(100)
                );

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

                case "BUY" ->

                        buy.add(
                                action.symbol()
                        );

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