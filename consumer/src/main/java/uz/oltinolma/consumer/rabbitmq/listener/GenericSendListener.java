package uz.oltinolma.consumer.rabbitmq.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.oltinolma.consumer.mvc.model.Message;
import uz.oltinolma.consumer.mvc.model.Taxonomy;
import uz.oltinolma.consumer.mvc.service.MovieService;
import uz.oltinolma.consumer.mvc.service.TaxonomyService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(priority = "1", queues = "kinoman.send",
        containerFactory = "directMessageListenerContainer")
public class GenericSendListener {

    private TaxonomyService taxonomyService;

    private ObjectMapper objectMapper;

    @Autowired
    MovieService movieService;

    @Autowired
    public void setAll(ObjectMapper objectMapper, TaxonomyService taxonomyService) {
        this.objectMapper = objectMapper;
        this.taxonomyService = taxonomyService;
    }

    @RabbitHandler
    public void sends(@Header("amqp_receivedRoutingKey") String rk, String json) throws Exception {
        Message message = objectMapper.readValue(json, Message.class);
        router(rk, message);
    }

    private void router(String rk, Message message) throws JsonProcessingException {
        System.out.println("incoming routing key : " + rk);
        switch (rk) {
            case "send.taxonomy.insert":
                taxonomyService.insert(objectMapper.convertValue(message.getPayload(), Taxonomy.class));
                break;
            case "send.taxonomy.update":
                taxonomyService.update(objectMapper.convertValue(message.getPayload(), Taxonomy.class));
                break;
            case "send.taxonomy.delete":
                taxonomyService.delete((int) message.getParams().get("id"));
                break;
            case "send.movie.insert.or.update":
                movieService.insert(objectMapper.writeValueAsString(message.getPayload()));
                break;
//            case "send.taxonomyTerm.insert":
//                tax.insert(objectMapper.convertValue(message.getPayload(), Taxonomy.class));
//                break;
//            case "send.taxonomyTerm.update":
//                taxonomyService.update(objectMapper.convertValue(message.getPayload(), Taxonomy.class));
//                break;
//            case "send.taxonomyTerm.delete":
//                taxonomyService.delete((int) message.getParams().get("id"));
//                break;
            default:
                break;
        }
    }

}
