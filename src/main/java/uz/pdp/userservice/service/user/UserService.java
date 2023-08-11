package uz.pdp.userservice.service.user;

import uz.pdp.userservice.domain.dto.UserRequestDTO;

import java.util.UUID;

public interface UserService {

    String save(UserRequestDTO dto);

    String verify(UUID userId, String verificationCode);
}
