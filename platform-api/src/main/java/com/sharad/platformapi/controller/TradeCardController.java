package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.TradeCardDto;
import com.sharad.platformapi.service.TradeCardService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trade-card")
public class TradeCardController {

    private final TradeCardService service;

    public TradeCardController(
            TradeCardService service
    ) {
        this.service = service;
    }

    @GetMapping("/{symbol}")
    public TradeCardDto getTradeCard(
            @PathVariable
            String symbol
    ) {
        return service.generate(
                symbol
        );
    }
}