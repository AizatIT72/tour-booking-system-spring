package ru.kpfu.itis.tourbookingsystemspring.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends BusinessException {

    public AccessDeniedException(String messageKey) {
        super(messageKey, HttpStatus.FORBIDDEN);
    }
}