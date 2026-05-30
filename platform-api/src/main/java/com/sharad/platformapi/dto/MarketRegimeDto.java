package com.sharad.platformapi.dto;

import java.time.LocalDate;

public class MarketRegimeDto {

    private LocalDate tradeDate;

    private String marketRegime;

    private String topRankedEtf;


    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getMarketRegime() {
        return marketRegime;
    }

    public void setMarketRegime(String marketRegime) {
        this.marketRegime = marketRegime;
    }

    public String getTopRankedEtf() {
        return topRankedEtf;
    }

    public void setTopRankedEtf(String topRankedEtf) {
        this.topRankedEtf = topRankedEtf;
    }
}