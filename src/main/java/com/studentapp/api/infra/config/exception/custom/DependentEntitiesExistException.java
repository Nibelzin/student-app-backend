package com.studentapp.api.infra.config.exception.custom;

public class DependentEntitiesExistException extends RuntimeException {
    public DependentEntitiesExistException(String message) {
        super(message);
    }
}
