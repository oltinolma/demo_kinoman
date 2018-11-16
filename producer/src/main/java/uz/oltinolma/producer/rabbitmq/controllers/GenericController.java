package uz.oltinolma.producer.rabbitmq.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.oltinolma.producer.rabbitmq.generics.Message;
import uz.oltinolma.producer.rabbitmq.model.BasicMessageResponse;
import uz.oltinolma.producer.common.LogUtil;

@Controller
@RequestMapping("/v1")
public class GenericController {

    private static final Logger logger = LogUtil.getInstance();
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    AmqpTemplate amqpTemplate;

    @PostMapping(value = "/send", produces = "application/json")
    public @ResponseBody
    BasicMessageResponse input(@RequestBody(required = false) Message ob, @RequestHeader("Routing-Key") String routing) throws Exception {
        BasicMessageResponse response = new BasicMessageResponse();
        amqpTemplate.convertAndSend("kinomanTopicExchange", "send." + routing,
                objectMapper.writeValueAsString(ob));
        response.setData(ob.getPayload());
        logger.info("request send to queue " + response.toString());
        return response;
    }

    @PostMapping(value = "/request", produces = "application/json")
    public @ResponseBody
    Object output(@RequestBody(required = false) Message ob, @RequestHeader("Routing-Key") String routing) throws Exception {
        return amqpTemplate.
                convertSendAndReceive("kinomanTopicExchange", "request." + routing,
                        objectMapper.writeValueAsString(ob));
    }
}




