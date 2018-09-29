package uz.oltinolma.consumer.mvc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class Message {

    @NotNull
    @JsonProperty("routingKey")
    private String routingKey;

    @JsonProperty("headers")
    private
    Map<Object, Object> headers;

    @JsonProperty("params")
    private
    Map<Object, Object> params;

    private Object payload;

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

    public String getRoutingKey() {
        return routingKey;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    @Override
    public String toString() {
        return "Message{" +
                "routingKey='" + routingKey + '\'' +
                ", headers=" + headers +
                ", params=" + params +
                ", payload='" + payload + '\'' +
                '}';
    }

}
