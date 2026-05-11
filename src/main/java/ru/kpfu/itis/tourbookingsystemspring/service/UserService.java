package ru.kpfu.itis.tourbookingsystemspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.tourbookingsystemspring.entity.Role;
import ru.kpfu.itis.tourbookingsystemspring.entity.User;
import ru.kpfu.itis.tourbookingsystemspring.form.RegistrationForm;
import ru.kpfu.itis.tourbookingsystemspring.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(RegistrationForm form) {
        if (userRepository.existsByEmail(form.getEmail())) {
            throw new IllegalArgumentException("error.email.exists");
        }
        if (userRepository.existsByUsername(form.getUsername())) {
            throw new IllegalArgumentException("error.username.exists");
        }

        Role role = form.getRoleEnum();
        if (role == Role.ADMIN) {
            throw new IllegalArgumentException("error.role.admin.forbidden");
        }

        User user = new User();
        user.setEmail(form.getEmail());
        user.setUsername(form.getUsername());
        user.setFullName(form.getFullName());
        user.setPasswordHash(passwordEncoder.encode(form.getPassword()));
        user.setRole(role);
        user.setEnabled(true);

        return userRepository.save(user);
    }
}