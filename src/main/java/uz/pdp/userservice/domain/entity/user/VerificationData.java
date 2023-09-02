package uz.pdp.userservice.domain.entity.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationData {
    private String verificationCode;

    private LocalDateTime verificationDate;
}