package uz.oltinolma.consumer.rabbitmq.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.oltinolma.consumer.mvc.model.Message;
import uz.oltinolma.consumer.mvc.model.ResponseWrapper;
import uz.oltinolma.consumer.mvc.service.TaxonomyService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(priority = "1", queues = "request",
        containerFactory = "directMessageListenerContainer")
public class GenericRequestsListener {

    private TaxonomyService taxonomyService;

    private ObjectMapper objectMapper;

    @Autowired
    public void setAll(ObjectMapper objectMapper, TaxonomyService taxonomyService) {
        this.objectMapper = objectMapper;
        this.taxonomyService = taxonomyService;
    }

    @RabbitHandler
    public String requests(@Header("amqp_receivedRoutingKey") String rk, String json) throws Exception {
        Message message = objectMapper.readValue(json, Message.class);
        return router(rk, message);
    }

    public String router(String rk, Message message) throws JsonProcessingException {
        System.out.println("incoming routing key : " + rk);
        String response;
        try {
            switch (rk) {
                case "request.taxonomy.getById":
                    System.out.println("taxonomy get by id");
                    response = taxonomyService.getById((int) message.getParams().get("id")).toJSON();
                    break;
                case "request.taxonomy.list":
                    System.out.println("taxonomy list");
                    response = taxonomyService.getAll().toJSON();
                    break;
                default:
                    System.out.println("default case");
                    response = new ResponseWrapper("no mapping for routing key").toJSON();
                    break;
            }
        } catch (Exception e) {
            response = new ResponseWrapper("something went wrong, try again please!").toJSON();
        }
        return response;
    }
}
