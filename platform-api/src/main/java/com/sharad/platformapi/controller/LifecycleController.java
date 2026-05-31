package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.LifecycleActionDto;
import com.sharad.platformapi.service.LifecycleService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lifecycle")
public class LifecycleController {

    private final LifecycleService service;

    public LifecycleController(
            LifecycleService service
    ) {
        this.service = service;
    }

    @GetMapping
    public List<LifecycleActionDto> getActions() {

        return service.generate();
    }
}