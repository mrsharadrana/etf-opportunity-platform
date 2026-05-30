package com.sharad.platformapi.dto;

public record FearGreedDto(

        int fearScore,

        String state,

        double drawdownPct,

        double distanceFrom200DMA

) {
}