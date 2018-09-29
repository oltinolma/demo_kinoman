package uz.oltinolma.consumer.mvc.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ResponseWrapper {
    private Object data;
    private int status = 1;
    private String message = "ok";

    public ResponseWrapper(String errorMessage) {
        message = errorMessage;
        status = -1;
    }

    public ResponseWrapper(Object data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public ResponseWrapper setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseWrapper setData(Object data) {
        this.data = data;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public ResponseWrapper setStatus(int status) {
        this.status = status;
        return this;
    }

    public String toJSON() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
