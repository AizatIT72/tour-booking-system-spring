package ru.kpfu.itis.tourbookingsystemspring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kpfu.itis.tourbookingsystemspring.form.ExcursionSearchForm;
import ru.kpfu.itis.tourbookingsystemspring.security.CustomUserDetails;
import ru.kpfu.itis.tourbookingsystemspring.service.ExcursionService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ExcursionController {

    private final ExcursionService excursionService;

    @GetMapping("/excursions")
    public String list(@ModelAttribute("form") ExcursionSearchForm form,
                       @AuthenticationPrincipal CustomUserDetails userDetails,
                       Model model) {
        log.debug("GET /excursions by user={}", userDetails.getUsername());

        model.addAttribute("currentUser", userDetails.getUser());
        model.addAttribute("excursionsPage", excursionService.search(form));
        model.addAttribute("categories", excursionService.getAllCategories());
        return "excursion/list";
    }

    @GetMapping("/excursions/{id}")
    public String details(@PathVariable Long id,
                          @AuthenticationPrincipal CustomUserDetails userDetails,
                          Model model) {
        log.debug("GET /excursions/{} by user={}", id, userDetails.getUsername());

        model.addAttribute("currentUser", userDetails.getUser());
        model.addAttribute("excursion", excursionService.getDetails(id));
        return "excursion/details";
    }
}