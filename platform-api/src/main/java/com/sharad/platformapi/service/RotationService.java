package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.LifecycleActionDto;
import com.sharad.platformapi.dto.RotationDto;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RotationService {

    private final LifecycleService lifecycleService;

    public RotationService(
            LifecycleService lifecycleService
    ) {
        this.lifecycleService =
                lifecycleService;
    }

    public List<RotationDto> getRotation() {

        return lifecycleService.generate()
                .stream()
                .map(
                        this::mapRotation
                )
                .toList();
    }

    private RotationDto mapRotation(
            LifecycleActionDto action
    ) {

        String rotationState;

        switch (
                action.signal()
        ) {

            case "STRONG_BUY":
            case "BUY":

                rotationState =
                        "ACCUMULATING";
                break;

            case "HOLD":

                rotationState =
                        "WATCHING";
                break;

            case "REDUCE_20":
            case "REDUCE_40":

                rotationState =
                        "REDUCING";
                break;

            default:

                rotationState =
                        "EXITED";
        }

        return new RotationDto(
                action.symbol(),
                action.signal(),
                rotationState
        );
    }
}