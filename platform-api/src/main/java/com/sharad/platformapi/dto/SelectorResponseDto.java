package com.sharad.platformapi.dto;

import java.util.List;

public record SelectorResponseDto(

        String marketRegime,

        String recommendedETF,

        Integer confidence,

        String rating,

        List<SelectorRankingDto> topRankings

) {
}