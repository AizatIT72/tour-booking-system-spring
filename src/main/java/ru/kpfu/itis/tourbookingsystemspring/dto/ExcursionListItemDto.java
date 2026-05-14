package ru.kpfu.itis.tourbookingsystemspring.dto;

import java.math.BigDecimal;
import java.util.List;

public record ExcursionListItemDto(
        Long id,
        String title,
        String shortDescription,
        String city,
        BigDecimal price,
        Integer durationHours,
        Integer maxGroupSize,
        Long guideId,
        String guideName,
        List<String> categories
) {}