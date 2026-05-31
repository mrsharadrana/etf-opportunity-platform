package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.RotationDto;
import com.sharad.platformapi.service.RotationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RotationController {

    private final RotationService service;

    public RotationController(
            RotationService service
    ) {
        this.service = service;
    }

    @GetMapping("/api/rotation")
    public List<RotationDto> rotation() {

        return service.getRotation();
    }
}