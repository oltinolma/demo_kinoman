package uz.oltinolma.producer.rabbitmq.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.oltinolma.producer.rabbitmq.generics.Message;
import uz.oltinolma.producer.common.LogUtil;

@Controller
@RequestMapping("/open")
public class OpenController {
    private static final Logger logger = LogUtil.getInstance();
    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    AmqpTemplate amqpTemplate;

    @PostMapping(value = "/request", produces = "application/json")
    public @ResponseBody
    Object output(@RequestBody(required = false) Message ob, @RequestHeader("Routing-Key") String routing) throws Exception {
        return amqpTemplate.
                convertSendAndReceive("kinomanTopicExchange", "request.open." + routing,
                        objectMapper.writeValueAsString(ob));
    }
}
