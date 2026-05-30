package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.RankingDto;

import com.sharad.platformapi.service.ranking.RankingService;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(
            RankingService rankingService
    ) {
        this.rankingService = rankingService;
    }

    @GetMapping("/rankings")
    public List<RankingDto> rankings() {

        return rankingService.getLatestRankings();
    }
}