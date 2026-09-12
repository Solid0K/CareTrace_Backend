package com.krishu.caretracev2.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection="patient")
public class Patient {

    @Id
    private String id;
    private String userId;
    private Integer age;
    private String preferred_language;
    private String careTakerId;
}
