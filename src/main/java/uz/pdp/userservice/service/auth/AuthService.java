package uz.pdp.userservice.service.auth;

import uz.pdp.userservice.domain.dto.LoginDTO;
import uz.pdp.userservice.domain.dto.PasswordUpdateDTO;
import uz.pdp.userservice.domain.dto.ResetPasswordDTO;
import uz.pdp.userservice.domain.dto.request.UserRequestDTO;
import uz.pdp.userservice.domain.dto.response.ResponseDTO;
import uz.pdp.userservice.domain.dto.response.UserResponseDTO;

import java.util.UUID;

public interface AuthService {
    ResponseDTO<UserResponseDTO> save(UserRequestDTO dto);

    String verify(String email, String verificationCode);

    String newVerifyCode(String email);

    LoginDTO login(LoginDTO loginDTO);

    String forgotPassword(String email);

    String resetPassword(UUID userId, ResetPasswordDTO resetPasswordDTO);

    String updatePassword(UUID userId, PasswordUpdateDTO passwordUpdateDTO);
}
