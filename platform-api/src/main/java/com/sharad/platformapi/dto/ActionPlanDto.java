package com.sharad.platformapi.dto;

import java.math.BigDecimal;
import java.util.List;

public record ActionPlanDto(

        int fearScore,

        String fearState,

        String crashLayer,

        int deployPercent,

        BigDecimal deployAmount,

        List<String> buy,

        List<String> reduce,

        List<String> exit

) {
}