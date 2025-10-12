package com.studentapp.api.infra.config.exception.custom;

public class InvalidPeriodException extends RuntimeException {
    public InvalidPeriodException(String message) {
        super(message);
    }
}
