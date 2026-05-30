package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.CrashLayerDto;
import com.sharad.platformapi.service.CrashLayerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrashLayerController {

    private final CrashLayerService service;

    public CrashLayerController(
            CrashLayerService service
    ) {
        this.service = service;
    }

    @GetMapping("/api/crash-layer")
    public CrashLayerDto getCrashLayer() {

        return service.calculate();
    }
}