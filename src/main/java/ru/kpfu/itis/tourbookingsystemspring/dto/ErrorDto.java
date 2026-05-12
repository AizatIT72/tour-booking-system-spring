package ru.kpfu.itis.tourbookingsystemspring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto {

    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
    private final Map<String, String> fieldErrors;
}
