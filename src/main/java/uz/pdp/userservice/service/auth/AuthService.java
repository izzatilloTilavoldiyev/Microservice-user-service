package uz.pdp.userservice.service.auth;

import uz.pdp.userservice.domain.dto.LoginDTO;
import uz.pdp.userservice.domain.dto.PasswordUpdateDTO;
import uz.pdp.userservice.domain.dto.ResetPasswordDTO;

import java.util.UUID;

public interface AuthService {

    String newVerifyCode(UUID userId);

    LoginDTO login(LoginDTO loginDTO);

    String forgotPassword(String email);

    String resetPassword(UUID userId, ResetPasswordDTO resetPasswordDTO);

    String updatePassword(UUID userId, PasswordUpdateDTO passwordUpdateDTO);
}
