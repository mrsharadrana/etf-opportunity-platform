package com.sharad.platformapi.repository;

import com.sharad.platformapi.entity.EtfUniverse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtfUniverseRepository
        extends JpaRepository<EtfUniverse, Long> {

    List<EtfUniverse> findByEnabledTrue();

    List<EtfUniverse> findByRegionAndEnabledTrue(
            String region
    );

    List<EtfUniverse> findByCategoryAndEnabledTrue(
            String category
    );
}