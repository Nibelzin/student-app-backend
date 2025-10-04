package com.studentapp.api.infra.config.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PeriodConflictException extends RuntimeException {
    public PeriodConflictException(String message) {
        super(message);
    }
}
