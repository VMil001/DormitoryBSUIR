package by.bsuir.Dormitory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("No user found with id: " + id);
    }

    public UserNotFoundException(String username) {
        super("No user found with username: " + username);
    }
}
