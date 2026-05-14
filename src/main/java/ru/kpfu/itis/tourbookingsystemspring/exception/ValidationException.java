package ru.kpfu.itis.tourbookingsystemspring.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends BusinessException {

    public ValidationException(String messageKey) {
        super(messageKey, HttpStatus.BAD_REQUEST);
    }
}