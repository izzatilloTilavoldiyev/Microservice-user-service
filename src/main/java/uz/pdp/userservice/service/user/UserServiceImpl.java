package uz.pdp.userservice.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.userservice.exception.DataNotFoundException;
import uz.pdp.userservice.domain.entity.user.User;
import uz.pdp.userservice.repository.UserRepository;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getById(UUID userId) {
        return getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(
                () -> new DataNotFoundException("User not found with email: " + email)
        );
    }

    private User getUserById(UUID userId) {
        return userRepository.findUserById(userId).orElseThrow(
                () -> new DataNotFoundException("User not found with '" + userId + "' id")
        );
    }
}
