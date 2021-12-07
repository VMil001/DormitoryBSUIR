package by.bsuir.Dormitory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DormitoryImageNotFoundException extends RuntimeException {
    public DormitoryImageNotFoundException(Long id) {
        super("No DormitoryImage found with id: " + id);
    }
}
