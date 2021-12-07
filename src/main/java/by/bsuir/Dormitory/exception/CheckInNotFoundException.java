package by.bsuir.Dormitory.exception;

import by.bsuir.Dormitory.model.Application;
import by.bsuir.Dormitory.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CheckInNotFoundException extends RuntimeException {
    public CheckInNotFoundException(Long id) {
        super("No CheckIn found with id: " + id);
    }

    public CheckInNotFoundException(User user) {
        super("No CheckIn found with user: " + user);
    }

    public CheckInNotFoundException(Application application) {
        super("No CheckIn found with application: " + application);
    }
}
