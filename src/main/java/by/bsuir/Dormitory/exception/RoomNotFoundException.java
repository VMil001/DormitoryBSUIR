package by.bsuir.Dormitory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(Long id) {
        super("No Room found with id: " + id);
    }
    public RoomNotFoundException(String number) {
        super("No Room found with number: " + number);
    }
}
