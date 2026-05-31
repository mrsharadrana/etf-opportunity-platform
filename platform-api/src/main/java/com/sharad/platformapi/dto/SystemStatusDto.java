package com.sharad.platformapi.dto;

import java.time.LocalDateTime;

public record SystemStatusDto(

        LocalDateTime generatedAt,

        String marketStatus

) {
}