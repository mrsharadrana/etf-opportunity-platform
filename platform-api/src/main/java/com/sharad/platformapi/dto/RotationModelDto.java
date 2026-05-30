package com.sharad.platformapi.dto;

import java.util.List;

public record RotationModelDto(

        String marketRegime,

        String recommendedETF,

        Integer confidence,

        String rating,

        List<String> reasoning

) {
}