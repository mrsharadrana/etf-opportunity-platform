package com.sharad.platformapi.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "market_regimes")
public class MarketRegime {

    @Id
    @Column(name = "trade_date")
    private LocalDate tradeDate;

    @Column(name = "market_regime")
    private String marketRegime;

    @Column(name = "top_ranked_etf")
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