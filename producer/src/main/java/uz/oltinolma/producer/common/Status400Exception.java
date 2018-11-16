package uz.oltinolma.producer.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class Status400Exception extends RuntimeException {
    public Status400Exception(String message) {
        super(message);
    }

    public Status400Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
