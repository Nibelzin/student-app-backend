package com.studentapp.api.infra.config.exception.handler;

import com.studentapp.api.infra.config.exception.custom.EmailAlreadyExistsException;
import com.studentapp.api.infra.config.exception.custom.PeriodConflictException;
import com.studentapp.api.infra.config.exception.custom.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {

        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.CONFLICT.value(),
                "error", "Conflict",
                "message", e.getMessage()
        );

        return new ResponseEntity<>(body.toString(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.NOT_FOUND.value(),
                "error", "Not Found",
                "message", e.getMessage()
        );

        return new ResponseEntity<>(body.toString(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PeriodConflictException.class)
    public ResponseEntity<String> handlePeriodConflictException(PeriodConflictException e) {
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.CONFLICT.value(),
                "error", "Conflict",
                "message", e.getMessage()
        );

        return new ResponseEntity<>(body.toString(), HttpStatus.CONFLICT);
    }

}
