package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.ScreenerResponseDto;
import com.sharad.platformapi.service.ScreenerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScreenerController {

    private final ScreenerService service;

    public ScreenerController(
            ScreenerService service
    ) {
        this.service = service;
    }

    @GetMapping("/api/screener")
    public ScreenerResponseDto getScreener() {

        return service.getScreener();
    }
}