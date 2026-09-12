package com.krishu.caretracev2.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SafeLocationResponse {
    private String id;
    private String patientId;
    private String name;
    private Double longitude;
    private Double latitude;
    private Double radius;
}
