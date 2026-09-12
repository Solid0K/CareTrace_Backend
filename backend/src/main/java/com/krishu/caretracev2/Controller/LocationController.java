package com.krishu.caretracev2.Controller;

import com.krishu.caretracev2.DTO.PatientLocationRequest;
import com.krishu.caretracev2.DTO.PatientLocationResponse;
import com.krishu.caretracev2.Service.PatientLocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient/{patientId}/location")
public class LocationController {

    private final PatientLocationService locationService;

    public LocationController(PatientLocationService locationService) {
        this.locationService = locationService;
    }

    @PutMapping
    public ResponseEntity<PatientLocationResponse> updateLocation(@PathVariable String patientId, @RequestBody PatientLocationRequest request) {
        return ResponseEntity.ok(locationService.updateLocation(patientId, request));
    }
}
