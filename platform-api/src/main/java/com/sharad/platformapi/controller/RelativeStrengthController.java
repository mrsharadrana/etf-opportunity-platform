package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.RelativeStrengthDto;
import com.sharad.platformapi.service.RelativeStrengthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RelativeStrengthController {

    private final RelativeStrengthService service;

    public RelativeStrengthController(
            RelativeStrengthService service
    ) {
        this.service = service;
    }

    @GetMapping("/api/relative-strength")
    public List<RelativeStrengthDto> getRelativeStrength() {

        return service.calculate();
    }
}