package com.sharad.platformapi.service;

import com.sharad.platformapi.entity.EtfUniverse;
import com.sharad.platformapi.repository.EtfUniverseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtfUniverseService {

    private final EtfUniverseRepository repository;

    public EtfUniverseService(EtfUniverseRepository repository) {
        this.repository = repository;
    }

    public List<EtfUniverse> getActiveEtfs() {
        return repository.findByEnabledTrue();
    }

    public List<String> getActiveSymbols() {
        return repository.findByEnabledTrue()
                .stream()
                .map(EtfUniverse::getSymbol)
                .toList();
    }
}