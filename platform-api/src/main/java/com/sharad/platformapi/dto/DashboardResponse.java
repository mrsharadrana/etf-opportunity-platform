package com.sharad.platformapi.dto;

public class DashboardResponse {

    private MarketRegimeResponse marketRegime;

    private RankingResponse[] rankings;


    public MarketRegimeResponse getMarketRegime() {
        return marketRegime;
    }

    public void setMarketRegime(
            MarketRegimeResponse marketRegime
    ) {
        this.marketRegime = marketRegime;
    }

    public RankingResponse[] getRankings() {
        return rankings;
    }

    public void setRankings(
            RankingResponse[] rankings
    ) {
        this.rankings = rankings;
    }
}