package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.DeploymentRecommendationDto;
import com.sharad.platformapi.dto.DeploymentRequestDto;
import com.sharad.platformapi.service.DeploymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DeploymentController {

    private final DeploymentService deploymentService;

    public DeploymentController(
            DeploymentService deploymentService
    ) {
        this.deploymentService = deploymentService;
    }

    @PostMapping("/deployment")
    public ResponseEntity<?> calculateDeployment(
            @Valid @RequestBody
            DeploymentRequestDto request
    ) {

        try {

            DeploymentRecommendationDto response =
                    deploymentService.calculateDeployment(
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
                                    "Crash layer information unavailable"
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