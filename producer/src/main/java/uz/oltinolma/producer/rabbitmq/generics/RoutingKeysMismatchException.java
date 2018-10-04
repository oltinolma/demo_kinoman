package uz.oltinolma.producer.rabbitmq.generics;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Your routing key doesn't match with header routing key")
public class RoutingKeysMismatchException extends RuntimeException {

    public RoutingKeysMismatchException() {
        super();
    }

    public RoutingKeysMismatchException(String message) {
        super(message);
    }

    public RoutingKeysMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoutingKeysMismatchException(Throwable cause) {
        super(cause);
    }

    protected RoutingKeysMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
