package ru.kpfu.itis.tourbookingsystemspring.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ExcursionDetailsDto(
        Long id,
        String title,
        String description,
        String city,
        BigDecimal price,
        Integer durationHours,
        Integer maxGroupSize,
        LocalDateTime createdAt,
        Long guideId,
        String guideName,
        String guideUsername,
        List<String> categories,
        List<ExcursionDateDto> dates
) {}