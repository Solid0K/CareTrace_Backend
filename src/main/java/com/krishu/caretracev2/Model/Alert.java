package com.krishu.caretracev2.Model;

import com.krishu.caretracev2.AlertStatus;
import com.krishu.caretracev2.AlertType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection="alert")
public class Alert {
    @Id
    private String id;
    private String patientId;
    private AlertType type;
    private String message;
    private AlertStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;
}
