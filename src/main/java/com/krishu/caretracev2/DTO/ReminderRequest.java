package com.krishu.caretracev2.DTO;

import com.krishu.caretracev2.ReminderType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReminderRequest {
    private LocalTime time;
    private LocalDate date;
    private ReminderType type;
    private String medicationId;
    private String routineId;
}
