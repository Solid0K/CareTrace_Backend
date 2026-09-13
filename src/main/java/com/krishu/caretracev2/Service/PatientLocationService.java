package com.krishu.caretracev2.Service;

import com.krishu.caretracev2.CustomExceptions.NotFoundException;
import com.krishu.caretracev2.DTO.PatientLocationRequest;
import com.krishu.caretracev2.DTO.PatientLocationResponse;
import com.krishu.caretracev2.Model.PatientLocation;
import com.krishu.caretracev2.Repository.PatientLocationRepo;
import com.krishu.caretracev2.Repository.PatientRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PatientLocationService {

    private final PatientLocationRepo patientLocationRepo;
    private final PatientRepo patientRepo;
    private final AlertService alertService;

    public PatientLocationService(PatientLocationRepo patientLocationRepo, PatientRepo patientRepo, AlertService alertService) {
        this.patientLocationRepo = patientLocationRepo;
        this.patientRepo = patientRepo;
        this.alertService = alertService;
    }

    public PatientLocationResponse updateLocation(String patientId, PatientLocationRequest request){
        patientRepo.findById(patientId).orElseThrow(() -> new NotFoundException("Patient not found"));
        PatientLocation location = patientLocationRepo
                .findByPatientId(patientId)
                .orElseGet(PatientLocation::new);

        location.setPatientId(patientId);
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());
        location.setUpdatedAt(LocalDateTime.now());
        PatientLocation savedLocation = patientLocationRepo.save(location);
        alertService.checkGeoFenceAlert(patientId);
        return mapToResponse(savedLocation);
    }

    private PatientLocationResponse mapToResponse(PatientLocation savedLocation) {
        PatientLocationResponse response = new PatientLocationResponse();
        response.setId(savedLocation.getId());
        response.setPatientId(savedLocation.getPatientId());
        response.setLatitude(savedLocation.getLatitude());
        response.setLongitude(savedLocation.getLongitude());
        response.setUpdatedAt(savedLocation.getUpdatedAt());
        return response;
    }
}
