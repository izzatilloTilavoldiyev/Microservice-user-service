package uz.pdp.userservice.service.user;

import uz.pdp.userservice.domain.dto.UserRequestDTO;
import uz.pdp.userservice.domain.entity.user.User;

import java.util.UUID;

public interface UserService {

    String save(UserRequestDTO dto);

    String verify(UUID userId, String verificationCode);

    User getById(UUID userId);

    String genVerificationCodee();

}
