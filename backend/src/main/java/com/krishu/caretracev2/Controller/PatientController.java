package com.krishu.caretracev2.Controller;

import com.krishu.caretracev2.DTO.PatientMakingRequest;
import com.krishu.caretracev2.DTO.PatientResponse;
import com.krishu.caretracev2.DTO.PatientUpdateRequest;
import com.krishu.caretracev2.Service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/makeAccount")
    public ResponseEntity<PatientResponse> createPatient(@RequestBody PatientMakingRequest request, Authentication authentication){
        return ResponseEntity.ok(patientService.createPatient(request,authentication));
    }

    @GetMapping("/getPatient/{patientId}")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable String patientId,Authentication authentication){
        return ResponseEntity.ok(patientService.getPatient(patientId,authentication));
    }

    @PutMapping("/updatePatient/{patientId}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable String patientId,@RequestBody PatientUpdateRequest request, Authentication authentication){
        return ResponseEntity.ok(patientService.updatePatient(patientId,request,authentication));
    }
}
