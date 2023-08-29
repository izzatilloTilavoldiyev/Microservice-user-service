package uz.pdp.userservice.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.userservice.domain.dto.response.ResponseDTO;
import uz.pdp.userservice.domain.dto.response.UserResponseDTO;
import uz.pdp.userservice.domain.entity.user.Role;
import uz.pdp.userservice.exception.DataNotFoundException;
import uz.pdp.userservice.exception.DuplicateValueException;
import uz.pdp.userservice.domain.dto.UserRequestDTO;
import uz.pdp.userservice.domain.entity.user.User;
import uz.pdp.userservice.domain.entity.user.UserState;
import uz.pdp.userservice.repository.UserRepository;
import uz.pdp.userservice.service.MailService;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final ModelMapper modelMapper;


    @Override
    public ResponseDTO<UserResponseDTO> save(UserRequestDTO dto) {
        checkUserEmailExists(dto.getEmail());
        checkEmailIsValid(dto.getEmail());
        User user = modelMapper.map(dto, User.class);
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationDate(LocalDateTime.now());
        user.setState(UserState.UNVERIFIED);
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);
        String responseMessage = mailService.sendVerificationCode(user.getEmail(), user.getVerificationCode());

        UserResponseDTO mappedUser = modelMapper.map(savedUser, UserResponseDTO.class);
        return new ResponseDTO<>(responseMessage, 200, mappedUser);
    }

    @Override
    public String verify(UUID userId, String verificationCode) {
        User user = getUserById(userId);
        if (!user.getVerificationDate().plusMinutes(5).isAfter(LocalDateTime.now())
                || !Objects.equals(user.getVerificationCode(), verificationCode))
            return "Verification code wrong";
        user.setState(UserState.ACTIVE);
        userRepository.save(user);
        return "Successfully verified";
    }

    @Override
    public User getById(UUID userId) {
        return getUserById(userId);
    }

    @Override
    public String genVerificationCodee() {
        return generateVerificationCode();
    }

    private String generateVerificationCode() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100000, 1000000));
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

    private User getUserById(UUID userId) {
        return userRepository.findUserById(userId).orElseThrow(
                () -> new DataNotFoundException("User not found with '" + userId + "' id")
        );
    }
}
