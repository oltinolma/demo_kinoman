package uz.oltinolma.consumer.rabbitmq.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uz.oltinolma.consumer.mvc.dao.MovieDao;
import uz.oltinolma.consumer.mvc.dao.TaxonomyDao;
import uz.oltinolma.consumer.mvc.model.Message;
import uz.oltinolma.consumer.mvc.model.ResponseWrapper;
import uz.oltinolma.consumer.mvc.movie.service.MovieService;
import uz.oltinolma.consumer.mvc.taxonomy.service.TaxonomyService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RabbitListener(priority = "1", queues = "kinoman.request",
        containerFactory = "directMessageListenerContainer")
public class GenericRequestsListener {

    private TaxonomyService taxonomyService;

    @Autowired
    private MovieService movieService;

    private ObjectMapper objectMapper;

    @Autowired
    public void setAll(ObjectMapper objectMapper, TaxonomyService taxonomyService) {
        this.objectMapper = objectMapper;
        this.taxonomyService = taxonomyService;
    }

    @RabbitHandler
    public String requests(@Header("amqp_receivedRoutingKey") String rk, String json) throws Exception {
        Message message = objectMapper.readValue(json, Message.class);
        System.out.println("routing key" + rk);
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
                case "request.taxonomy.menu.list":
                    System.out.println("taxonomy menu list");
                    response = taxonomyService.listForInputLabels();
                    break;
                case "request.movie.info.by.id":
                    System.out.println("movie info as json");
                    UUID id_movie = UUID.fromString(String.valueOf(message.getParams().get("id")));
                    response = movieService.movieInfoById(id_movie);
                    break;
                case "request.movie.list":
                    System.out.println("list of all movies");
                    response = new ResponseWrapper(movieService.list()).toJSON();
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
