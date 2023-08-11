package uz.pdp.userservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginDTO(
        @NotBlank(message = "Email must not be blank") String email,
        @NotBlank(message = "Password must not be blank") String password
) {}
