package com.krishu.caretracev2.Service;

import com.krishu.caretracev2.AlertStatus;
import com.krishu.caretracev2.AlertType;
import com.krishu.caretracev2.CustomExceptions.NotFoundException;
import com.krishu.caretracev2.DTO.AlertResponse;
import com.krishu.caretracev2.DTO.GeoFenceResponse;
import com.krishu.caretracev2.Model.Alert;
import com.krishu.caretracev2.Model.CareTaker;
import com.krishu.caretracev2.Model.Patient;
import com.krishu.caretracev2.NotificationType;
import com.krishu.caretracev2.Repository.AlertRepo;
import com.krishu.caretracev2.Repository.CareTakerRepo;
import com.krishu.caretracev2.Repository.PatientRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlertService {

    private final AlertRepo alertRepo;
    private final GeoFencingService geofenceService;
    private final CareTakerRepo careTakerRepo;
    private final NotificationService notificationService;
    private final PatientRepo patientRepo;

    public AlertService(AlertRepo alertRepo, GeoFencingService geofenceService, CareTakerRepo careTakerRepo, NotificationService notificationService, PatientRepo patientRepo) {
        this.alertRepo = alertRepo;
        this.geofenceService = geofenceService;
        this.careTakerRepo = careTakerRepo;
        this.notificationService = notificationService;
        this.patientRepo = patientRepo;
    }

    public AlertResponse checkGeoFenceAlert(String patientId){
        GeoFenceResponse response=geofenceService.checkPatientGeoFence(patientId);
        Optional<Alert> activeAlert=alertRepo.findByPatientIdAndTypeAndStatus(patientId, AlertType.GEOFENCE_BREACH, AlertStatus.ACTIVE);
        if(response.getInsideAnySafeLocation()){
            if(activeAlert.isPresent()){
                Alert alert=activeAlert.get();
                alert.setStatus(AlertStatus.RESOLVED);
                alert.setResolvedAt(LocalDateTime.now());
                return mapToAlertResponse(alertRepo.save(alert));
            }
            return null;
        }
        if(activeAlert.isPresent()){
            return null;
        }
        Alert alert=new Alert();
        alert.setPatientId(patientId);
        alert.setMessage("Patient is not any Safe Location");
        alert.setStatus(AlertStatus.ACTIVE);
        alert.setType(AlertType.GEOFENCE_BREACH);
        alert.setCreatedAt(LocalDateTime.now());
        Alert savedAlert=alertRepo.save(alert);
        Patient patient=patientRepo.findByUserId(patientId).orElseThrow(()->new NotFoundException("Patient not found"));
        notificationService.createNotification(patient.getCareTakerId(),patientId,savedAlert.getId(),
                NotificationType.GEOFENCE_BREACH,"Patient is outside of all safe location");
        return mapToAlertResponse(savedAlert);
    }

    public List<AlertResponse> getPatientsAlerts(Authentication authentication) {
        CareTaker careTaker=careTakerRepo.findByUserId(authentication.getName()).orElseThrow(()->new NotFoundException("Caretaker not found"));
        List<String> patientIds=careTaker.getPatientIds();
        List<Alert> alerts=new ArrayList<>();
        for(String patientId:patientIds){
            List<Alert> alert=alertRepo.findByPatientId(patientId);
            alerts.addAll(alert);
        }
        return alerts.stream().map(this::mapToAlertResponse).toList();
    }

    private AlertResponse mapToAlertResponse(Alert alert){
        AlertResponse response=new AlertResponse();
        response.setId(alert.getId());
        response.setPatientId(alert.getPatientId());
        response.setMessage(alert.getMessage());
        response.setType(alert.getType());
        return response;
    }
}
