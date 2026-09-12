package com.krishu.caretracev2.Service;

import com.krishu.caretracev2.CustomExceptions.NotFoundException;
import com.krishu.caretracev2.CustomExceptions.NotRelatedException;
import com.krishu.caretracev2.CustomExceptions.UnauthorizedException;
import com.krishu.caretracev2.DTO.SafeLocationResponse;
import com.krishu.caretracev2.Model.CareTaker;
import com.krishu.caretracev2.Model.Patient;
import com.krishu.caretracev2.Model.SafeZone;
import com.krishu.caretracev2.Repository.CareTakerRepo;
import com.krishu.caretracev2.Repository.PatientRepo;
import com.krishu.caretracev2.Repository.SafeZoneRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SafeZoneService {

    private final PatientRepo patientRepo;
    private final SafeZoneRepo safeZoneRepo;
    private final CareTakerRepo careTakerRepo;

    public SafeZoneService(PatientRepo patientRepo, SafeZoneRepo safeZoneRepo, CareTakerRepo careTakerRepo) {
        this.patientRepo = patientRepo;
        this.safeZoneRepo = safeZoneRepo;
        this.careTakerRepo = careTakerRepo;
    }

    public SafeLocationResponse createSafeZone(String patientId, SafeLocationResponse request, Authentication authentication) {
        Patient patient = getAuthorizedPatient(patientId, authentication);

        SafeZone safeZone = new SafeZone();
        safeZone.setPatientId(patient.getId());
        safeZone.setName(request.getName());
        safeZone.setLatitude(request.getLatitude());
        safeZone.setLongitude(request.getLongitude());
        safeZone.setRadius(request.getRadius());

        SafeZone savedSafeZone = safeZoneRepo.save(safeZone);

        return mapToResponse(savedSafeZone);
    }

    public List<SafeLocationResponse> getSafeZones(String patientId) {
        return safeZoneRepo.findByPatientId(patientId).stream().map(this::mapToResponse).toList();
    }

    public SafeLocationResponse updateSafeZone(String patientId, String safeZoneId, SafeLocationResponse request, Authentication authentication) {
        getAuthorizedPatient(patientId, authentication);

        SafeZone safeZone = safeZoneRepo.findById(safeZoneId).orElseThrow(() -> new NotFoundException("Safe zone not found"));
        if (!safeZone.getPatientId().equals(patientId)) {
            throw new NotRelatedException("Safe zone does not belong to this patient");
        }
        safeZone.setName(request.getName());
        safeZone.setLatitude(request.getLatitude());
        safeZone.setLongitude(request.getLongitude());
        safeZone.setRadius(request.getRadius());

        SafeZone savedSafeZone = safeZoneRepo.save(safeZone);
        return mapToResponse(savedSafeZone);
    }

    public void deleteSafeZone(String patientId, String safeZoneId, Authentication authentication) {
        getAuthorizedPatient(patientId, authentication);
        SafeZone safeZone = safeZoneRepo.findById(safeZoneId).orElseThrow(() -> new NotFoundException("Safe zone not found"));
        if (!safeZone.getPatientId().equals(patientId)) {
            throw new NotRelatedException("Safe zone does not belong to this patient");
        }
        safeZoneRepo.delete(safeZone);
    }

    private Patient getAuthorizedPatient(String patientId, Authentication authentication) {
        CareTaker careTaker = careTakerRepo.findByUserId(authentication.getName()).orElseThrow(() -> new NotFoundException("CareTaker not found"));
        Patient patient = patientRepo.findById(patientId).orElseThrow(() -> new NotFoundException("Patient not found"));
        if (!patient.getCareTakerId().equals(careTaker.getId())) {
            throw new UnauthorizedException("You are not authorized for this patient");
        }
        return patient;
    }

    private SafeLocationResponse mapToResponse(SafeZone safeZone) {

        SafeLocationResponse response = new SafeLocationResponse();

        response.setId(safeZone.getId());
        response.setPatientId(safeZone.getPatientId());
        response.setName(safeZone.getName());
        response.setLatitude(safeZone.getLatitude());
        response.setLongitude(safeZone.getLongitude());
        response.setRadius(safeZone.getRadius());

        return response;
    }
}
