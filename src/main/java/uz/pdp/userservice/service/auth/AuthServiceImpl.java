package uz.pdp.userservice.service.auth;

import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.userservice.domain.dto.LoginDTO;
import uz.pdp.userservice.domain.dto.PasswordUpdateDTO;
import uz.pdp.userservice.domain.dto.ResetPasswordDTO;
import uz.pdp.userservice.domain.entity.user.User;
import uz.pdp.userservice.exception.DataNotFoundException;
import uz.pdp.userservice.exception.UserPasswordWrongException;
import uz.pdp.userservice.repository.UserRepository;
import uz.pdp.userservice.service.MailService;
import uz.pdp.userservice.service.user.UserService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final UserService userService;
    private final MailService mailService;

    @Override
    public String newVerifyCode(UUID userId) {
        User user = userService.getById(userId);
        user.setVerificationCode(userService.genVerificationCodee());
        user.setVerificationDate(LocalDateTime.now());
        userRepository.save(user);
        return mailService.sendVerificationCode(user.getEmail(), user.getVerificationCode());
    }

    @Override
    public LoginDTO login(LoginDTO loginDTO) {
        User user = userRepository.findUserByEmail(loginDTO.email()).orElseThrow(
                () -> new DataNotFoundException("User not found with '" + loginDTO.email() + "' email")
        );
        if (!Objects.equals(user.getPassword(), loginDTO.password()))
            throw new UserPasswordWrongException("User password wrong Password: " + loginDTO.password());
        return LoginDTO.builder()
                .email(loginDTO.email())
                .password(loginDTO.password())
                .build();
    }

    @Override
    public String forgotPassword(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new DataNotFoundException("User not found with '" + email + "' email")
        );
        user.setVerificationCode(userService.genVerificationCodee());
        user.setVerificationDate(LocalDateTime.now());
        userRepository.save(user);
        return mailService.sendVerificationCode(user.getEmail(), user.getVerificationCode());
    }

    @Override
    public String resetPassword(UUID userId, ResetPasswordDTO resetPasswordDTO) {
        User user = userService.getById(userId);
        if (!user.getVerificationDate().plusMinutes(5).isAfter(LocalDateTime.now())
                || !Objects.equals(user.getVerificationCode(), resetPasswordDTO.verificationCode()))
            return "Verification code wrong";
        if (!Objects.equals(resetPasswordDTO.newPassword(), resetPasswordDTO.repeatPassword()))
            throw new BadRequestException("New password and repeat password are not same");
        user.setPassword(resetPasswordDTO.newPassword());
        userRepository.save(user);
        return "Success";
    }

    @Override
    public String updatePassword(UUID userId, PasswordUpdateDTO passwordUpdateDTO) {
        User user = userService.getById(userId);
        if (!Objects.equals(user.getPassword(), passwordUpdateDTO.oldPassword()))
            throw new BadRequestException("Old password wrong! Password: " + passwordUpdateDTO.oldPassword());
        if (!Objects.equals(passwordUpdateDTO.newPassword(), passwordUpdateDTO.repeatPassword()))
            throw new BadRequestException("User new password and repeat password are must be same");
        user.setPassword(passwordUpdateDTO.newPassword());
        userRepository.save(user);
        return "Password successfully updated";
    }
}
