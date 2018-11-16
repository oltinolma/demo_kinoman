package uz.oltinolma.producer.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class Status404Exception extends RuntimeException {

    public Status404Exception(String message) {
        super(message);
    }

    public Status404Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
