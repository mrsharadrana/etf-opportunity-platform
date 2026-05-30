package com.sharad.platformapi.dto;

import java.util.List;

public record ScreenerResponseDto(
        List<ScreenerRowDto> rows
) {
}