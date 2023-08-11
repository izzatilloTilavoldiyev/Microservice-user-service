package uz.pdp.userservice.service.user;

import uz.pdp.userservice.domain.dto.LoginDTO;
import uz.pdp.userservice.domain.dto.ResetPasswordDTO;
import uz.pdp.userservice.domain.dto.UserRequestDTO;

import java.util.UUID;

public interface UserService {

    String save(UserRequestDTO dto);

    String verify(UUID userId, String verificationCode);

    String newVerifyCode(UUID userId);

    LoginDTO login(LoginDTO loginDTO);

    String forgotPassword(String email);

    String resetPassword(UUID userId, ResetPasswordDTO resetPasswordDTO);


}
