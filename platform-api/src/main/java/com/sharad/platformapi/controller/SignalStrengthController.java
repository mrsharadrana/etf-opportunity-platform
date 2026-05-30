package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.SignalStrengthDto;
import com.sharad.platformapi.service.SignalStrengthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/signal-strength")
public class SignalStrengthController {

    private final SignalStrengthService signalStrengthService;

    public SignalStrengthController(
            SignalStrengthService signalStrengthService
    ) {
        this.signalStrengthService = signalStrengthService;
    }

    @GetMapping
    public List<SignalStrengthDto> signals() {

        return signalStrengthService.generateSignals();
    }
}