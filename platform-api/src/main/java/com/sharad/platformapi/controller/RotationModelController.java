package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.RotationModelDto;
import com.sharad.platformapi.service.RotationModelService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rotation-model")
public class RotationModelController {

    private final RotationModelService service;

    public RotationModelController(
            RotationModelService service
    ) {
        this.service = service;
    }

    @GetMapping
    public RotationModelDto getRotationModel() {

        return service.getRotationModel();
    }
}