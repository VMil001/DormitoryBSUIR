package by.bsuir.Dormitory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PasswordRecoveryNotFoundException extends RuntimeException {
    public PasswordRecoveryNotFoundException(Long id) {
        super("No PasswordRecovery found with id: " + id);
    }
}
