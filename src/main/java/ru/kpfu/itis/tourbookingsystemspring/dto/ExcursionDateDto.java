package ru.kpfu.itis.tourbookingsystemspring.dto;

import java.time.LocalDateTime;

public record ExcursionDateDto(
        Long id,
        LocalDateTime dateTime,
        Integer availableSlots,
        boolean isAvailable
) {}