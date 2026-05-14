package ru.kpfu.itis.tourbookingsystemspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.tourbookingsystemspring.form.BookingForm;
import ru.kpfu.itis.tourbookingsystemspring.security.CustomUserDetails;
import ru.kpfu.itis.tourbookingsystemspring.service.BookingService;
import ru.kpfu.itis.tourbookingsystemspring.service.ExcursionService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final ExcursionService excursionService;

    @GetMapping("/bookings/new")
    @PreAuthorize("hasRole('TOURIST')")
    public String bookingForm(@RequestParam Long excursionId,
                              @RequestParam Long dateId,
                              @AuthenticationPrincipal CustomUserDetails userDetails,
                              Model model) {
        log.debug("GET /bookings/new: excursionId={}, dateId={}, by={}",
                excursionId, dateId, userDetails.getUsername());

        BookingForm form = new BookingForm();
        form.setExcursionDateId(dateId);
        form.setParticipantsCount(1);

        model.addAttribute("currentUser", userDetails.getUser());
        model.addAttribute("form", form);
        model.addAttribute("excursion", excursionService.getDetails(excursionId));
        model.addAttribute("selectedDateId", dateId);
        return "booking/new";
    }

    @PostMapping("/bookings")
    @PreAuthorize("hasRole('TOURIST')")
    public String create(@Valid @ModelAttribute("form") BookingForm form,
                         BindingResult bindingResult,
                         @RequestParam Long excursionId,
                         @AuthenticationPrincipal CustomUserDetails userDetails,
                         Model model) {
        log.debug("POST /bookings: excursionId={}, by={}", excursionId, userDetails.getUsername());

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errors.put(fe.getField(), fe.getDefaultMessage());
            }
            model.addAttribute("currentUser", userDetails.getUser());
            model.addAttribute("excursion", excursionService.getDetails(excursionId));
            model.addAttribute("selectedDateId", form.getExcursionDateId());
            model.addAttribute("errors", errors);
            return "booking/new";
        }

        bookingService.createBooking(userDetails.getUser(), form);
        return "redirect:/my-bookings?created";
    }

    @GetMapping("/my-bookings")
    @PreAuthorize("hasRole('TOURIST')")
    public String myBookings(@AuthenticationPrincipal CustomUserDetails userDetails,
                             Model model) {
        log.debug("GET /my-bookings by={}", userDetails.getUsername());

        model.addAttribute("currentUser", userDetails.getUser());
        model.addAttribute("bookings", bookingService.getBookingsOfTourist(userDetails.getUser().getId()));
        return "booking/my-bookings";
    }
}