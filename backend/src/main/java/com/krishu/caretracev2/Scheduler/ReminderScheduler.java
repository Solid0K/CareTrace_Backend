package com.krishu.caretracev2.Scheduler;

import com.krishu.caretracev2.CustomExceptions.NotFoundException;
import com.krishu.caretracev2.Model.*;
import com.krishu.caretracev2.NotificationType;
import com.krishu.caretracev2.ReminderType;
import com.krishu.caretracev2.Repository.MedicationRepo;
import com.krishu.caretracev2.Repository.PatientRepo;
import com.krishu.caretracev2.Repository.ReminderRepo;
import com.krishu.caretracev2.Repository.RoutineRepo;
import com.krishu.caretracev2.Service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class ReminderScheduler {

    private final ReminderRepo reminderRepo;
    private final PatientRepo patientRepo;
    private final NotificationService notificationService;
    private final RoutineRepo routineRepo;
    private final MedicationRepo medicationRepo;

    public ReminderScheduler(ReminderRepo reminderRepo, PatientRepo patientRepo, NotificationService notificationService, RoutineRepo routineRepo, MedicationRepo medicationRepo) {
        this.reminderRepo = reminderRepo;
        this.patientRepo = patientRepo;
        this.notificationService = notificationService;
        this.routineRepo = routineRepo;
        this.medicationRepo = medicationRepo;
    }

    @Scheduled(fixedRate = 60000)
    public void checkReminder(){
        LocalDateTime now = LocalDateTime.now();

        LocalDate today = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();

        List<Reminder> reminders = reminderRepo.findAll();
        for (Reminder reminder : reminders) {
            if (isAlreadyTriggered(reminder, today)) {
                continue;
            }
            boolean due = false;

            if (reminder.getType() == ReminderType.ROUTINE) {
                Routine routine=routineRepo.findById(reminder.getRoutineId()).orElseThrow(()->new NotFoundException("Routine not found"));
                if (routine.getDays()!= null && routine.getDays().contains(today.getDayOfWeek()) && !currentTime.isBefore(routine.getTime())) {
                    due = true;
                }
            }
            else {
                if (reminder.getDate() != null && reminder.getDate().equals(today) && !currentTime.isBefore(reminder.getTime())) {
                    due = true;
                }
            }

            if (!due) {
                continue;
            }

            Patient patient = patientRepo.findById(reminder.getPatientId()).orElse(null);
            if (patient == null) {
                continue;
            }

            NotificationType notificationType;
            if (reminder.getType() == ReminderType.MEDICATION) {
                notificationType = NotificationType.MEDICATION_REMINDER;
            } else if (reminder.getType() == ReminderType.ROUTINE) {
                notificationType = NotificationType.ROUTINE_REMINDER;
            } else if (reminder.getType() == ReminderType.APPOINTMENT) {
                notificationType = NotificationType.APPOINTMENT_REMINDER;
            } else {
                notificationType = NotificationType.CUSTOM_REMINDER;
            }

            String message;

            if (notificationType == NotificationType.MEDICATION_REMINDER) {
                message = makeMedicationMessage(reminder);

            } else if (notificationType == NotificationType.ROUTINE_REMINDER) {
                message = makeRoutineMessage(reminder);

            } else {
                message = "You have a " + reminder.getType().name().toLowerCase() + " reminder";
            }
            notificationService.createNotification(patient.getId(), patient.getId(), null, notificationType, message);
            reminder.setLastTriggeredAt(now);
            reminderRepo.save(reminder);
        }
    }

    private boolean isAlreadyTriggered(Reminder reminder, LocalDate today){
        if(reminder.getLastTriggeredAt()==null){
            return false;
        }
        return reminder.getLastTriggeredAt().toLocalDate().equals(today);
    }

    private String makeMedicationMessage(Reminder reminder) {
        Medication medication = medicationRepo.findById(reminder.getMedicationId()).orElseThrow(() -> new NotFoundException("Medication not found"));
        return "Time to take " + medication.getName() + " - " + medication.getDosage();
    }

    private String makeRoutineMessage(Reminder reminder) {
        Routine routine = routineRepo.findById(reminder.getRoutineId()).orElseThrow(() -> new NotFoundException("Routine not found"));
        String message = "Time for " + routine.getTitle();
        if (routine.getDescription() != null && !routine.getDescription().isBlank()) {
            message += ". " + routine.getDescription();
        }
        return message;
    }
}
