package ru.kpfu.itis.tourbookingsystemspring.service;

import ru.kpfu.itis.tourbookingsystemspring.dto.BookingDto;
import ru.kpfu.itis.tourbookingsystemspring.entity.User;
import ru.kpfu.itis.tourbookingsystemspring.entity.enums.BookingStatus;
import ru.kpfu.itis.tourbookingsystemspring.form.BookingForm;

import java.util.List;

public interface BookingService {

    Long createBooking(User tourist, BookingForm form);

    List<BookingDto> getBookingsOfTourist(Long touristId);

    List<BookingDto> getBookingsOfTourist(Long touristId, BookingStatus statusFilter);

    void cancelBooking(Long bookingId, Long touristId);
}