package com.sharad.platformapi.dto;

public class RankingResponse {

    private String symbol;

    private String trade_date;

    private Double close_price;

    private Double momentum_score;

    private Integer rank;

    private String signal;


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTrade_date() {
        return trade_date;
    }

    public void setTrade_date(String trade_date) {
        this.trade_date = trade_date;
    }

    public Double getClose_price() {
        return close_price;
    }

    public void setClose_price(Double close_price) {
        this.close_price = close_price;
    }

    public Double getMomentum_score() {
        return momentum_score;
    }

    public void setMomentum_score(Double momentum_score) {
        this.momentum_score = momentum_score;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }
}