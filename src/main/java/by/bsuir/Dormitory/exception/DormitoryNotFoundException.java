package by.bsuir.Dormitory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DormitoryNotFoundException extends RuntimeException {
    public DormitoryNotFoundException(Long id) {
        super("No Dormitory found with id: " + id);
    }
}
