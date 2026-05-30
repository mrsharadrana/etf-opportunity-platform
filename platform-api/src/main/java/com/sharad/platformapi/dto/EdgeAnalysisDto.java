package com.sharad.platformapi.dto;

public record EdgeAnalysisDto(

        String condition,

        Integer sampleSize,

        Double winRatePct,

        Double avgReturnPct,

        Double bestReturnPct,

        Double worstReturnPct

) {
}