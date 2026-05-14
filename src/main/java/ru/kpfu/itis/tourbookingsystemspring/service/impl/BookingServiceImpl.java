package ru.kpfu.itis.tourbookingsystemspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.tourbookingsystemspring.dto.BookingDto;
import ru.kpfu.itis.tourbookingsystemspring.entity.Booking;
import ru.kpfu.itis.tourbookingsystemspring.entity.ExcursionDate;
import ru.kpfu.itis.tourbookingsystemspring.entity.Notification;
import ru.kpfu.itis.tourbookingsystemspring.entity.User;
import ru.kpfu.itis.tourbookingsystemspring.entity.enums.BookingStatus;
import ru.kpfu.itis.tourbookingsystemspring.entity.enums.NotificationType;
import ru.kpfu.itis.tourbookingsystemspring.exception.EntityNotFoundException;
import ru.kpfu.itis.tourbookingsystemspring.exception.ValidationException;
import ru.kpfu.itis.tourbookingsystemspring.form.BookingForm;
import ru.kpfu.itis.tourbookingsystemspring.mapper.BookingMapper;
import ru.kpfu.itis.tourbookingsystemspring.repository.BookingRepository;
import ru.kpfu.itis.tourbookingsystemspring.repository.ExcursionDateRepository;
import ru.kpfu.itis.tourbookingsystemspring.repository.NotificationRepository;
import ru.kpfu.itis.tourbookingsystemspring.service.BookingService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ExcursionDateRepository excursionDateRepository;
    private final NotificationRepository notificationRepository;
    private final BookingMapper bookingMapper;

    @Override
    @Transactional
    public Long createBooking(User tourist, BookingForm form) {
        log.info("Creating booking: tourist={}, dateId={}, participants={}",
                tourist.getId(), form.getExcursionDateId(), form.getParticipantsCount());

        ExcursionDate date = excursionDateRepository.findById(form.getExcursionDateId())
                .orElseThrow(() -> {
                    log.warn("ExcursionDate not found: id={}", form.getExcursionDateId());
                    return new EntityNotFoundException("error.date.not.found");
                });

        validateAvailability(date, form.getParticipantsCount());

        BigDecimal totalPrice = date.getExcursion().getPrice()
                .multiply(BigDecimal.valueOf(form.getParticipantsCount()));

        Booking booking = Booking.builder()
                .tourist(tourist)
                .excursionDate(date)
                .participantsCount(form.getParticipantsCount())
                .totalPrice(totalPrice)
                .specialRequests(form.getSpecialRequests())
                .status(BookingStatus.PENDING)
                .build();
        bookingRepository.save(booking);

        decreaseSlots(date, form.getParticipantsCount());
        notifyGuide(tourist, date, form.getParticipantsCount());

        log.info("Booking created: id={}, totalPrice={}", booking.getId(), totalPrice);
        return booking.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getBookingsOfTourist(Long touristId) {
        log.debug("Loading bookings for tourist={}", touristId);
        return bookingRepository.findAllByTouristId(touristId).stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingDto> getBookingsOfTourist(Long touristId, BookingStatus statusFilter) {
        log.debug("Loading bookings: tourist={}, filter={}", touristId, statusFilter);
        List<Booking> bookings = (statusFilter == null)
                ? bookingRepository.findAllByTouristId(touristId)
                : bookingRepository.findAllByTouristIdAndStatus(touristId, statusFilter);
        return bookings.stream().map(bookingMapper::toDto).toList();
    }

    @Override
    @Transactional
    public void cancelBooking(Long bookingId, Long touristId) {
        log.info("Cancel booking: id={}, tourist={}", bookingId, touristId);

        Booking booking = bookingRepository.findByIdWithDetails(bookingId)
                .orElseThrow(() -> {
                    log.warn("Booking not found: id={}", bookingId);
                    return new EntityNotFoundException("error.booking.not.found");
                });

        validateCancellation(booking, touristId);

        returnSlots(booking);
        booking.setStatus(BookingStatus.CANCELLED);
        notifyGuideAboutCancellation(booking);

        log.info("Booking cancelled: id={}, slots returned={}", bookingId, booking.getParticipantsCount());
    }

    private void validateCancellation(Booking booking, Long touristId) {
        if (!booking.getTourist().getId().equals(touristId)) {
            log.warn("Tourist {} tried to cancel booking {} of tourist {}",
                    touristId, booking.getId(), booking.getTourist().getId());
            throw new ru.kpfu.itis.tourbookingsystemspring.exception.AccessDeniedException("error.access.denied");
        }
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new ValidationException("error.booking.already.cancelled");
        }
        if (booking.getStatus() == BookingStatus.COMPLETED) {
            throw new ValidationException("error.booking.already.completed");
        }
        if (booking.getExcursionDate().getDateTime().isBefore(LocalDateTime.now())) {
            throw new ValidationException("error.booking.past.date");
        }
    }

    private void returnSlots(Booking booking) {
        ExcursionDate date = booking.getExcursionDate();
        date.setAvailableSlots(date.getAvailableSlots() + booking.getParticipantsCount());
        date.setAvailable(true);
    }

    private void notifyGuideAboutCancellation(Booking booking) {
        Notification notification = Notification.builder()
                .recipient(booking.getExcursionDate().getExcursion().getGuide())
                .title("Отмена бронирования")
                .message(String.format(
                        "%s отменил бронь на «%s» (%s)",
                        booking.getTourist().getFullName(),
                        booking.getExcursionDate().getExcursion().getTitle(),
                        booking.getExcursionDate().getDateTime().format(
                                java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                        )
                ))
                .type(NotificationType.BOOKING_CANCELLED)
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }

    private void validateAvailability(ExcursionDate date, int participants) {
        if (!date.isAvailable()) {
            log.warn("Date is not available: id={}", date.getId());
            throw new ValidationException("error.booking.date.unavailable");
        }
        if (date.getAvailableSlots() < participants) {
            log.warn("Not enough slots: requested={}, available={}",
                    participants, date.getAvailableSlots());
            throw new ValidationException("error.booking.not.enough.slots");
        }
    }

    private void decreaseSlots(ExcursionDate date, int participants) {
        int remaining = date.getAvailableSlots() - participants;
        date.setAvailableSlots(remaining);
        if (remaining == 0) {
            date.setAvailable(false);
        }
        excursionDateRepository.save(date);
    }

    private void notifyGuide(User tourist, ExcursionDate date, int participants) {
        Notification notification = Notification.builder()
                .recipient(date.getExcursion().getGuide())
                .title("Новое бронирование")
                .message(String.format(
                        "%s забронировал «%s» на %d человек",
                        tourist.getFullName(),
                        date.getExcursion().getTitle(),
                        participants
                ))
                .type(NotificationType.NEW_BOOKING)
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }
}