package uz.oltinolma.producer.rabbitmq.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.oltinolma.producer.rabbitmq.generics.Message;
import uz.oltinolma.producer.rabbitmq.model.BasicMessageResponse;

@Controller
@RequestMapping("/v1")
public class GenericController {

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    AmqpTemplate amqpTemplate;

    @PostMapping(value = "/send", produces = "application/json")
    public @ResponseBody
    BasicMessageResponse input(@RequestBody(required = false) Message ob, @RequestHeader("Routing-Key") String routing) throws Exception {
        BasicMessageResponse response = new BasicMessageResponse();
        amqpTemplate.convertAndSend("topicExchange", "send." + routing,
                objectMapper.writeValueAsString(ob));
        response.setData(ob.getPayload());
        return response;
    }

    @PostMapping(value = "/request", produces = "application/json")
    public @ResponseBody
    Object output(@RequestBody(required = false) Message ob, @RequestHeader("Routing-Key") String routing) throws Exception {
        return amqpTemplate.
                convertSendAndReceive("topicExchange", "request." + routing,
                        objectMapper.writeValueAsString(ob));
    }
}




