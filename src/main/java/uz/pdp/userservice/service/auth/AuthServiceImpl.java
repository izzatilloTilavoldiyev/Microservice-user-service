package uz.pdp.userservice.service.auth;

import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.userservice.domain.dto.LoginDTO;
import uz.pdp.userservice.domain.dto.PasswordUpdateDTO;
import uz.pdp.userservice.domain.dto.ResetPasswordDTO;
import uz.pdp.userservice.domain.dto.request.UserRequestDTO;
import uz.pdp.userservice.domain.dto.response.ResponseDTO;
import uz.pdp.userservice.domain.dto.response.UserResponseDTO;
import uz.pdp.userservice.domain.entity.user.Role;
import uz.pdp.userservice.domain.entity.user.User;
import uz.pdp.userservice.domain.entity.user.UserState;
import uz.pdp.userservice.domain.entity.user.VerificationData;
import uz.pdp.userservice.exception.DuplicateValueException;
import uz.pdp.userservice.exception.UserPasswordWrongException;
import uz.pdp.userservice.repository.UserRepository;
import uz.pdp.userservice.service.MailService;
import uz.pdp.userservice.service.user.UserService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final UserService userService;
    private final MailService mailService;
    private final ModelMapper modelMapper;

    @Override
    public ResponseDTO<UserResponseDTO> save(UserRequestDTO dto) {
        checkUserEmailExists(dto.getEmail());
        checkEmailIsValid(dto.getEmail());
        User user = modelMapper.map(dto, User.class);
        user.setState(UserState.UNVERIFIED);
        user.setRole(Role.USER);
        user.setVerificationData(generateVerificationCode());
        User savedUser = userRepository.save(user);
        String responseMessage = mailService.sendVerificationCode(user.getEmail(), user.getVerificationCode());
        UserResponseDTO mappedUser = modelMapper.map(savedUser, UserResponseDTO.class);
        return new ResponseDTO<>(responseMessage, 200, mappedUser);
    }

    @Override
    public String verify(String email, String verificationCode) {
        User user = userService.getUserByEmail(email);
        if (checkVerificationCodeExpiration(user.getVerificationData(), verificationCode))
            return "Verification code wrong";
        user.setState(UserState.ACTIVE);
        userRepository.save(user);
        return "Successfully verified";
    }

    @Override
    public String newVerifyCode(String email) {
        User user = userService.getUserByEmail(email);
        user.setVerificationData(generateVerificationCode());
        userRepository.save(user);
        return mailService.sendVerificationCode(user.getEmail(), user.getVerificationData().getVerificationCode());
    }

    @Override
    public LoginDTO login(LoginDTO loginDTO) {
        User user = userService.getUserByEmail(loginDTO.email());
        if (!Objects.equals(user.getPassword(), loginDTO.password()))
            throw new UserPasswordWrongException("User password wrong Password: " + loginDTO.password());
        return LoginDTO.builder()
                .email(loginDTO.email())
                .password(loginDTO.password())
                .build();
    }

    @Override
    public String forgotPassword(String email) {
        User user = userService.getUserByEmail(email);
        user.setVerificationData(generateVerificationCode());
        userRepository.save(user);
        return mailService.sendVerificationCode(user.getEmail(), user.getVerificationData().getVerificationCode());
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

    private VerificationData generateVerificationCode() {
        Random random = new Random();
        String verificationCode = String.valueOf(random.nextInt(100000, 1000000));
        return new VerificationData(verificationCode, LocalDateTime.now());
    }

    private void checkUserEmailExists(String email) {
        if (userRepository.existsUserByEmail(email))
            throw new DuplicateValueException("Email already exists with Email: " + email);
    }

    private void checkEmailIsValid(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    public boolean checkVerificationCodeExpiration(VerificationData verificationData, String verificationCode) {
        return !verificationData.getVerificationDate().plusMinutes(5).isAfter(LocalDateTime.now())
                || !Objects.equals(verificationData.getVerificationCode(), verificationCode);

    }
}
