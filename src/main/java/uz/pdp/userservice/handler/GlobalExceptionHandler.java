package uz.pdp.userservice.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pdp.userservice.domain.dto.ErrorMessage;
import uz.pdp.userservice.exception.DataNotFoundException;
import uz.pdp.userservice.exception.DuplicateValueException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<ErrorMessage> dataNotFoundExceptionHandler(RuntimeException e) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(404).body(message);
    }

    @ExceptionHandler({DuplicateValueException.class})
    public ResponseEntity<ErrorMessage> duplicateValueExceptionHandler(RuntimeException e) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(409).body(message);
    }
}
