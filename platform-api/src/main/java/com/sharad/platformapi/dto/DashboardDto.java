package com.sharad.platformapi.dto;

import java.util.List;

public class DashboardDto {

    private MarketRegimeDto marketRegime;

    private List<RankingDto> rankings;


    public MarketRegimeDto getMarketRegime() {
        return marketRegime;
    }

    public void setMarketRegime(
            MarketRegimeDto marketRegime
    ) {
        this.marketRegime = marketRegime;
    }

    public List<RankingDto> getRankings() {
        return rankings;
    }

    public void setRankings(
            List<RankingDto> rankings
    ) {
        this.rankings = rankings;
    }
}