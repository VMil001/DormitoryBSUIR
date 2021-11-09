package by.bsuir.Dormitory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PrivilegeNotFoundException extends RuntimeException {
    public PrivilegeNotFoundException(Long id) {
        super("No Privilege found with id: " + id);
    }
}
