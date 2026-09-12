package com.krishu.caretracev2.DTO;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.krishu.caretracev2.Model.CareTaker;
import com.krishu.caretracev2.Model.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CareTakerPatientPair {
    private CareTaker careTaker;
    private Patient patient;
}
