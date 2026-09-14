package com.krishu.caretracev2.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@Document(collection="message")
public class Message {
    @Id
    private String id;
    private String senderId;
    private String receiverId;
    private String patientId;
    private String content;
    private Boolean read;
    private LocalDateTime createdAt;
}
