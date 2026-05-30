package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.ScoreHistoryDto;
import com.sharad.platformapi.service.ScoreHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score/history")
public class ScoreHistoryController {

    private final ScoreHistoryService service;

    public ScoreHistoryController(
            ScoreHistoryService service
    ) {
        this.service = service;
    }

    @GetMapping("/{symbol}")
public List<ScoreHistoryDto> getHistory(
        @PathVariable String symbol,

        @RequestParam(
                defaultValue = "30"
        )
        int days
) {

    return service.getHistory(
            symbol,
            days
    );
}
}