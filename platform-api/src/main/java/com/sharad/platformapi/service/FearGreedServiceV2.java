package com.sharad.platformapi.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FearGreedServiceV2 {

        private final List<FearFactorProvider> factorProviders;

        public FearGreedServiceV2(
                        List<FearFactorProvider> factorProviders
        ) {
                this.factorProviders = Objects.requireNonNull(
                                factorProviders,
                                "factorProviders must not be null"
                );
        }

        public FearGreedV2Dto calculate() {

                // Get raw factor outputs from providers
                List<FearFactorDto> rawFactors =
                                factorProviders.stream()
                                                .map(FearFactorProvider::calculate)
                                                .toList();

                // Sum of weights (used to convert weights into relative proportions)
                double totalWeight = rawFactors.stream()
                                .mapToDouble(FearFactorDto::weight)
                                .sum();

                List<FearFactorDto> factors = new ArrayList<>();
                double compositeScore;

                if (totalWeight <= 0) {
                        // If no weights, default to neutral 50 and zero contributions
                        compositeScore = 50.0;
                        for (FearFactorDto f : rawFactors) {
                                factors.add(new FearFactorDto(
                                                f.factorName(),
                                                f.rawValue(),
                                                f.normalizedScore(),
                                                f.weight(),
                                                0.0
                                ));
                        }
                } else {
                        // Compute contribution per factor as: (normalizedScore * weight) / totalWeight
                        double runningSum = 0.0;
                        for (FearFactorDto f : rawFactors) {
                                double contribution = (f.normalizedScore() * f.weight()) / totalWeight;
                                factors.add(new FearFactorDto(
                                                f.factorName(),
                                                f.rawValue(),
                                                f.normalizedScore(),
                                                f.weight(),
                                                contribution
                                ));
                                runningSum += contribution;
                        }
                        compositeScore = runningSum;
                }

                int roundedScore = (int) Math.round(compositeScore);

                String state = determineState(roundedScore);

                return new FearGreedV2Dto(roundedScore, state, factors);
        }

        private String determineState(int score) {
                if (score >= 81) {
                        return "PANIC";
                }

                if (score >= 61) {
                        return "FEAR";
                }

                if (score >= 41) {
                        return "NEUTRAL";
                }

                if (score >= 21) {
                        return "GREED";
                }

                return "EUPHORIA";
        }

        public static record FearFactorDto(
                        String factorName,
                        double rawValue,
                        double normalizedScore,
                        double weight,
                        double contribution
        ) {

                public FearFactorDto {
                        Objects.requireNonNull(factorName, "factorName must not be null");
                }
        }

        public static record FearGreedV2Dto(
                        int fearScore,
                        String fearState,
                        List<FearFactorDto> factors
        ) {

                public FearGreedV2Dto {
                        Objects.requireNonNull(fearState, "fearState must not be null");
                        Objects.requireNonNull(factors, "factors must not be null");
                }
        }
}
