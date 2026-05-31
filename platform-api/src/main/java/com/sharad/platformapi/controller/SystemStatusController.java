package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.SystemStatusDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/system-status")
public class SystemStatusController {

    @GetMapping
    public SystemStatusDto status() {

        LocalTime now =
                LocalTime.now();

        boolean marketOpen =
                LocalDate.now().getDayOfWeek() != DayOfWeek.SATURDAY
                &&
                LocalDate.now().getDayOfWeek() != DayOfWeek.SUNDAY
                &&
                now.isAfter(
                        LocalTime.of(
                                9,
                                15
                        )
                )
                &&
                now.isBefore(
                        LocalTime.of(
                                15,
                                30
                        )
                );

        return new SystemStatusDto(
                LocalDateTime.now(),
                marketOpen
                        ? "OPEN"
                        : "CLOSED"
        );
    }
}