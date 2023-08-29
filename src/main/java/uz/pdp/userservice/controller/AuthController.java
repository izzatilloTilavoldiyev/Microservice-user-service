package uz.pdp.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.userservice.domain.dto.LoginDTO;
import uz.pdp.userservice.domain.dto.PasswordUpdateDTO;
import uz.pdp.userservice.domain.dto.ResetPasswordDTO;
import uz.pdp.userservice.domain.dto.request.UserRequestDTO;
import uz.pdp.userservice.domain.dto.response.ResponseDTO;
import uz.pdp.userservice.domain.dto.response.UserResponseDTO;
import uz.pdp.userservice.service.auth.AuthService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            description = "POST endpoint to user sign up",
            summary = "API to sign up"
    )
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDTO<UserResponseDTO>> signUp(
            @Valid @RequestBody UserRequestDTO userRequestDTO
    ) {
        ResponseDTO<UserResponseDTO> response = authService.save(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            description = "POST endpoint to user verify after signed up",
            summary = "API to user verify"
    )
    @PostMapping("/verify")
    public String verify(
            @RequestParam String email,
            @RequestParam String verificationCode
    ) {
        return authService.verify(email, verificationCode);
    }

    @Operation(
            description = "POST endpoint to user regenerate verification code",
            summary = "API to regenerate verify code"
    )
    @GetMapping("/new-veify-code/{userId}")
    public String newVerifyCode(
            @PathVariable UUID userId
    ) {
        return authService.newVerifyCode(userId);
    }

    @Operation(
            description = "POST endpoint to user login after verified",
            summary = "API to login"
    )
    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(
            @Valid @RequestBody LoginDTO loginDTO
    ) {
        LoginDTO login = authService.login(loginDTO);
        return ResponseEntity.ok(login);
    }

    @Operation(
            description = "POST endpoint to user forgot password",
            summary = "API to forgot password"
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestParam String email
    ) {
        String response = authService.forgotPassword(email);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "PUT endpoint to user reset password",
            summary = "API to reset password"
    )
    @PutMapping("/reset-password/{userId}")
    public ResponseEntity<String> resetPassword(
            @PathVariable UUID userId,
            @Valid @RequestBody ResetPasswordDTO resetPasswordDTO
    ) {
        String response = authService.resetPassword(userId, resetPasswordDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "PUT endpoint to user update password",
            summary = "API to update password"
    )
    @PutMapping("/update-password/{userId}")
    public ResponseEntity<String> updatePassword(
            @PathVariable UUID userId,
            PasswordUpdateDTO passwordUpdateDTO
    ) {
        String response = authService.updatePassword(userId, passwordUpdateDTO);
        return ResponseEntity.ok(response);
    }

}
