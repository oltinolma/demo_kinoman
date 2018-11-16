package uz.oltinolma.producer.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class Status500Exception extends RuntimeException {
    public Status500Exception(String message) {
        super(message);
    }

    public Status500Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
