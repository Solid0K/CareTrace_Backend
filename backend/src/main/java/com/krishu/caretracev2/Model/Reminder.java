package com.krishu.caretracev2.Model;

import com.krishu.caretracev2.ReminderType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@Document(collection="reminder")
public class Reminder {
    @Id
    private String id;
    private String patientId;
    private LocalTime time;
    private LocalDate date;
    private ReminderType type;
    private String medicationId;
    private String routineId;
}
