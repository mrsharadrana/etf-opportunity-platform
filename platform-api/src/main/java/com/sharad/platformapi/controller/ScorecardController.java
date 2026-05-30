package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.EtfScorecardDto;
import com.sharad.platformapi.service.ScorecardService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scorecard")
public class ScorecardController {

    private final ScorecardService scorecardService;

    public ScorecardController(
            ScorecardService scorecardService
    ) {
        this.scorecardService = scorecardService;
    }

    @GetMapping("/{symbol}")
    public EtfScorecardDto getScorecard(
            @PathVariable String symbol
    ) {

        return scorecardService.getScorecard(
                symbol
        );
    }
}