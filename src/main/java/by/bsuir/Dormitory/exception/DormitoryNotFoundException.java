package by.bsuir.Dormitory.exception;

import by.bsuir.Dormitory.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DormitoryNotFoundException extends RuntimeException {
    public DormitoryNotFoundException(Long id) {
        super("No Dormitory found with id: " + id);
    }

    public DormitoryNotFoundException(String name) {
        super("No Dormitory found with name: " + name);
    }

    public DormitoryNotFoundException(User user) {
        super("No Dormitory found with manager: " + user);
    }
}
