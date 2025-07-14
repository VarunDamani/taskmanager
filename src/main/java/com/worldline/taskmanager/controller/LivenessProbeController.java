package com.worldline.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LivenessProbeController {

    @Operation(summary = "Health check API")
    @RequestMapping(path = "/healthz", method = RequestMethod.GET)
    public ResponseEntity<HttpStatus> livenessProbe() {
        return ResponseEntity.ok().build();
    }
}
