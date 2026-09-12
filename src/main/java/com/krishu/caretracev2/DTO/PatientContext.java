package com.krishu.caretracev2.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientContext {
    private String patientId;
    private String name;
    private int age;
    private String preferredLanguage;
    private String careTakerId;
    private String notes;
    private List<ImportantPersonResponse> importantPersons = new ArrayList<>();
    private List<MedicationResponse> medications = new ArrayList<>();
    private List<RoutineResponse> routines = new ArrayList<>();

    public PatientContext(String patientId, String name, int age, String preferredLanguage, String careTakerId, String notes) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.preferredLanguage = preferredLanguage;
        this.careTakerId = careTakerId;
        this.notes = notes;
    }
}
