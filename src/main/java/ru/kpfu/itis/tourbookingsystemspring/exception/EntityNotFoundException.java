package ru.kpfu.itis.tourbookingsystemspring.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String messageKey) {
        super(messageKey, HttpStatus.NOT_FOUND);
    }
}