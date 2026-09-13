package com.krishu.caretracev2.CustomExceptions;

public class PatientLocationNotKnowException extends RuntimeException {
    public PatientLocationNotKnowException(String message) {
        super(message);
    }
}
