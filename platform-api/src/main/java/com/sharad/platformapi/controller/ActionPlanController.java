package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.ActionPlanDto;
import com.sharad.platformapi.dto.ActionPlanRequestDto;
import com.sharad.platformapi.service.ActionPlanService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/action-plan")
public class ActionPlanController {

    private final ActionPlanService service;

    public ActionPlanController(
            ActionPlanService service
    ) {
        this.service = service;
    }

    @PostMapping
    public ActionPlanDto generate(
            @Valid
            @RequestBody
            ActionPlanRequestDto request
    ) {

        return service.generate(
                request.opportunityBuffer()
        );
    }
}