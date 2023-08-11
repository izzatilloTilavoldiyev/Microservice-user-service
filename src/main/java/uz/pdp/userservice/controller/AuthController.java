package uz.pdp.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.userservice.domain.dto.LoginDTO;
import uz.pdp.userservice.domain.dto.UserRequestDTO;
import uz.pdp.userservice.service.user.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/user/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

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
        return userService.newVerifyCode(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTO> login(
            @Valid @RequestBody LoginDTO loginDTO
    ) {
        LoginDTO login = userService.login(loginDTO);
        return ResponseEntity.ok(login);
    }

}
