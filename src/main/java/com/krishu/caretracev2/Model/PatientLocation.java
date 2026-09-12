package com.krishu.caretracev2.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@Document(collection="patientLocation")
public class PatientLocation {
    @Id
    private String id;
    private String patientId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime updatedAt;
}
