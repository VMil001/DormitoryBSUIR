package by.bsuir.Dormitory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MyJwtException extends RuntimeException {

    public MyJwtException(String message, Exception e) {
        super(message, e);
    }

    public MyJwtException(String message) {
        super(message);
    }
}
