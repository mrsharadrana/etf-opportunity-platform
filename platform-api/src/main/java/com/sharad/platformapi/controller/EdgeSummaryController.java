package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.EdgeSummaryDto;
import com.sharad.platformapi.service.EdgeSummaryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/edge/summary")
public class EdgeSummaryController {

    private final EdgeSummaryService service;

    public EdgeSummaryController(
            EdgeSummaryService service
    ) {
        this.service = service;
    }

    @GetMapping("/{symbol}")
    public EdgeSummaryDto summary(
            @PathVariable String symbol
    ) {

        return service.summarize(
                symbol
        );
    }
}