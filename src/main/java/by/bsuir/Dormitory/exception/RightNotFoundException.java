package by.bsuir.Dormitory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RightNotFoundException extends RuntimeException {
    public RightNotFoundException(Long id) {
        super("No Right found with id: " + id);
    }
    public RightNotFoundException(String name) {
        super("No Right found with name: " + name);
    }
}
