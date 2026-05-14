package ru.kpfu.itis.tourbookingsystemspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.itis.tourbookingsystemspring.security.CustomUserDetails;
import ru.kpfu.itis.tourbookingsystemspring.service.SubscriptionService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GuideController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/guides")
    public String allGuides(@AuthenticationPrincipal CustomUserDetails userDetails,
                            Model model) {
        log.debug("GET /guides by={}", userDetails.getUsername());

        Long touristId = userDetails.getUser().getRole().name().equals("TOURIST")
                ? userDetails.getId() : null;

        model.addAttribute("currentUser", userDetails.getUser());
        model.addAttribute("guides", subscriptionService.getAllGuides(touristId));
        return "guide/list";
    }

    @GetMapping("/my-guides")
    @PreAuthorize("hasRole('TOURIST')")
    public String myGuides(@AuthenticationPrincipal CustomUserDetails userDetails,
                           Model model) {
        log.debug("GET /my-guides by={}", userDetails.getUsername());

        model.addAttribute("currentUser", userDetails.getUser());
        model.addAttribute("guides", subscriptionService.getMyGuides(userDetails.getId()));
        return "guide/my-guides";
    }
}