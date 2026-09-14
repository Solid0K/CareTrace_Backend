package com.krishu.caretracev2.Controller;

import com.krishu.caretracev2.DTO.PatientLocationRequest;
import com.krishu.caretracev2.DTO.PatientLocationResponse;
import com.krishu.caretracev2.Service.PatientLocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class LocationController {

    private final PatientLocationService locationService;

    public LocationController(PatientLocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/me/location")
    public ResponseEntity<PatientLocationResponse> getMyLocation(Authentication authentication){
        return ResponseEntity.ok(locationService.getMyLocation(authentication));
    }

    @PutMapping("/me/updateLocation")
    public void updatePatientLocation(Authentication authentication,@RequestBody PatientLocationRequest request){
        locationService.updateLocation(authentication,request);
    }

    @GetMapping("/location/{patientId}")
    public ResponseEntity<PatientLocationResponse> getPatientLocation(@PathVariable String patientId,Authentication authentication){
        return ResponseEntity.ok(locationService.getPatientLocation(patientId,authentication));
    }
}
