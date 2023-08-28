package uz.pdp.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.userservice.domain.entity.user.User;
import uz.pdp.userservice.service.user.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "so guys, let me tell you something";
    }

    @Operation(
            description = "GET endpoint to get user by user id",
            summary = "API to get user by id"
    )
    @GetMapping("/get/{userId}")
    public ResponseEntity<User> getUser(
            @PathVariable UUID userId
    ) {
        User user = userService.getById(userId);
        return ResponseEntity.ok(user);
    }

}
