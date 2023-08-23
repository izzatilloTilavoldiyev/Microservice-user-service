package uz.pdp.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/get/{userId}")
    public ResponseEntity<User> getUser(
            @PathVariable UUID userId
    ) {
        User user = userService.getById(userId);
        return ResponseEntity.ok(user);
    }

}
