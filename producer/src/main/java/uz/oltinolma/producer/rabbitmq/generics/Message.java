package uz.oltinolma.producer.rabbitmq.generics;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;


public class Message {

    @JsonProperty("headers")
    Map<Object, Object> headers;

    @JsonProperty("params")
    Map<Object, Object> params;

    Object payload;

    public Map<Object, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<Object, Object> headers) {
        this.headers = headers;
    }

    public Map<Object, Object> getParams() {
        return params;
    }

    public void setParams(Map<Object, Object> params) {
        this.params = params;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Message{" +
                ", headers=" + headers +
                ", params=" + params +
                ", payload='" + payload + '\'' +
                '}';
    }
}
