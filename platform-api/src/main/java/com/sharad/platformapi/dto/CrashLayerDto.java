package com.sharad.platformapi.dto;

public record CrashLayerDto(
        int fearScore,
        String fearState,
        String crashLayer,
        int deployPercent,
        String explanation,
        int remainingLayers
) {
}