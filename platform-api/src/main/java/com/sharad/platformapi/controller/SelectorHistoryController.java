package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.SelectorHistoryDto;
import com.sharad.platformapi.service.SelectorHistoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/selector/history")
public class SelectorHistoryController {

    private final SelectorHistoryService service;

    public SelectorHistoryController(
            SelectorHistoryService service
    ) {
        this.service = service;
    }

    @GetMapping
    public List<SelectorHistoryDto> history() {

        return service.getHistory();
    }
}