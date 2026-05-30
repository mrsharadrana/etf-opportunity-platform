package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.DashboardResponseDto;
import com.sharad.platformapi.service.DashboardService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(
            DashboardService service
    ) {
        this.service = service;
    }

    @GetMapping
    public DashboardResponseDto dashboard() {

        return service.getDashboard();
    }
}