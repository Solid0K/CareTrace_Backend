package com.krishu.caretracev2.Service;

import com.krishu.caretracev2.CustomExceptions.NotFoundException;
import com.krishu.caretracev2.DTO.ImportantPersonResponse;
import com.krishu.caretracev2.DTO.MedicationResponse;
import com.krishu.caretracev2.DTO.PatientContext;
import com.krishu.caretracev2.DTO.RoutineResponse;
import com.krishu.caretracev2.Model.Client;
import com.krishu.caretracev2.Model.ImportantPerson;
import com.krishu.caretracev2.Model.Medication;
import com.krishu.caretracev2.Model.Patient;
import com.krishu.caretracev2.Model.Routine;
import com.krishu.caretracev2.Repository.ImportantPersonRepo;
import com.krishu.caretracev2.Repository.MedicationRepo;
import com.krishu.caretracev2.Repository.PatientRepo;
import com.krishu.caretracev2.Repository.RoutineRepo;
import com.krishu.caretracev2.Repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AIContextService {

    private final PatientRepo patientRepo;
    private final UserRepo userRepo;
    private final ImportantPersonRepo importantPersonRepo;
    private final MedicationRepo medicationRepo;
    private final RoutineRepo routineRepo;

    public AIContextService(PatientRepo patientRepo,
                            UserRepo userRepo,
                            ImportantPersonRepo importantPersonRepo,
                            MedicationRepo medicationRepo,
                            RoutineRepo routineRepo) {
        this.patientRepo = patientRepo;
        this.userRepo = userRepo;
        this.importantPersonRepo = importantPersonRepo;
        this.medicationRepo = medicationRepo;
        this.routineRepo = routineRepo;
    }

    /**
     * Gathers complete patient care information, identity, family contacts,
     * prescriptions, and daily routines into a single context DTO.
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

        // 3. Fetch Important Persons (Family / Emergency contacts)
        List<ImportantPersonResponse> importantPersons = importantPersonRepo.findByPatientId(patientId)
                .stream()
                .map(this::mapToImportantPersonResponse)
                .toList();

        // 4. Fetch Prescribed Medications
        List<MedicationResponse> medications = medicationRepo.findByPatientId(patientId)
                .stream()
                .map(this::mapToMedicationResponse)
                .toList();

        // 5. Fetch Daily Routines
        List<RoutineResponse> routines = routineRepo.findByPatientId(patientId)
                .stream()
                .map(this::mapToRoutineResponse)
                .toList();

        // 6. Assemble the enriched patient context card
        PatientContext context = new PatientContext(
                patient.getId(),
                patientName,
                patient.getAge() != null ? patient.getAge() : 0,
                patient.getPreferred_language(),
                patient.getCareTakerId(),
                patient.getNotes()
        );
        context.setImportantPersons(importantPersons);
        context.setMedications(medications);
        context.setRoutines(routines);

        return context;
    }

    /**
     * Builds a structured, dementia-sensitive prompt containing guidelines and patient context.
     */
    public String buildPrompt(PatientContext context, String userQuestion) {
        String contactsFormatted = formatImportantPersons(context.getImportantPersons());
        String medicationsFormatted = formatMedications(context.getMedications());
        String routinesFormatted = formatRoutines(context.getRoutines());

        return """
                You are CareTrace, a compassionate, patient, and gentle AI companion specialized in assisting dementia patients and their caregivers.

                GUIDELINES:
                - Always respond in a calm, comforting, and reassuring tone.
                - Keep sentences simple, short, and easy to understand.
                - Never argue, confuse, confront, or overwhelm the patient. Use gentle validation and positive redirection.
                - Address the patient warmly by their name.
                - Respond in their preferred language: %s.

                PATIENT INFORMATION:
                - Name: %s
                - Age: %d
                - Caretaker ID: %s
                - Caregiver Notes / Medical Context: %s

                FAMILY & IMPORTANT CONTACTS:
                %s

                PRESCRIBED MEDICATIONS:
                %s

                DAILY ROUTINES:
                %s

                PATIENT QUESTION:
                "%s"

                Based strictly on the patient's information and your supportive role, provide a gentle and helpful response:
                """.formatted(
                context.getPreferredLanguage() != null ? context.getPreferredLanguage() : "English",
                context.getName(),
                context.getAge(),
                context.getCareTakerId() != null ? context.getCareTakerId() : "Not assigned",
                context.getNotes() != null && !context.getNotes().isBlank() ? context.getNotes() : "None",
                contactsFormatted,
                medicationsFormatted,
                routinesFormatted,
                userQuestion
        );
    }

    private String formatImportantPersons(List<ImportantPersonResponse> persons) {
        if (persons == null || persons.isEmpty()) {
            return "- None registered yet.";
        }
        return persons.stream()
                .map(p -> "- " + p.getName() + " (" + (p.getRelation() != null ? p.getRelation() : "Contact") +
                        (p.getPhoneNo() != null ? ", Phone: " + p.getPhoneNo() : "") + ")")
                .collect(Collectors.joining("\n"));
    }

    private String formatMedications(List<MedicationResponse> medications) {
        if (medications == null || medications.isEmpty()) {
            return "- No active medications on file.";
        }
        return medications.stream()
                .map(m -> "- " + m.getName() + ": Dosage: " + (m.getDosage() != null ? m.getDosage() : "As directed") +
                        ", Frequency: " + (m.getFrequency() != null ? m.getFrequency() : "N/A") +
                        (m.getInstructions() != null && !m.getInstructions().isBlank() ? " (Instructions: " + m.getInstructions() + ")" : ""))
                .collect(Collectors.joining("\n"));
    }

    private String formatRoutines(List<RoutineResponse> routines) {
        if (routines == null || routines.isEmpty()) {
            return "- No daily routines scheduled.";
        }
        return routines.stream()
                .map(r -> "- " + r.getTitle() + " at " + (r.getTime() != null ? r.getTime() : "unspecified time") +
                        (r.getDays() != null && !r.getDays().isEmpty() ? " on " + r.getDays() : "") +
                        (r.getDescription() != null && !r.getDescription().isBlank() ? " (" + r.getDescription() + ")" : ""))
                .collect(Collectors.joining("\n"));
    }

    private ImportantPersonResponse mapToImportantPersonResponse(ImportantPerson person) {
        ImportantPersonResponse response = new ImportantPersonResponse();
        response.setId(person.getId());
        response.setName(person.getName());
        response.setRelation(person.getRelation());
        response.setPhoneNo(person.getPhoneNo());
        return response;
    }

    private MedicationResponse mapToMedicationResponse(Medication medication) {
        MedicationResponse response = new MedicationResponse();
        response.setId(medication.getId());
        response.setName(medication.getName());
        response.setDosage(medication.getDosage());
        response.setFrequency(medication.getFrequency());
        response.setStartDate(medication.getStartDate());
        response.setEndDate(medication.getEndDate());
        response.setInstructions(medication.getInstructions());
        return response;
    }

    private RoutineResponse mapToRoutineResponse(Routine routine) {
        RoutineResponse response = new RoutineResponse();
        response.setId(routine.getId());
        response.setTitle(routine.getTitle());
        response.setDescription(routine.getDescription());
        response.setTime(routine.getTime());
        response.setDays(routine.getDays());
        return response;
    }
}

