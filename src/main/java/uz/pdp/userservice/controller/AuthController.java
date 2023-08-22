package uz.pdp.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.userservice.domain.dto.LoginDTO;
import uz.pdp.userservice.domain.dto.PasswordUpdateDTO;
import uz.pdp.userservice.domain.dto.ResetPasswordDTO;
import uz.pdp.userservice.domain.dto.UserRequestDTO;
import uz.pdp.userservice.service.auth.AuthService;
import uz.pdp.userservice.service.user.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/user/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.save(userRequestDTO);
    }

    @PostMapping("/verify/{userId}")
    public String verify(
            @PathVariable UUID userId,
            @RequestParam String verificationCode
    ) {
        return userService.verify(userId, verificationCode);
    }

    @GetMapping("/new-veify-code/{userId}")
    public String newVerifyCode(
            @PathVariable UUID userId
    ) {
        return authService.newVerifyCode(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(
            @Valid @RequestBody LoginDTO loginDTO
    ) {
        LoginDTO login = authService.login(loginDTO);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestParam String email
    ) {
        String response = authService.forgotPassword(email);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/reset-password/{userId}")
    public ResponseEntity<String> resetPassword(
            @PathVariable UUID userId,
            @Valid @RequestBody ResetPasswordDTO resetPasswordDTO
    ) {
        String response = authService.resetPassword(userId, resetPasswordDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-password/{userId}")
    public ResponseEntity<String> updatePassword(
            @PathVariable UUID userId,
            PasswordUpdateDTO passwordUpdateDTO
    ) {
        String response = authService.updatePassword(userId, passwordUpdateDTO);
        return ResponseEntity.ok(response);
    }

}
