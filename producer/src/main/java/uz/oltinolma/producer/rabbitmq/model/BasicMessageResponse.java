package uz.oltinolma.producer.rabbitmq.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BasicMessageResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("responseTime")
    private long responseTime;

    @JsonProperty("status")
    private int status;

    @JsonProperty("data")
    private Object data;

    public BasicMessageResponse() {
        this.status = 200;
        this.setMessage("Success!");
        this.responseTime = System.currentTimeMillis();
    }

    public BasicMessageResponse(String message, int code) {
        this.status = code;
        this.setMessage(message);
        this.responseTime = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public BasicMessageResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public BasicMessageResponse setResponseTime(long responseTime) {
        this.responseTime = responseTime;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public BasicMessageResponse setStatus(int status) {
        this.status = status;
        return this;
    }

    public Object getData() {
        return data;
    }

    public BasicMessageResponse setData(Object data) {
        this.data = data;
        return this;
    }
}