package ru.kpfu.itis.tourbookingsystemspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kpfu.itis.tourbookingsystemspring.entity.User;
import ru.kpfu.itis.tourbookingsystemspring.form.RegistrationForm;
import ru.kpfu.itis.tourbookingsystemspring.security.CustomUserDetails;
import ru.kpfu.itis.tourbookingsystemspring.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping("/sign-up")
    public String signUpPage(Model model) {
        if (!model.containsAttribute("form")) {
            model.addAttribute("form", new RegistrationForm());
        }
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String register(@Valid @ModelAttribute("form") RegistrationForm form,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", collectFieldErrors(bindingResult));
            return "sign-up";
        }

        try {
            User user = userService.register(form);
            authenticateUser(user);
            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            String message = messageSource.getMessage(
                    e.getMessage(), null, e.getMessage(),
                    LocaleContextHolder.getLocale()
            );
            model.addAttribute("globalError", message);
            return "sign-up";
        }
    }

    @GetMapping("/sign-in")
    public String signInPage() {
        return "sign-in";
    }

    private Map<String, String> collectFieldErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }

    private void authenticateUser(User user) {
        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}