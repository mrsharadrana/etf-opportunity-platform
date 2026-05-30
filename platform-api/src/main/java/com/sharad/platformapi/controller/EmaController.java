package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.EmaResponseDto;
import com.sharad.platformapi.service.indicators.ta4j.IndicatorFacadeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ema")
public class EmaController {

    private final IndicatorFacadeService facade;

    public EmaController(
            IndicatorFacadeService facade
    ) {
        this.facade = facade;
    }

    @GetMapping("/{symbol}")
    public EmaResponseDto getEma(
            @PathVariable String symbol
    ) {

        return new EmaResponseDto(
                symbol,
                facade.getEma50(symbol)
        );
    }
}