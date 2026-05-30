package com.sharad.platformapi.controller;

import com.sharad.platformapi.dto.CrashLayerDto;
import com.sharad.platformapi.service.CrashLayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CrashLayerController {

    private final CrashLayerService crashLayerService;

    public CrashLayerController(CrashLayerService crashLayerService) {
        this.crashLayerService = crashLayerService;
    }

    public static record ErrorResponse(String error, String message) {
    }

    @GetMapping("/crash-layer")
    public ResponseEntity<Object> getCrashLayer() {
        try {
            CrashLayerDto dto = crashLayerService.getCurrentCrashLayer();
            return ResponseEntity.ok(dto);
        } catch (IllegalStateException e) {
            ErrorResponse err = new ErrorResponse("SERVICE_UNAVAILABLE", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(err);
        } catch (Exception e) {
            ErrorResponse err = new ErrorResponse("INTERNAL_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
        }
    }
}