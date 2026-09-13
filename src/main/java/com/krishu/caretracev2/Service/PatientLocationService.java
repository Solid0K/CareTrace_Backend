package com.krishu.caretracev2.Service;

import com.krishu.caretracev2.CustomExceptions.NotFoundException;
import com.krishu.caretracev2.CustomExceptions.PatientLocationNotKnowException;
import com.krishu.caretracev2.CustomExceptions.UnauthorizedException;
import com.krishu.caretracev2.DTO.PatientLocationRequest;
import com.krishu.caretracev2.DTO.PatientLocationResponse;
import com.krishu.caretracev2.Model.CareTaker;
import com.krishu.caretracev2.Model.Patient;
import com.krishu.caretracev2.Model.PatientLocation;
import com.krishu.caretracev2.Repository.CareTakerRepo;
import com.krishu.caretracev2.Repository.PatientLocationRepo;
import com.krishu.caretracev2.Repository.PatientRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PatientLocationService {

    private final PatientLocationRepo patientLocationRepo;
    private final PatientRepo patientRepo;
    private final AlertService alertService;
    private final CareTakerRepo careTakerRepo;

    public PatientLocationService(PatientLocationRepo patientLocationRepo, PatientRepo patientRepo,
                                  AlertService alertService, CareTakerRepo careTakerRepo) {
        this.patientLocationRepo = patientLocationRepo;
        this.patientRepo = patientRepo;
        this.alertService = alertService;
        this.careTakerRepo = careTakerRepo;
    }

    public PatientLocationResponse updateLocation(Authentication authentication, PatientLocationRequest request){
        Patient patient=patientRepo.findByUserId(authentication.getName()).orElseThrow(()->new NotFoundException("Patient not found"));
        PatientLocation location = patientLocationRepo
                .findByPatientId(patient.getId())
                .orElseGet(PatientLocation::new);

        location.setPatientId(patient.getId());
        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());
        location.setUpdatedAt(LocalDateTime.now());
        PatientLocation savedLocation = patientLocationRepo.save(location);
        alertService.checkGeoFenceAlert(patient.getId());
        return mapToResponse(savedLocation);
    }

    public PatientLocationResponse getMyLocation(Authentication authentication) {
        Patient patient=patientRepo.findByUserId(authentication.getName()).orElseThrow(()->new NotFoundException("Patient not found"));
        PatientLocation currLocation=patientLocationRepo.findByPatientId(patient.getId()).
                orElseThrow(()->new PatientLocationNotKnowException("Patient not Known"));
        return mapToResponse(currLocation);
    }

    public PatientLocationResponse getPatientLocation(String patientId, Authentication authentication) {
        getAuthorizedPatient(patientId,authentication);
        PatientLocation location=patientLocationRepo.findByPatientId(patientId)
                .orElseThrow(()->new PatientLocationNotKnowException("Location not known"));
        return mapToResponse(location);
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

    private Patient getAuthorizedPatient(String patientId, Authentication authentication) {
        CareTaker careTaker = careTakerRepo.findByUserId(authentication.getName()).orElseThrow(() -> new NotFoundException("CareTaker not found"));
        Patient patient = patientRepo.findById(patientId).orElseThrow(() -> new NotFoundException("Patient not found"));
        if (!patient.getCareTakerId().equals(careTaker.getId())) {
            throw new UnauthorizedException("You are not authorized for this patient");
        }
        return patient;
    }
}
