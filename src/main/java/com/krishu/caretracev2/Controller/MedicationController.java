package com.krishu.caretracev2.Controller;

import com.krishu.caretracev2.DTO.MedicationRequest;
import com.krishu.caretracev2.DTO.MedicationResponse;
import com.krishu.caretracev2.Service.MedicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medication")
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping("/addMedication/{patientId}")
    public ResponseEntity<MedicationResponse> createMedication(MedicationRequest request, @PathVariable String patientId, Authentication authentication){
        return ResponseEntity.ok(medicationService.createMedication(request,patientId,authentication));
    }

    @GetMapping("/getMedications/{patientId}")
    public ResponseEntity<List<MedicationResponse>> getPatientMedications(@PathVariable String patientId){
        return ResponseEntity.ok(medicationService.getPatientMedications(patientId));
    }

    @PutMapping("/updateMedication/{patientId}/{medicatioId}")
    public ResponseEntity<MedicationResponse> updateMedication(@RequestBody MedicationRequest request,@PathVariable String medicationId,
                                                               @PathVariable String patientId,Authentication authentication){
        return ResponseEntity.ok(medicationService.updateMedication(request,medicationId,patientId,authentication));
    }

    @DeleteMapping("/deleteMedication/{medicationId}/{patientId}")
    public void deleteMedication(@PathVariable String medicationId,@PathVariable String patientId,Authentication authentication){
        medicationService.deleteMedication(medicationId,patientId,authentication);
    }
}
