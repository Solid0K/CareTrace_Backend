package com.krishu.caretracev2.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection="safezone")
public class SafeZone {
    @Id
    private String id;
    private String patientId;
    private String name;
    private Double latitude;
    private Double longitude;
    private Double radius;
}
