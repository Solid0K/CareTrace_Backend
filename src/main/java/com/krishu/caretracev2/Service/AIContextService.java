package com.krishu.caretracev2.Service;

import com.krishu.caretracev2.CustomExceptions.NotFoundException;
import com.krishu.caretracev2.DTO.PatientContext;
import com.krishu.caretracev2.Model.Client;
import com.krishu.caretracev2.Model.Patient;
import com.krishu.caretracev2.Repository.PatientRepo;
import com.krishu.caretracev2.Repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class AIContextService {

    private final PatientRepo patientRepo;
    private final UserRepo userRepo;

    public AIContextService(PatientRepo patientRepo, UserRepo userRepo) {
        this.patientRepo = patientRepo;
        this.userRepo = userRepo;
    }

    /**
     * Gathers patient care information and personal identity into a single context DTO.
     */
    public PatientContext getPatientContext(String patientId) {
        // 1. Fetch patient record from MongoDB
        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() -> new NotFoundException("Patient not found with id: " + patientId));

        // 2. Fetch the patient's name from the Client user account
        String patientName = "Unknown";
        if (patient.getUserId() != null) {
            patientName = userRepo.findById(patient.getUserId())
                    .map(Client::getName)
                    .orElse("Unknown");
        }

        // 3. Assemble the enriched patient context card
        return new PatientContext(
                patient.getId(),
                patientName,
                patient.getAge() != null ? patient.getAge() : 0,
                patient.getPreferred_language(),
                patient.getCareTakerId(),
                patient.getNotes()
        );
    }

    /**
     * Builds a structured, dementia-sensitive prompt containing guidelines and patient context.
     */
    public String buildPrompt(PatientContext context, String userQuestion) {
        return """
                You are CareTrace, a compassionate, patient, and gentle AI companion specialized in assisting dementia patients and their caregivers.
                
                GUIDELINES:
                - Always respond in a calm, comforting, and reassuring tone.
                - Keep sentences simple, short, and easy to understand.
                - Never argue, confuse, or overwhelm the patient.
                - Address the patient warmly by their name.
                - Respond in their preferred language: %s.
                
                PATIENT INFORMATION:
                - Name: %s
                - Age: %d
                - Caretaker ID: %s
                - Personal / Caregiver Notes: %s
                
                PATIENT QUESTION:
                "%s"
                
                Based strictly on the patient's information and your supportive role, provide a gentle and helpful response:
                """.formatted(
                        context.getPreferredLanguage() != null ? context.getPreferredLanguage() : "English",
                        context.getName(),
                        context.getAge(),
                        context.getCareTakerId() != null ? context.getCareTakerId() : "Not assigned",
                        context.getNotes() != null && !context.getNotes().isBlank() ? context.getNotes() : "None",
                        userQuestion
                );
    }
}

