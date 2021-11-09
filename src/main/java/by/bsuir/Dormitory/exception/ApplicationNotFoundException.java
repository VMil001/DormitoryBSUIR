package by.bsuir.Dormitory.exception;

import by.bsuir.Dormitory.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException(Long id) {
        super("No Application found with id: " + id);
    }
    public ApplicationNotFoundException(User user) {
        super("No Application found with user: " + user.getUsername());
    }
}
