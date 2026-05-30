package com.sharad.platformapi.dto;

import java.util.List;

public record OpportunityResponseDto(

        String indiaMarketState,

        String usaMarketState,

        String chinaMarketState,

        String goldMarketState,

        String silverMarketState,

        List<String> topOpportunities

) {
}