package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.CreateHoldingRequest;
import com.sharad.platformapi.dto.HoldingResponseDto;
import com.sharad.platformapi.service.PortfolioHoldingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holdings")
public class PortfolioHoldingController {

    private final PortfolioHoldingService service;

    public PortfolioHoldingController(
            PortfolioHoldingService service) {
        this.service = service;
    }

    @PostMapping
    public HoldingResponseDto create(
            @Valid @RequestBody CreateHoldingRequest request) {

        return service.create(
                request);
    }

    @GetMapping
    public List<HoldingResponseDto> getAll() {

        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        service.delete(
                id);
    }
}