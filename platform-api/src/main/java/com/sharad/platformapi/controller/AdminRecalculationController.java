package com.sharad.platformapi.controller;

import com.sharad.platformapi.service.AdminRecalculationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminRecalculationController {

    private final AdminRecalculationService service;

    public AdminRecalculationController(
            AdminRecalculationService service
    ) {
        this.service = service;
    }

    @PostMapping("/recalculate")
    public String recalculate() {

        service.recalculate();

        return "Recalculation completed";
    }
}