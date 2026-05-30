package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.SignalHistoryDto;
import com.sharad.platformapi.service.SignalHistoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/signals")
public class SignalHistoryController {

    private final SignalHistoryService service;

    public SignalHistoryController(
            SignalHistoryService service
    ) {
        this.service = service;
    }

    @GetMapping
    public List<SignalHistoryDto> getSignals() {

        return service.generateSignals();
    }
}