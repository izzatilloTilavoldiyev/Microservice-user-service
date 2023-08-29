package uz.pdp.userservice.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "name must not be blank")
    @Size(min = 2, message = "Name length must be at least 2 characters")
    private String name;

    @NotBlank(message = "email must not be blank")
    @Size(min = 12, message = "Email length must be at least 12 characters")
    private String email;

    @NotBlank(message = "password must not be blank")
    private String password;
}
