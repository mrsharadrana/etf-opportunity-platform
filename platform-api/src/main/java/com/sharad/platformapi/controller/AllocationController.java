package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.AllocationRecommendationDto;
import com.sharad.platformapi.dto.AllocationRequestDto;
import com.sharad.platformapi.service.AllocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AllocationController {

    private final AllocationService allocationService;

    public AllocationController(
            AllocationService allocationService
    ) {
        this.allocationService = allocationService;
    }

    @PostMapping("/allocation")
    public ResponseEntity<?> allocate(
            @Valid
            @RequestBody
            AllocationRequestDto request
    ) {

        try {

            AllocationRecommendationDto response =
                    allocationService.allocate(
                            request.opportunityBuffer()
                    );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            new ErrorResponse(
                                    "INVALID_INPUT",
                                    e.getMessage()
                            )
                    );

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(
                            new ErrorResponse(
                                    "SERVICE_UNAVAILABLE",
                                    e.getMessage()
                            )
                    );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            new ErrorResponse(
                                    "INTERNAL_ERROR",
                                    "Unexpected error occurred"
                            )
                    );
        }
    }

    public record ErrorResponse(
            String error,
            String message
    ) {
    }
}