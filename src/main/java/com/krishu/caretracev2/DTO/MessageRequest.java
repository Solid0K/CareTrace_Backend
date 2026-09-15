package com.krishu.caretracev2.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageRequest {
    private String receiverId;
    private String message;
}
