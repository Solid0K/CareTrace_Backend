package com.krishu.caretracev2.DTO;

import com.krishu.caretracev2.AlertType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AlertResponse {
    private String id;
    private String patientId;
    private AlertType type;
    private String message;
}
