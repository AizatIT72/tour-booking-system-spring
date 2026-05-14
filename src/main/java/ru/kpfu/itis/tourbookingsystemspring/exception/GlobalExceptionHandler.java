package ru.kpfu.itis.tourbookingsystemspring.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import ru.kpfu.itis.tourbookingsystemspring.dto.ErrorDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public Object handleBusiness(BusinessException ex, HttpServletRequest request, Model model) {
        log.warn("Business exception: {} [{}]", ex.getMessageKey(), request.getRequestURI());
        String message = translate(ex.getMessageKey());

        if (isAjax(request)) {
            return jsonResponse(ex.getStatus(), message);
        }
        return errorPage(ex.getStatus(), message, model);
    }

    @ExceptionHandler({NoHandlerFoundException.class, NoResourceFoundException.class})
    public Object handleNotFound(Exception ex,
                                 HttpServletRequest request,
                                 Model model) {
        log.warn("404 at {}", request.getRequestURI());
        String message = translate("error.not.found");

        if (isAjax(request)) {
            return jsonResponse(HttpStatus.NOT_FOUND, message);
        }
        return errorPage(HttpStatus.NOT_FOUND, message, model);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                           HttpServletRequest request,
                                           Model model) {
        log.warn("Method not supported at {}: {}", request.getRequestURI(), ex.getMethod());
        String message = translate("error.method.not.supported");

        if (isAjax(request)) {
            return jsonResponse(HttpStatus.METHOD_NOT_ALLOWED, message);
        }
        return errorPage(HttpStatus.NOT_FOUND, message, model);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Object handleSpringAccessDenied(AccessDeniedException ex,
                                           HttpServletRequest request,
                                           Model model) {
        log.warn("Access denied at {}: {}", request.getRequestURI(), ex.getMessage());
        String message = translate("error.access.denied");

        if (isAjax(request)) {
            return jsonResponse(HttpStatus.FORBIDDEN, message);
        }
        return errorPage(HttpStatus.FORBIDDEN, message, model);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidation(MethodArgumentNotValidException ex,
                                   HttpServletRequest request,
                                   Model model) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fe.getField(), fe.getDefaultMessage());
        }
        log.debug("Validation failed at {}: {}", request.getRequestURI(), fieldErrors);

        if (isAjax(request)) {
            ErrorDto dto = ErrorDto.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(translate("error.validation"))
                    .timestamp(LocalDateTime.now())
                    .fieldErrors(fieldErrors)
                    .build();
            return ResponseEntity.badRequest().body(dto);
        }
        return errorPage(HttpStatus.BAD_REQUEST, translate("error.validation"), model);
    }

    @ExceptionHandler(Exception.class)
    public Object handleAll(Exception ex, HttpServletRequest request, Model model) {
        log.error("Unhandled exception at {}: ", request.getRequestURI(), ex);
        String message = translate("error.internal");

        if (isAjax(request)) {
            return jsonResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
        }
        return errorPage(HttpStatus.INTERNAL_SERVER_ERROR, message, model);
    }

    private boolean isAjax(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String requestedWith = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWith)
                || (accept != null && accept.contains("application/json"));
    }

    private String translate(String key) {
        return messageSource.getMessage(key, null, key, LocaleContextHolder.getLocale());
    }

    private ResponseEntity<ErrorDto> jsonResponse(HttpStatus status, String message) {
        ErrorDto dto = ErrorDto.builder()
                .status(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(status).body(dto);
    }

    private String errorPage(HttpStatus status, String message, Model model) {
        model.addAttribute("status", status.value());
        model.addAttribute("message", message);

        return switch (status) {
            case NOT_FOUND -> "error/404";
            case FORBIDDEN -> "error/403";
            default -> "error/500";
        };
    }
}