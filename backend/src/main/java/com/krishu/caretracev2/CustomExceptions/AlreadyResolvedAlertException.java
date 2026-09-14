package com.krishu.caretracev2.CustomExceptions;

public class AlreadyResolvedAlertException extends RuntimeException {
    public AlreadyResolvedAlertException(String message) {
        super(message);
    }
}
