package com.sharad.platformapi.service;

import com.sharad.platformapi.dto.AllocationRecommendationDto;
import com.sharad.platformapi.dto.DeploymentRecommendationDto;
import com.sharad.platformapi.dto.EtfAllocationDto;
import com.sharad.platformapi.dto.RankingDto;
import com.sharad.platformapi.service.ranking.RankingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllocationService {

    private final DeploymentService deploymentService;

    private final RankingService rankingService;

    public AllocationService(
            DeploymentService deploymentService,
            RankingService rankingService
    ) {
        this.deploymentService = deploymentService;
        this.rankingService = rankingService;
    }

    public AllocationRecommendationDto allocate(
            BigDecimal opportunityBuffer
    ) {

        DeploymentRecommendationDto deployment =
                deploymentService.calculateDeployment(
                        opportunityBuffer
                );

        List<RankingDto> topBuyEtfs =
                rankingService.getLatestRankings()
                        .stream()
                        .filter(r ->
                                "BUY".equalsIgnoreCase(r.getSignal())
                                        && r.getMomentumScore() != null
                                        && r.getMomentumScore().compareTo(BigDecimal.ZERO) > 0
                        )
                        .sorted(
                                Comparator.comparing(
                                                RankingDto::getMomentumScore
                                        )
                                        .reversed()
                        )
                        .limit(3)
                        .toList();

        if (topBuyEtfs.isEmpty()) {

            return new AllocationRecommendationDto(
                    deployment.fearScore(),
                    deployment.fearState(),
                    deployment.crashLayer(),
                    deployment.deployPercent(),
                    deployment.deployAmount(),
                    List.of(
                            new EtfAllocationDto(
                                    "LIQUIDBEES.NS",
                                    BigDecimal.ZERO,
                                    BigDecimal.valueOf(100),
                                    deployment.deployAmount()
                            )
                    )
            );
        }

        BigDecimal totalScore =
                topBuyEtfs.stream()
                        .map(RankingDto::getMomentumScore)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        List<EtfAllocationDto> allocations =
                topBuyEtfs.stream()
                        .map(etf -> {

                            BigDecimal weightPercent =
                                    etf.getMomentumScore()
                                            .divide(
                                                    totalScore,
                                                    6,
                                                    RoundingMode.HALF_UP
                                            )
                                            .multiply(
                                                    BigDecimal.valueOf(100)
                                            );

                            BigDecimal allocationAmount =
                                    deployment.deployAmount()
                                            .multiply(weightPercent)
                                            .divide(
                                                    BigDecimal.valueOf(100),
                                                    2,
                                                    RoundingMode.HALF_UP
                                            );

                            return new EtfAllocationDto(
                                    etf.getSymbol(),
                                    etf.getMomentumScore(),
                                    weightPercent.setScale(
                                            2,
                                            RoundingMode.HALF_UP
                                    ),
                                    allocationAmount
                            );
                        })
                        .collect(
                                Collectors.toCollection(
                                        ArrayList::new
                                )
                        );

        BigDecimal allocatedTotal =
                allocations.stream()
                        .map(
                                EtfAllocationDto::allocationAmount
                        )
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        BigDecimal difference =
                deployment.deployAmount()
                        .subtract(allocatedTotal);

        if (
                difference.compareTo(BigDecimal.ZERO) != 0
                        && !allocations.isEmpty()
        ) {

            EtfAllocationDto first =
                    allocations.get(0);

            EtfAllocationDto corrected =
                    new EtfAllocationDto(
                            first.symbol(),
                            first.momentumScore(),
                            first.weightPercent(),
                            first.allocationAmount()
                                    .add(difference)
                    );

            allocations.set(
                    0,
                    corrected
            );
        }

        return new AllocationRecommendationDto(
                deployment.fearScore(),
                deployment.fearState(),
                deployment.crashLayer(),
                deployment.deployPercent(),
                deployment.deployAmount(),
                allocations
        );
    }
}