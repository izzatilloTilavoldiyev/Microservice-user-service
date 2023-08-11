package uz.pdp.userservice.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordDTO(
        @NotBlank(message = "Verification code must not be blank") String verificationCode,
        @NotBlank(message = "New password must not be blank") String newPassword,
        @NotBlank(message = "Repeat password must not be blank") String repeatPassword
) {}
