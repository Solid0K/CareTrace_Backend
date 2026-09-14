package com.krishu.caretracev2.Model;

import com.krishu.caretracev2.ClientRole;
import com.krishu.caretracev2.NotificationType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@Document(collection="notification")
public class Notification {
    @Id
    private String id;
    private String recipientId;
    private ClientRole recipientRole;
    private String patientId;
    private String alertId;
    private NotificationType type;
    private String message;
    private Boolean read;
    private LocalDateTime createdAt;
}
