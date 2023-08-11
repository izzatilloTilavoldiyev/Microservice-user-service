package uz.pdp.userservice.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateDTO(
        @NotBlank(message = "Old password must not be blank") String oldPassword,
        @NotBlank(message = "New password must not be blank") String newPassword,
        @NotBlank(message = "Repeat password must not be blank") String repeatPassword
) {}
