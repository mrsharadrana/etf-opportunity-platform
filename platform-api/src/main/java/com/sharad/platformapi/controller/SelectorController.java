package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.SelectorResponseDto;
import com.sharad.platformapi.service.SelectorService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/selector")
public class SelectorController {

    private final SelectorService service;

    public SelectorController(
            SelectorService service
    ) {
        this.service = service;
    }

    @GetMapping
    public SelectorResponseDto getSelector() {

        return service.getSelector();
    }
}