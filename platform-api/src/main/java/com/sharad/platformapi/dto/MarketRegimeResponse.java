package com.sharad.platformapi.dto;

public class MarketRegimeResponse {

    private String trade_date;

    private String market_regime;

    private String top_ranked_etf;


    public String getTrade_date() {
        return trade_date;
    }

    public void setTrade_date(String trade_date) {
        this.trade_date = trade_date;
    }

    public String getMarket_regime() {
        return market_regime;
    }

    public void setMarket_regime(String market_regime) {
        this.market_regime = market_regime;
    }

    public String getTop_ranked_etf() {
        return top_ranked_etf;
    }

    public void setTop_ranked_etf(String top_ranked_etf) {
        this.top_ranked_etf = top_ranked_etf;
    }
}