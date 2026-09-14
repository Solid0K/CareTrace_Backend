package com.krishu.caretracev2.Service;

import com.krishu.caretracev2.CustomExceptions.NotFoundException;
import com.krishu.caretracev2.CustomExceptions.UnauthorizedException;
import com.krishu.caretracev2.Model.CareTaker;
import com.krishu.caretracev2.Model.Notification;
import com.krishu.caretracev2.Model.Patient;
import com.krishu.caretracev2.NotificationType;
import com.krishu.caretracev2.Repository.CareTakerRepo;
import com.krishu.caretracev2.Repository.NotificationRepo;
import com.krishu.caretracev2.Repository.PatientRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepo notificationRepo;
    private final CareTakerRepo careTakerRepo;
    private final PatientRepo patientRepo;

    public NotificationService(NotificationRepo notificationRepo, CareTakerRepo careTakerRepo, PatientRepo patientRepo) {
        this.notificationRepo = notificationRepo;
        this.careTakerRepo = careTakerRepo;
        this.patientRepo = patientRepo;
    }

    public Notification createNotification(String recipientId, String patientId, String alertId, NotificationType type, String message) {
        Notification notification = new Notification();

        notification.setRecipientId(recipientId);
        notification.setPatientId(patientId);
        notification.setAlertId(alertId);
        notification.setType(type);
        notification.setMessage(message);
        notification.setRead(false);
        notification.setCreatedAt(LocalDateTime.now());

        return notificationRepo.save(notification);
    }

    public List<Notification> getMyNotification(Authentication authentication){
        CareTaker careTaker=careTakerRepo.findByUserId(authentication.getName()).orElseThrow(()->new NotFoundException("CareTaker not found"));
        return notificationRepo.findByRecipientId(careTaker.getId());
    }

    public List<Notification> getMyPatientNotification(Authentication authentication){
        Patient patient=patientRepo.findByUserId(authentication.getName()).orElseThrow(()->new NotFoundException("Patient not found"));
        return notificationRepo.findByRecipientId(patient.getId());
    }

    public Notification markAsReadForPatient(String notificationId,Authentication authentication){
        Patient patient=patientRepo.findByUserId(authentication.getName()).orElseThrow(()->new NotFoundException("CareTaker not found"));
        Notification notification=notificationRepo.findById(notificationId).orElseThrow(()->new NotFoundException("Notification not found"));
        if(!notification.getRecipientId().equals(patient.getId())){
            throw new UnauthorizedException("You cannot access this notification");
        }
        notification.setRead(true);
        return notificationRepo.save(notification);
    }

    public Notification markAsRead(String notificationId,Authentication authentication){
        CareTaker careTaker=careTakerRepo.findByUserId(authentication.getName()).orElseThrow(()->new NotFoundException("CareTaker not found"));
        Notification notification=notificationRepo.findById(notificationId).orElseThrow(()->new NotFoundException("Notification not found"));
        if(!notification.getRecipientId().equals(careTaker.getId())){
            throw new UnauthorizedException("You cannot access this notification");
        }
        notification.setRead(true);
        return notificationRepo.save(notification);
    }
}
