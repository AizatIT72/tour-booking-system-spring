package ru.kpfu.itis.tourbookingsystemspring.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.kpfu.itis.tourbookingsystemspring.entity.Enum.Role;

@Getter
@Setter
public class RegistrationForm {

    @NotBlank(message = "{validation.email.required}")
    @Email(message = "{validation.email.invalid}")
    private String email;

    @NotBlank(message = "{validation.password.required}")
    @Size(min = 6, message = "{validation.password.size}")
    @Pattern(
            regexp = "^(?=.*[A-ZА-Я])(?=.*\\d).+$",
            message = "{validation.password.pattern}"
    )
    private String password;

    @NotBlank(message = "{validation.username.required}")
    @Size(min = 3, max = 100, message = "{validation.username.size}")
    private String username;

    @NotBlank(message = "{validation.fullname.required}")
    private String fullName;

    @NotBlank(message = "{validation.role.required}")
    private String role;

    public Role getRoleEnum() {
        return Role.valueOf(role);
    }
}