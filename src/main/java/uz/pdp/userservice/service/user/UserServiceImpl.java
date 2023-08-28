package uz.pdp.userservice.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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
    public String save(UserRequestDTO dto) {
        checkUserEmail(dto.getEmail());

        User user = modelMapper.map(dto, User.class);
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationDate(LocalDateTime.now());
        user.setState(UserState.UNVERIFIED);
        user.setRole(Role.USER);
        userRepository.save(user);
        return mailService.sendVerificationCode(user.getEmail(), user.getVerificationCode());
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

    private void checkUserEmail(String email) {
        if (userRepository.existsUserByEmail(email))
            throw new DuplicateValueException("email already exists");
    }

    private User getUserById(UUID userId) {
        return userRepository.findUserById(userId).orElseThrow(
                () -> new DataNotFoundException("User not found with '" + userId + "' id")
        );
    }
}
