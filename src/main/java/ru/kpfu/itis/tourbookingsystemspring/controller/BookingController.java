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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.tourbookingsystemspring.entity.enums.BookingStatus;
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
    public String myBookings(@RequestParam(value = "status", required = false) BookingStatus statusFilter,
                             @AuthenticationPrincipal CustomUserDetails userDetails,
                             Model model) {
        log.debug("GET /my-bookings by={}, filter={}", userDetails.getUsername(), statusFilter);

        model.addAttribute("currentUser", userDetails.getUser());
        model.addAttribute("bookings", bookingService.getBookingsOfTourist(userDetails.getId(), statusFilter));
        model.addAttribute("currentStatus", statusFilter);
        return "booking/my-bookings";
    }

    @PostMapping("/my-bookings/{id}/cancel")
    @PreAuthorize("hasRole('TOURIST')")
    public String cancelBooking(@PathVariable Long id,
                                @AuthenticationPrincipal CustomUserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        log.debug("POST /my-bookings/{}/cancel by={}", id, userDetails.getUsername());
        bookingService.cancelBooking(id, userDetails.getId());
        redirectAttributes.addFlashAttribute("cancelSuccess", true);
        return "redirect:/my-bookings";
    }
}