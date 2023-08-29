package uz.pdp.userservice.service.user;

import uz.pdp.userservice.domain.dto.UserRequestDTO;
import uz.pdp.userservice.domain.dto.response.ResponseDTO;
import uz.pdp.userservice.domain.dto.response.UserResponseDTO;
import uz.pdp.userservice.domain.entity.user.User;

import java.util.UUID;

public interface UserService {

    ResponseDTO<UserResponseDTO> save(UserRequestDTO dto);

    String verify(UUID userId, String verificationCode);

    User getById(UUID userId);

    String genVerificationCodee();
}
