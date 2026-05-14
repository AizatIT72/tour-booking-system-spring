package ru.kpfu.itis.tourbookingsystemspring.dto;

import ru.kpfu.itis.tourbookingsystemspring.entity.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BookingDto(
        Long id,
        Long excursionId,
        String excursionTitle,
        String guideName,
        String city,
        LocalDateTime dateTime,
        Integer participantsCount,
        BigDecimal totalPrice,
        String specialRequests,
        BookingStatus status,
        LocalDateTime bookingDate
) {}