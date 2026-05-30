package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.EtfUniverseDto;
import com.sharad.platformapi.entity.EtfUniverse;
import com.sharad.platformapi.service.EtfUniverseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/universe")
public class EtfUniverseController {

    private final EtfUniverseService etfUniverseService;

    public EtfUniverseController(
            EtfUniverseService etfUniverseService) {

        this.etfUniverseService = etfUniverseService;
    }

    @GetMapping
    public List<EtfUniverseDto> getUniverse() {

        return etfUniverseService.getActiveEtfs()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private EtfUniverseDto mapToDto(EtfUniverse etf) {

        return new EtfUniverseDto(
                etf.getSymbol(),
                etf.getName(),
                etf.getCategory(),
                etf.getBenchmark()
        );
    }
}