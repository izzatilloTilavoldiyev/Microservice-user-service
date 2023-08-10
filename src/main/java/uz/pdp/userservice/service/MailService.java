package uz.pdp.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uz.pdp.userservice.domain.dto.MailDTO;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class MailService {

    private final RestTemplate restTemplate;

    @Value("${services.notification-url}")
    private String notificationServiceUrl;

    public String sendVerificationCode(String email, String verificationCode) {
        String message = "This is your verification code: " + verificationCode;
        return sendMail(message, email, "/send-single");
    }

    private String sendMail(String message, String email, String uri) {
        MailDTO mailDto = new MailDTO(message, email);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MailDTO> entity = new HttpEntity<>(mailDto, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                URI.create(notificationServiceUrl + uri),
                HttpMethod.POST,
                entity,
                String.class);

        return response.getBody();
    }

}
