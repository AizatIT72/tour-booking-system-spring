package ru.kpfu.itis.tourbookingsystemspring.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingForm {

    @NotNull(message = "{validation.booking.date.required}")
    private Long excursionDateId;

    @NotNull(message = "{validation.booking.participants.required}")
    @Min(value = 1, message = "{validation.booking.participants.min}")
    @Max(value = 100, message = "{validation.booking.participants.max}")
    private Integer participantsCount;

    @Size(max = 1000, message = "{validation.booking.requests.size}")
    private String specialRequests;
}