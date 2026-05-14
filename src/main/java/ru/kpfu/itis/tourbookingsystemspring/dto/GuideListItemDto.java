package ru.kpfu.itis.tourbookingsystemspring.dto;

import java.math.BigDecimal;

public record GuideListItemDto(
        Long userId,
        String fullName,
        String username,
        String bio,
        BigDecimal rating,
        long subscriberCount,
        boolean subscribed
) {}