package com.sharad.platformapi.dto;

import com.sharad.platformapi.service.FearGreedServiceV2;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record FearGreedV2Dto(
        int fearScore,
        String fearState,
        List<FearFactorDto> factors
) {

    public FearGreedV2Dto {
        Objects.requireNonNull(fearState, "fearState must not be null");
        Objects.requireNonNull(factors, "factors must not be null");
    }

    public static FearGreedV2Dto from(
            FearGreedServiceV2.FearGreedV2Dto source
    ) {
        return new FearGreedV2Dto(
                source.fearScore(),
                source.fearState(),
                source.factors().stream()
                        .map(FearFactorDto::from)
                        .collect(Collectors.toList())
        );
    }

    public record FearFactorDto(
            String factorName,
            double rawValue,
            double normalizedScore,
            double weight,
            double contribution
    ) {

        public static FearFactorDto from(
                FearGreedServiceV2.FearFactorDto source
        ) {
            return new FearFactorDto(
                    source.factorName(),
                    source.rawValue(),
                    source.normalizedScore(),
                    source.weight(),
                    source.contribution()
            );
        }
    }
}
