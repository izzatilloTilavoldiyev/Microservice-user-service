package uz.pdp.userservice.service.user;

import uz.pdp.userservice.domain.entity.user.User;

import java.util.UUID;

public interface UserService {

    User getById(UUID userId);

    User getUserByEmail(String email);
}
