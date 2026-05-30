package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.RsiResponseDto;
import com.sharad.platformapi.service.indicators.ta4j.IndicatorFacadeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rsi")
public class RsiController {

    private final IndicatorFacadeService facade;

    public RsiController(
            IndicatorFacadeService facade
    ) {
        this.facade = facade;
    }

    @GetMapping("/{symbol}")
    public RsiResponseDto getRsi(
            @PathVariable String symbol
    ) {

        return new RsiResponseDto(
                symbol,
                facade.getRsi(symbol)
        );
    }
}