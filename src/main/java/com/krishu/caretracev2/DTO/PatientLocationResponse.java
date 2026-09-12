package com.krishu.caretracev2.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PatientLocationResponse {
    private String id;
    private String patientId;
    private double latitude;
    private double longitude;
    private LocalDateTime updatedAt;
}
