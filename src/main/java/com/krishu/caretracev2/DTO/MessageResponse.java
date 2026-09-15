package com.krishu.caretracev2.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageResponse {
    private String id;
    private String senderId;
    private String receiverId;
    private String patientId;
    private String content;
    private boolean read;
    private LocalDateTime createdAt;
}

