package com.sharad.platformapi.dto;

public record CrashLayerDto(

        int fearScore,

        String marketState,

        int crashLayer,

        int deployPercent

) {
}