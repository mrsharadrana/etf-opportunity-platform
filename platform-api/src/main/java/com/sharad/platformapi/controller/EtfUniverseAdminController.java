package com.sharad.platformapi.controller;

import com.sharad.platformapi.entity.EtfUniverse;
import com.sharad.platformapi.repository.EtfUniverseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/universe")
public class EtfUniverseAdminController {

    private final EtfUniverseRepository repository;

    public EtfUniverseAdminController(
            EtfUniverseRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<EtfUniverse> getAll() {
        return repository.findAll();
    }

    @PutMapping("/{id}/enable")
    public EtfUniverse enable(@PathVariable Long id) {

        EtfUniverse etf = repository.findById(id)
                .orElseThrow();

        etf.setEnabled(true);

        return repository.save(etf);
    }

    @PutMapping("/{id}/disable")
    public EtfUniverse disable(@PathVariable Long id) {

        EtfUniverse etf = repository.findById(id)
                .orElseThrow();

        etf.setEnabled(false);

        return repository.save(etf);
    }
}