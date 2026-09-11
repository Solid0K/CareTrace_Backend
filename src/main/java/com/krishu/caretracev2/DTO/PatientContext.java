package com.krishu.caretracev2.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
