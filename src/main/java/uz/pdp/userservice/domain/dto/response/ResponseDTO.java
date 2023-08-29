package uz.pdp.userservice.domain.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO<T> {

    private String message;
    private Integer code;
    private T object;

    public ResponseDTO(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}