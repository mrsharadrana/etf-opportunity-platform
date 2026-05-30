package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.MacdResponseDto;
import com.sharad.platformapi.service.indicators.ta4j.IndicatorFacadeService;
import com.sharad.platformapi.service.indicators.ta4j.MacdIndicatorService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/macd")
public class MacdController {

    private final IndicatorFacadeService facade;

    public MacdController(
            IndicatorFacadeService facade
    ) {
        this.facade = facade;
    }

    @GetMapping("/{symbol}")
    public MacdResponseDto getMacd(
            @PathVariable String symbol
    ) {

        MacdIndicatorService.MacdResult result =
                facade.getMacd(symbol);

        return new MacdResponseDto(
                symbol,
                result.macd(),
                result.signal(),
                result.histogram()
        );
    }
}